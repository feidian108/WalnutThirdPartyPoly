package com.walnut.cloud.bytedance.open.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.walnut.cloud.bytedance.open.api.ByteOpenConfigStorage;
import com.walnut.cloud.bytedance.open.api.ByteOpenOauthService;
import com.walnut.cloud.bytedance.open.api.ByteOpenService;
import com.walnut.cloud.bytedance.open.bean.ByteOpenAccessToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenCallback;
import com.walnut.cloud.bytedance.open.bean.ByteOpenClientToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenRefreshToken;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizationInfo;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.bytedance.open.bean.data.billboard.*;
import com.walnut.cloud.bytedance.open.bean.data.star.ByteOpenStarAuthorScore;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoData;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoList;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoSource;
import com.walnut.cloud.bytedance.open.bean.result.*;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFans;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFansCheck;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFollow;
import com.walnut.cloud.bytedance.open.enums.ByteOpenApiUrl;
import com.walnut.cloud.bytedance.open.util.decrypt.DecryptMobileUtil;
import com.walnut.cloud.bytedance.open.util.json.ByteOpenGsonBuilder;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.bytedance.ByteRuntimeException;
import com.walnut.cloud.open.common.util.http.URIUtil;
import com.walnut.cloud.open.common.util.security.SHA1SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.walnut.cloud.bytedance.open.enums.ByteOpenApiUrl.DataExternal.*;
import static com.walnut.cloud.bytedance.open.enums.ByteOpenApiUrl.User.*;
import static com.walnut.cloud.bytedance.open.enums.ByteOpenApiUrl.Video.*;


@Slf4j
@AllArgsConstructor
public class ByteOpenOauthServiceImpl implements ByteOpenOauthService {

    private final ByteOpenService byteOpenService;


    public ByteOpenService getByteOpenService() {
        return byteOpenService;
    }

    @Override
    public ByteOpenConfigStorage getByteOpenConfigStorage() {
        return byteOpenService.getByteOpenConfigStorage();
    }

    /**
     * <h3> 账号授权 - 构造各个账户授权二维码链接地址 </h3>
     * @param authType 账号类型
     * @param scope 作用域
     * @param redirectUri 授权成功后的回调地址，必须以http/https开头。域名必须对应申请应用时填写的域名，如不清楚请联系应用申请人
     * @param optionalScope 应用授权可选作用域,多个授权作用域以英文逗号（,）分隔，每一个授权作用域后需要加上一个是否默认勾选的参数，1为默认勾选，0为默认不勾选
     * @param state 用于保持请求和回调的状态
     * @return 授权二维码链接地址
     */
    @Override
    public String getPreAuthUrl(String authType, String scope, String redirectUri, String optionalScope, String state) {
        return createPreAuthUrl(authType, scope, redirectUri, optionalScope, state);
    }

    /**
     * <h3> 账号授权 - 通过授权码获取授权信息 access token 和 refresh_token </h3>
     * @param code 授权码
     * @param authType 账号类型
     * @return 授权信息
     */
    @Override
    public ByteOpenQueryAuthResult getQueryAuth(String code, String authType) {

        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("client_key", getByteOpenConfigStorage().getClientKey()));
        parameters.add(new BasicNameValuePair("client_secret", getByteOpenConfigStorage().getClientSecret()));
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("code", code));

        String responseContent = post(ByteOpenApiUrl.OAuth2.DOU_OAUTH_GET_ACCESS_TOKEN_URL.getUrl(getByteOpenConfigStorage()), parameters);
        ByteOpenQueryAuthResult queryAuth = ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenQueryAuthResult.class);
        if( queryAuth == null || queryAuth.getData() == null ) {
            return queryAuth;
        }
        ByteOpenAuthorizationInfo authorizationInfo = queryAuth.getData();

        if( authorizationInfo.getAccessToken() != null ) {
            getByteOpenConfigStorage().updateAccessToken(authorizationInfo.getOpenId(), authorizationInfo.getAccessToken(), authorizationInfo.getExpiresIn());
        }

        if( authorizationInfo.getRefreshToken() != null ) {
            getByteOpenConfigStorage().setRefreshToken(authorizationInfo.getOpenId(), authorizationInfo.getRefreshToken(), authorizationInfo.getRefreshExpiresIn());
        }
        return queryAuth;

    }

    /**
     * <h3> 账号授权 - 获取 AccessToken </h3>
     * @param forceRefresh 强制刷新
     * @return token 信息
     */
    @Override
    public String getAccessToken(String openId, boolean forceRefresh) {
        ByteOpenConfigStorage config = getByteOpenConfigStorage();

        if (!config.isAccessTokenExpired(openId) && !forceRefresh) {
            return config.getAccessToken(openId);
        }

        Lock lock = config.getAccessTokenLock(openId);
        boolean locked;
        try {
            do {
                locked = lock.tryLock(100, TimeUnit.MILLISECONDS);
                if (!forceRefresh && !config.isAccessTokenExpired(openId)) {
                    return config.getAccessToken(openId);
                }
            } while (!locked);
            String refreshToken = config.getRefreshToken(openId);
            if (refreshToken == null) {
                throw new ByteErrorException("授权信息已经过期，请重新授权！");
            }
            return this.refreshAccessToken(openId, refreshToken).getAccessToken();
        } catch (InterruptedException | ByteErrorException e) {
            throw new ByteRuntimeException(e);
        }
    }

    /**
     * <h3> 账号授权 - 获取 RefreshToken </h3>
     * @param openId 用户id
     * @param forceRefresh 强制刷新
     * @return RefreshToken
     */
    @Override
    public String getRefreshToken(String openId, boolean forceRefresh) throws ByteErrorException {
        ByteOpenConfigStorage config = getByteOpenConfigStorage();

        if( !config.isRefreshTokenExpired(openId) && !forceRefresh ) {
            return config.getRefreshToken(openId);
        }
        throw new ByteErrorException("refresh_token 无效或过期");

    }

    /**
     * <h3> 刷新 Access Token </h3>
     * @param refreshToken 刷新token
     * @return 刷新后获得的 Access Token 信息
     */
    private ByteOpenAccessToken refreshAccessToken(String openId, String refreshToken) throws ByteErrorException {

        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("client_key", getByteOpenConfigStorage().getClientKey()));
        parameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        parameters.add(new BasicNameValuePair("refresh_token", refreshToken));

        String responseContent = post(ByteOpenApiUrl.OAuth2.DOU_OAUTH_REFRESH_ACCESS_TOKEN_URL.getUrl(getByteOpenConfigStorage()), parameters);
        ByteOpenAccessToken accessToken = ByteOpenAccessToken.fromJson(responseContent);

        if( accessToken.getErrorCode() == 10010 ) {
            return refreshAccessToken(openId, this.renewRefreshToken(openId, refreshToken).getRefreshToken());
        }

        if( accessToken.getAccessToken() != null ) {
            getByteOpenConfigStorage().updateAccessToken(openId, accessToken.getAccessToken(), accessToken.getExpiresIn());
        }
        if( accessToken.getRefreshToken() != null ) {
            getByteOpenConfigStorage().setRefreshToken(openId, accessToken.getRefreshToken(), accessToken.getRefreshExpiresIn());
        }

        return accessToken;

    }

    /**
     * <h3> 刷新 RefreshToken </h3>
     * @param oldRefreshToken 旧的RefreshToken
     * @return 新的 RefreshToken 信息
     */
    private ByteOpenRefreshToken renewRefreshToken (String openId, String oldRefreshToken) throws ByteErrorException {
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("client_key", getByteOpenConfigStorage().getClientKey()));
        parameters.add(new BasicNameValuePair("refresh_token", oldRefreshToken));
        String responseContent = post(ByteOpenApiUrl.OAuth2.DOU_OAUTH_RENEW_REFRESH_TOKEN_URL.getUrl(getByteOpenConfigStorage()), parameters);
        ByteOpenRefreshToken refreshToken = ByteOpenRefreshToken.fromJson(responseContent);

        if( refreshToken.getErrorCode() == 10020 ) {
            throw new ByteErrorException("refresh_token已过期，请重新授权！");
        }
        if( refreshToken.getRefreshToken() != null ) {
            getByteOpenConfigStorage().setRefreshToken(openId, refreshToken.getRefreshToken(), refreshToken.getExpiresIn());
        }
        return refreshToken;

    }

    /**
     * <h3> 账号授权 - 获取 client_token </h3>
     * @param forceRefresh 是否强制刷新
     * @return client token 信息
     */
    @Override
    public String getClientToken(boolean forceRefresh) {
        final ByteOpenConfigStorage config = this.getByteOpenConfigStorage();
        if (!config.isClientTokenExpired() && !forceRefresh) {
            return config.getClientToken();
        }
        Lock lock = config.getClientTokenLock();
        lock.lock();
        try {
            if (!config.isClientTokenExpired() && !forceRefresh) {
                return config.getClientToken();
            }
            List<BasicNameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("client_key", getByteOpenConfigStorage().getClientKey()));
            parameters.add(new BasicNameValuePair("client_secret", getByteOpenConfigStorage().getClientSecret()));
            parameters.add(new BasicNameValuePair("grant_type", "client_credential"));

            String responseContent = post("https://open.douyin.com/oauth/client_token", parameters);
            ByteOpenClientToken clientToken = ByteOpenClientToken.fromJson(responseContent);
            config.updateClientToken(clientToken);
            return config.getClientToken();
        } finally {
            lock.unlock();
        }
    }

    /**
     * <h3> 用户管理 - 获取授权方帐号基本信息 </h3>
     * @param openId 用户ID
     * @return 帐号基本信息
     */
    @Override
    public ByteOpenAuthorizerInfo getAuthorizerInfo(String openId) {
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("access_token", getByteOpenConfigStorage().getAccessToken(openId)));
        parameters.add(new BasicNameValuePair("open_id", openId));
        String responseContent =  post(GET_OAUTH_USER_INFO_URL.getUrl(getByteOpenConfigStorage()), parameters);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenAuthorizerInfo.class);

    }

    /**
     * <h3> 获取粉丝列表信息 </h3>
     * @param openId 授权方帐号ID
     * @param cursor 分页游标
     * @param count 每页数量
     * @return 粉丝列表信息
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenFans getFansList(String openId, int cursor, int count) throws ByteErrorException {
        String currentUrl = String.format(DOU_GET_FANS_LIST_URL.getUrl(getByteOpenConfigStorage()),
                openId, cursor, count);
        String responseContent = get(currentUrl, openId);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenFans.class);
    }

    /**
     * <h3> 获取关注用户信息 </h3>
     * @param openId 授权方帐号ID
     * @param cursor 分页游标
     * @param count 每页数量
     * @return 关注用户信息
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenFollow getFollowingList(String openId, int cursor, int count) throws ByteErrorException {
        String currentUrl = String.format(DOU_GET_FOLLOW_LIST_URL.getUrl(getByteOpenConfigStorage()),
                openId, cursor, count);
        String responseContent = get(currentUrl, openId);
        return  ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenFollow.class);
    }

    /**
     * <h3> 用户管理 - 粉丝判断 </h3>
     * @param followerOpenId 需要检查的用户ID
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @return 粉丝判断
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenFansCheck fansCheck(String followerOpenId, String openId) throws ByteErrorException {
        String responseContent = get(String.format(DOU_FANS_CHECK_URL.getUrl(getByteOpenConfigStorage()), followerOpenId, openId), openId);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenFansCheck.class);
    }

    /**
     * <h3> 用户管理 - 解密手机号码 </h3>
     * @param encryptedMobile 加密的手机号码
     * @return 解密后的手机号码
     */
    @Override
    public String decryptMobile(String encryptedMobile) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        ByteOpenConfigStorage config = getByteOpenConfigStorage();
        String clientSecret = config.getClientSecret();
        byte[] clientSecretBytes = clientSecret.getBytes();
        SecretKey secretKey = new SecretKeySpec(clientSecretBytes, 0, clientSecretBytes.length, "AES");

        byte[] iv = Arrays.copyOfRange(clientSecretBytes, 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        String algorithm = "AES/CBC/PKCS5Padding";

        return DecryptMobileUtil.decrypt(algorithm, encryptedMobile, secretKey, ivParameterSpec);
    }

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询授权账号视频列表 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量
     * @return 视频列表信息
     */
    @Override
    public ByteOpenUserVideoList getDouYinVideoList(String openId, int cursor, int count) throws ByteErrorException {
        String currentUrl = String.format(DOU_VIDEO_LIST_URL.getUrl(getByteOpenConfigStorage()),
                openId, cursor, count);
        String responseContent = get(currentUrl, openId);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenUserVideoList.class);
    }

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询特定视频的视频数据 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemsIds item_id数组，仅能查询access_token对应用户上传的视频
     * @return 视频数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenUserVideoData getDouYinVideoData(String openId, List<String> itemsIds) throws ByteErrorException {
        JSONObject json = new JSONObject();
        json.put("item_ids", itemsIds.toArray());
        String currentUrl = String.format(DOU_VIDEO_DATA_URL.getUrl(getByteOpenConfigStorage()),
                openId);
        String responseContent = post(currentUrl, openId, json.toString());
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenUserVideoData.class);
    }

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询抖音视频来源 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemsIds item_id数组，仅能查询access_token对应用户上传的视频
     * @return 视频来源
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenUserVideoSource getDouYinVideoSource(String openId, List<String> itemsIds) throws ByteErrorException {
        JSONObject json = new JSONObject();
        json.put("item_ids", itemsIds.toArray());
        String responseContent = post(String.format(DOU_VIDEO_SOURCE_URL.getUrl(getByteOpenConfigStorage()),
                openId), openId, json.toString());
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenUserVideoSource.class);
    }

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询视频携带的地点信息 </h3>
     * @param keyword 查询关键字，例如美食
     * @param city 查询城市，例如上海、北京
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量（最大20）
     * @return 地点信息
     * @throws ByteErrorException 异常信息
     */
    @Override
    public String getDouYinVideoPoi(String keyword, String city, int cursor, int count) throws ByteErrorException {
        return get("https://open.douyin.com/poi/search/keyword/?keyword=" + keyword + "&count=" + count + "&city=" + city + "&cursor=" + cursor, null);
    }

    /**
     * <h3> 互动管理 - 评论管理（普通用户） - 评论列表 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemId 视频id
     * @param sortType 列表排序方式，不传默认按推荐序，可选值：time(时间逆序)、time_asc(时间顺序)
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页的数量，最大不超过50，最小不低于1
     * @return 评论列表
     * @throws ByteErrorException 异常
     */
    @Override
    public String getItemCommentList(String openId, String itemId, String sortType, int cursor, int count) throws ByteErrorException {

        return get(String.format(DOU_ITEM_COMMENT_DATA_URL.getUrl(getByteOpenConfigStorage()), openId, itemId, sortType, cursor, count), openId);
    }

    /**
     * <h3> 搜索管理 - 关键词视频搜索 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param keyword  关键词
     * @param count 每页数量
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @return 视频信息
     * @throws ByteErrorException 异常
     */
    @Override
    public String videoSearch(String openId, String keyword, int count, int cursor) throws ByteErrorException {
        return get("https://open.douyin.com/video/search/?open_id=" + openId + "&keyword=" + keyword + "&count=" + count + "&cursor=" +cursor, openId);
    }

    /**
     * <h3> 数据开放服务 - 用户数据 </h3>
     * @param type 数据类型[]
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param dateType 近7/15天；输入7代表7天、15代表15天、30代表30天
     * @return 用户相关数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenUserItemResult getUserData(String type, String openId, int dateType) throws ByteErrorException {
        String responseContent = get(String.format(DOU_USER_ITEM_DATA_URL.getUrl(getByteOpenConfigStorage()),
                openId, dateType), openId);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenUserItemResult.class);
    }

    /**
     * <h3> 数据开放服务 - 粉丝画像数据 </h3>
     * @param type 数据类型["":粉丝数据,source:粉丝来源,favourite:粉丝喜好,comment:粉丝热评]
     * @param openId 用户ID
     * @return 粉丝画像数据
     * @throws ByteErrorException 异常
     */
    @Override
    public String getFansData(String type, String openId) throws ByteErrorException {
        if( StringUtils.isNotEmpty(type) ) {
            return get("https://open.douyin.com/fans/data/" +type+ "/?open_id=" + openId, openId);
        }
        return get("https://open.douyin.com/fans/data/?open_id=" + openId, openId);
    }

    /**
     * <h3> 数据开放服务 - 视频数据 </h3>
     * @param type 数据类型：[base:基础数据,like:点赞数据,comment:评论数据,play:播放数据,share:分享数据]
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemId item_id，仅能查询access_token对应用户上传的视频
     * @return 视频相关数据
     * @throws ByteErrorException 异常
     */
    @Override
    public String getItemData(String type, String openId, String itemId, int dateType) throws ByteErrorException {
        return get("https://open.douyin.com/data/external/item/" + type +"/?open_id=" + openId + "&item_id=" + itemId + "&date_type=" + dateType, openId);
    }

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人热榜 </h3>
     * @param hotListType 达人热榜类型 * `1` - 星图指数榜 * `2` - 涨粉指数榜 * `3` - 性价比指数榜 * `4` - 种草指数榜 * `5` - 精选指数榜 * `6` - 传播指数榜
     * @return  抖音星图达人热榜
     */
    @Override
    public ByteOpenStarHotListResult getStarHotList(int hotListType) throws ByteErrorException {
        String responseContent = get(String.format(DOU_STAR_HOT_LIST_URL.getUrl(getByteOpenConfigStorage()),
                hotListType), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenStarHotListResult.class);
    }

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人指数 </h3>
     * @param openId 用户ID
     * @return 抖音星图达人指数
     */
    @Override
    public ByteOpenStarAuthorScore getStarTopScoreDisplay(String openId) throws ByteErrorException {
        String responseContent = get(String.format(DOU_STAR_AUTHOR_SCORE_URL.getUrl(getByteOpenConfigStorage()),
                openId), openId);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenStarAuthorScore.class);
    }

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人指数数据V2</h3>
     * @param uniqueId 达人抖音号
     * @return 达人指数
     */
    @Override
    public ByteOpenStarAuthorScore getStarAuthorScoreDisplay(String uniqueId) throws ByteErrorException {
        String responseContent = get(String.format(DOU_STAR_AUTHOR_SCORE_V2_URL.getUrl(getByteOpenConfigStorage()),
                uniqueId), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenStarAuthorScore.class);
    }


    /**
     * <h3> 数据开放服务 - 抖音影视综艺榜单数据 - 获取抖音电影榜、抖音电视剧榜、抖音综艺榜 </h3>
     * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @param version 榜单版本：空值默认为本周榜单
     * @return 榜单数据
     */
    @Override
    public ByteOpenRankItemResult getDiscoveryEntRankItem(int type, int version) throws ByteErrorException {
        String currentUrl = String.format(DOU_DISCOVERY_ENT_RANK_ITEM_URL.getUrl(getByteOpenConfigStorage()),
                type, version);
        if (version == 0) {
            currentUrl = currentUrl.replace("&version=", "&version=" + version);
        } else {
            currentUrl = currentUrl.replace("&version=", "");
        }
        String responseContent = get(currentUrl, null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenRankItemResult.class);
    }

    /**
     * <h3> 数据开放服务 - 抖音影视综艺榜单数据 - 获取抖音影视综榜单版本 </h3>
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量(最大20)
     * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @return 榜单版本信息
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenRankVersionResult getDiscoveryEntRankVersion(int cursor, int count, int type) throws ByteErrorException {
        String responseContent = get(String.format(DOU_DISCOVERY_ENT_RANK_VERSION_URL.getUrl(getByteOpenConfigStorage()),
                type, count, cursor), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenRankVersionResult.class);
    }

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取实时热点词 </h3>
     * @return 热点词
     * @throws ByteErrorException 异常
     */
    @Override
    public String getHotSearchSentences() throws ByteErrorException {
        return get("https://open.douyin.com/hotsearch/sentences/", null);
    }

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取上升词</h3>
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量（最大为20）
     * @return 上升词
     * @throws ByteErrorException 异常
     */
    @Override
    public String getHotSearchTrendingSentences(int cursor, int count) throws ByteErrorException {
        return get("https://open.douyin.com/hotsearch/trending/sentences/?cursor=" + cursor + "&count=" + count, null);
    }

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取热点词聚合的视频 </h3>
     * @param hotSentence 热点词
     * @return 热点词聚合的视频
     * @throws ByteErrorException 异常
     */
    @Override
    public String getHotSearchVideos(String hotSentence) throws ByteErrorException {
        return get("https://open.douyin.com/hotsearch/videos/?hot_sentence=" + hotSentence, null);
    }

    /**
     * <h3> 数据开放服务 - 榜单数据 - 热门视频榜单 </h3>
     * @return 热门书品榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenHotVideoBillboardResult getHotVideoBillboard() throws ByteErrorException {
        String responseContent = get(DOU_BILLBOARD_HOT_VIDEO_URL.getUrl(getByteOpenConfigStorage()), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenHotVideoBillboardResult.class);
    }

    /**
     * <h3> 数据开放 - 榜单数据 - 直播榜单 </h3>
     * @return 榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenLiveBillboardList getLiveBillboard() throws ByteErrorException {
        String responseContent = get(DOU_BILLBOARD_LIVE_URL.getUrl(getByteOpenConfigStorage()), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenLiveBillboardList.class);
    }

    /**
     * <h3> 数据开放服务 - 榜单数据 - 音乐榜数据 </h3>
     * @param billTye 音乐榜单类型  hot 热歌榜  soar 飙升榜 original 原创榜
     * @return 榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenMusicBillboardList getMusicBillboard(String billTye) throws ByteErrorException {
        String currentUrl;

        switch (billTye) {
            case "hot":
                currentUrl = DOU_BILLBOARD_MUSIC_HOT_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "soar":
                currentUrl = DOU_BILLBOARD_MUSIC_SOAR_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "original":
                currentUrl = DOU_BILLBOARD_MUSIC_ORIGINAL_URL.getUrl(getByteOpenConfigStorage());
                break;
            default:
                throw new ByteRuntimeException("不支持的榜单类型！");
        }
        String responseContent = get(currentUrl, null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenMusicBillboardList.class);
    }

    /**
     * <h3> 数据开放 - 榜单数据 - 话题榜数据 </h3>
     * @return 榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenTopicBillboardList getTopicBillboard() throws ByteErrorException {
        String responseContent = get(DOU_BILLBOARD_TOPIC_URL.getUrl(getByteOpenConfigStorage()), null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenTopicBillboardList.class);
    }

    /**
     * <h3> 数据开放 - 榜单数据 - 道具榜数据 </h3>
     * @return 榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenPropBillboardList getPropBillboard() throws ByteErrorException {
        String responseContent = get(DOU_BILLBOARD_PROP_URL.getUrl(getByteOpenConfigStorage()),  null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenPropBillboardList.class);
    }


    /**
     * <h3> 数据开放服务 - 榜单数据 </h3>
     * @param cate 榜单类型 [hot_video:热门视频榜,sport:体育榜，amusement:搞笑榜,game:游戏榜,food:美食榜，drama:剧情榜,car:汽车榜,travel:旅游榜，cospa:二次元榜，stars:娱乐明星榜，live:直播榜，music:音乐榜，topic:话题榜，prop:道具榜]
     * @param type 榜单类型
     *          <p> 体育榜：[overall:总榜,basketball:篮球榜,soccer:足球榜,comprehensive:综合体育榜,fitness:运动健身榜,outdoors:户外运动榜,table_tennis:台球榜,culture:运动文化榜]</p>
     *          <p> 搞笑榜：[overall:总榜,new:新势力榜]</p>
     *          <p> 游戏榜：[console:单机主机榜,inf:游戏资讯榜]</p>
     *          <p> 美食榜：[overall:总榜,new:新势力榜,tutorial:教程榜,shop:探店榜]</p>
     *          <p> 剧情榜：[overall:总榜]</p>
     *          <p> 汽车榜：[overall:总榜,comment:评车,play:玩车,use:用车,driver:驾车]</p>
     *          <p> 旅游榜：[overall:总榜,new:新势力榜]</p>
     *          <p> 二次元：[overall:总榜,qing_man:轻漫,out_shot:出镜拍摄,painting:绘画,voice_control:声控,brain_cavity:脑洞,new:新势力榜]</p>
     * @return  榜单数据
     * @throws ByteErrorException 异常
     */
    @Override
    public ByteOpenBillboardList getBillboard(String cate, String type) throws ByteErrorException {
        String url = getBillboardUrlByCateAndType(cate, type);
        String responseContent = get(url, null);
        return ByteOpenGsonBuilder.create().fromJson(responseContent, ByteOpenBillboardList.class);
    }

    /**
     * <h3> 数据开放服务 - 获取SDK分享视频数据 </h3>
     * @param beginDate  开始日期(yyyy-MM-dd)
     * @param endDate  结束日期(yyyy-MM-dd)
     * @return 分享视频数据
     * @throws ByteErrorException 异常
     */
    @Override
    public String getSDKShare(String beginDate, String endDate) throws ByteErrorException {
        return get("https://open.douyin.com/data/external/sdk_share/?begin_date=" + beginDate + "&end_date=" + endDate, null);
    }

    /**
     * <h3> 数据开放服务 - 获取小程序点击量视频分布 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param mpId mp_id，小程序id
     * @param dateType 近7/15/30天；输入7代表7天、15代表15天、30代表30天
     * @return 点击量视频分布
     * @throws ByteErrorException 异常
     */
    @Override
    public String getMpItemClickDistribution(String openId, String mpId, int dateType) throws ByteErrorException {
        return get("https://open.douyin.com/data/external/anchor/mp_item_click_distribution?open_id=%s&access_token=%s&mp_id=%s&date_type=%s", openId);
    }

    @Override
    public ByteOpenCallback getEventMessage(String body, String signature) throws ByteErrorException {
        String str = this.getByteOpenConfigStorage().getClientSecret() + body;
        if(!SHA1SecurityUtil.shaEncode(str).equals(signature)) {
            throw new ByteErrorException("消息来源有误！");
        }

        return ByteOpenGsonBuilder.create().fromJson(body, ByteOpenCallback.class);
    }

    @Override
    public String post(String uri, List<BasicNameValuePair> parameters) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(uri);
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, "utf-8");

            post.setEntity(urlEncodedFormEntity);
            String newUri;
            HttpResponse response = client.execute(post);
            if ( response.getStatusLine().getStatusCode() >= 300 ) {
                Header header = response.getFirstHeader("Location");// 跳转的目标地址是在 HTTP-HEAD 中的
                newUri = "https://open.douyin.com" + header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
                post = new HttpPost(newUri);
                post.setEntity(urlEncodedFormEntity);
                response = client.execute(post);
            }
            HttpEntity entity  = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            throw new ByteRuntimeException(e);
        }
    }


    @Override
    public String post(String uri, String openId, String postData) throws ByteErrorException {
        return post(uri, openId, postData, "access-token");
    }

    @Override
    public String post(String uri, String openId, String postData, String accessTokenKey) throws ByteErrorException {
        String accessToken = getAccessToken(openId, false);
        Map<String, String> headers = new HashMap<>();
        headers.put(accessTokenKey, accessToken);

        return getByteOpenService().post(uri, headers, postData);
    }

    @Override
    public String get(String uri, String openId) throws ByteErrorException {
        return get(uri, openId, "access-token");
    }

    @Override
    public String get(String uri, String openId, String accessTokenKey) throws ByteErrorException {
        String accessToken;
        Map<String, String> headers = new HashMap<>();
        if( openId == null ) {
            accessToken = getClientToken(false);
        } else{
            accessToken = getAccessToken(openId, false);
        }
        headers.put(accessTokenKey, accessToken);
        return getByteOpenService().get(uri, headers, null);
    }

    /**
     * <h3> 创建授权链接 </h3>
     * @param authType 账户类型
     * @param scope 作用域
     * @param redirectUri 回调地址
     * @param optionalScope 作用域
     * @param state 随机码
     * @return 授权二维码链接地址
     */
    private String createPreAuthUrl(String authType, String scope,  String redirectUri, String optionalScope, String state) {

        String url = this.getUriByAuthType(authType);
        String preAuthUrlStr = String.format(url,
                getByteOpenConfigStorage().getClientKey(),
                scope,
                URIUtil.encodeURIComponent(redirectUri),
                optionalScope,
                StringUtils.trimToEmpty(state));
        if (StringUtils.isNotEmpty(optionalScope)) {
            preAuthUrlStr = preAuthUrlStr.replace("&optionalScope=", "&optionalScope=" + optionalScope);
        } else {
            preAuthUrlStr = preAuthUrlStr.replace("&optionalScope=", "");
        }
        if (StringUtils.isNotEmpty(state)) {
            preAuthUrlStr = preAuthUrlStr.replace("&state=", "&state=" + state);
        } else {
            preAuthUrlStr = preAuthUrlStr.replace("&state=", "");
        }
        return preAuthUrlStr;
    }

    private String getUriByAuthType(String authType) {

        switch (authType) {
            case "douyin":
                return ByteOpenApiUrl.OAuth2.DOU_OAUTH2_ACCESS_TOKEN_URL.getUrl(getByteOpenConfigStorage());
            case "snssdk":
                return ByteOpenApiUrl.OAuth2.TOU_OAUTH2_ACCESS_TOKEN_URL.getUrl(getByteOpenConfigStorage());
            case "xigua":
                return ByteOpenApiUrl.OAuth2.GUA_OAUTH2_ACCESS_TOKEN_URL.getUrl(getByteOpenConfigStorage());
            default:
                throw new ByteRuntimeException("不存在的帐号授权信息！");
        }
    }

    /**
     * <h3> 数据开放 - 榜单数据 </h3>
     * @param category 分类
     * @param type 明细类
     * @return 链接地址
     */
    private String getBillboardUrlByCateAndType( String category, String type ) {
        String url;
        switch ( category ) {
            case "sport":
                url = this.getSportBillboardUrlByType(type);
                break;
            case "amusement":
                if(Objects.equals(type, "overall")) {
                    url = DOU_BILLBOARD_AMUSEMENT_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                } else if(Objects.equals(type, "new")) {
                    url = DOU_BILLBOARD_AMUSEMENT_NEW_URL.getUrl(getByteOpenConfigStorage());
                } else{
                    throw new ByteRuntimeException("[搞笑类]榜单不存在[" + type + "]明细类榜单数据");
                }
                break;
            case "game":
                if(Objects.equals(type, "console")) {
                    url = DOU_BILLBOARD_GAME_CONSOLE_URL.getUrl(getByteOpenConfigStorage());
                } else if(Objects.equals(type, "inf")) {
                    url = DOU_BILLBOARD_GAME_INF_URL.getUrl(getByteOpenConfigStorage());
                } else{
                    throw new ByteRuntimeException("[游戏类]榜单不存在[" + type + "]明细类榜单数据");
                }
                break;
            case "food":
                url = this.getFoodBillboardUrlByType(type);
                break;
            case "drama":
                url = DOU_BILLBOARD_DRAMA_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "car":
                url = this.getCarBillboardUrlByType(type);
                break;
            case "travel":
                if(Objects.equals(type, "overall")) {
                    url = DOU_BILLBOARD_TRAVEL_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                } else if(Objects.equals(type, "new")) {
                    url = DOU_BILLBOARD_TRAVEL_NEW_URL.getUrl(getByteOpenConfigStorage());
                } else{
                    throw new ByteRuntimeException("[旅游类]榜单不存在[" + type + "]明细类榜单数据");
                }
                break;
            case "cospa":
                url = this.getCosplayBillboardUrlByType(type);
                break;
            default:
                throw new ByteRuntimeException("不存在的榜单类型！");
        }
        return url;
    }

    /**
     * <h3> 体育类榜单分类地址构建 </h3>
     * @param type 榜单分类
     * @return 链接地址
     */
    private String getSportBillboardUrlByType(String type) {
        String url;
        switch (type) {
            case "overall":
                url = DOU_BILLBOARD_SPORT_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "basketball":
                url = DOU_BILLBOARD_SPORT_BASKETBALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "soccer":
                url = DOU_BILLBOARD_SPORT_SOCCER_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "comprehensive":
                url = DOU_BILLBOARD_SPORT_COMPREHENSIVE_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "fitness":
                url = DOU_BILLBOARD_SPORT_FITNESS_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "outdoors":
                url = DOU_BILLBOARD_SPORT_OUTDOORS_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "table_tennis":
                url = DOU_BILLBOARD_SPORT_TABLE_TENNIS_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "culture":
                url = DOU_BILLBOARD_SPORT_CULTURE_URL.getUrl(getByteOpenConfigStorage());
                break;
            default:
                throw new ByteRuntimeException("[体育类]榜单不存在[" + type + "]明细类榜单数据");
        }
        return url;
    }

    /**
     * <h3> 美食类榜单分类地址构建 </h3>
     * @param type 榜单分类
     * @return 链接地址
     */
    private String getFoodBillboardUrlByType(String type) {
        String url;
        switch (type) {
            case "overall":
                url = DOU_BILLBOARD_FOOD_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "new":
                url = DOU_BILLBOARD_FOOD_NEW_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "tutorial":
                url = DOU_BILLBOARD_FOOD_TUTORIAL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "shop":
                url = DOU_BILLBOARD_FOOD_SHOP_URL.getUrl(getByteOpenConfigStorage());
                break;
            default:
                throw new ByteRuntimeException("[美食类]榜单不存在[" + type + "]明细类榜单数据");
        }
        return url;
    }

    /**
     * <h3> 汽车类榜单分类地址构建 </h3>
     * @param type 分类
     * @return 链接地址
     */
    private String getCarBillboardUrlByType(String type) {
        String url;
        switch (type) {
            case "overall":
                url = DOU_BILLBOARD_CAR_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "comment":
                url = DOU_BILLBOARD_CAR_COMMENT_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "play":
                url = DOU_BILLBOARD_CAR_PLAY_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "use":
                url = DOU_BILLBOARD_CAR_USE_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "driver":
                url = DOU_BILLBOARD_CAR_DRIVER_URL.getUrl(getByteOpenConfigStorage());
                break;
            default:
                throw new ByteRuntimeException("[汽车类]榜单不存在[" + type + "]明细类榜单数据");
        }
        return url;
    }

    /**
     * <h3> 二次元类榜单分类地址构建 </h3>
     * @param type 类型
     * @return 链接地址
     */
    private String getCosplayBillboardUrlByType(String type) {
        String url;
        switch (type) {
            case "overall":
                url = DOU_BILLBOARD_COSPLAY_OVERALL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "new":
                url = DOU_BILLBOARD_COSPLAY_NEW_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "qing_man":
                url = DOU_BILLBOARD_COSPLAY_QING_MAN_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "out_shot":
                url = DOU_BILLBOARD_COSPLAY_OUT_SHOT_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "painting":
                url = DOU_BILLBOARD_COSPLAY_PAINTING_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "voice_control":
                url = DOU_BILLBOARD_COSPLAY_VOICE_CONTROL_URL.getUrl(getByteOpenConfigStorage());
                break;
            case "brain_cavity":
                url = DOU_BILLBOARD_COSPLAY_BRAIN_CAVITY_URL.getUrl(getByteOpenConfigStorage());
                break;
            default:
                throw new ByteRuntimeException("[汽车类]榜单不存在[" + type + "]明细类榜单数据");
        }
        return url;
    }
}
