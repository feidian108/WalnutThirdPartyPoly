package com.walnut.cloud.bytedance.open.api;


import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenMusicBillboardList;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenPropBillboardList;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenTopicBillboardList;
import com.walnut.cloud.bytedance.open.bean.data.star.ByteOpenStarAuthorScore;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoData;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoList;
import com.walnut.cloud.bytedance.open.bean.result.*;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFans;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFollow;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface ByteOpenOauthService {

    ByteOpenConfigStorage getByteOpenConfigStorage();

    /**
     * <h3> 账号授权 - 构造授权链接方法 </h3>
     * @param authType 账号类型
     * @param redirectUri 授权成功后的回调地址，必须以http/https开头。域名必须对应申请应用时填写的域名，如不清楚请联系应用申请人
     * @param optionalScope 应用授权可选作用域,多个授权作用域以英文逗号（,）分隔，每一个授权作用域后需要加上一个是否默认勾选的参数，1为默认勾选，0为默认不勾选
     * @param state 用于保持请求和回调的状态
     * @return 授权二维码链接字符串
     * @throws ByteErrorException 异常
     */
    String getPreAuthUrl(String authType, String scope, String redirectUri, String optionalScope, String state) throws ByteErrorException;

    /**
     * <h3> 账号授权 - 通过授权码获取授权 access token </h3>
     * @param code 授权码
     * @param authType 账号类型
     * @return 授权信息
     * @throws ByteErrorException 异常
     */
    ByteOpenQueryAuthResult getQueryAuth(String code, String authType) throws ByteErrorException, IOException;

    /**
     * <h3> 账号授权 - 获取 AccessToken </h3>
     * @param forceRefresh 强制刷新
     * @return token 信息
     * @throws ByteErrorException 异常
     */
    String getAccessToken(String openId, boolean forceRefresh) throws ByteErrorException;

    /**
     * <h3> 账号授权 - 获取 refresh_token </h3>
     * @param openId 用户id
     * @param forceRefresh 强制刷新
     * @return token 信息
     * @throws ByteErrorException 异常
     */
    String getRefreshToken(String openId,  boolean forceRefresh) throws ByteErrorException;

    /**
     * <h3> 账号授权 - 获取 client token </h3>
     * @param forceRefresh 是否强制刷新
     * @return client token
     * @throws ByteErrorException 异常
     */
    String getClientToken(boolean forceRefresh) throws ByteErrorException;


    /**
     * <h3> 用户管理 - 获取粉丝列表信息 </h3>
     * @param openId 授权方帐号ID
     * @param cursor 分页游标
     * @param count 每页数量
     * @return 粉丝列表信息
     * @throws ByteErrorException 异常
     */
    ByteOpenFans getFansList(String openId, int cursor, int count) throws ByteErrorException;

    /**
     * <h3> 用户管理 - 获取关注列表 </h3>
     * @param openId 授权方帐号ID
     * @param cursor 分页游标
     * @param count 每页数量
     * @return 粉丝列表信息
     * @throws ByteErrorException 异常
     */
    ByteOpenFollow getFollowingList(String openId, int cursor, int count) throws ByteErrorException;

    /**
     * <h3> 用户管理 - 获取用户公开信息 </h3>
     * @param openId 用户ID
     * @return 帐号基本信息
     * @throws ByteErrorException 异常
     */
    ByteOpenAuthorizerInfo getAuthorizerInfo(String openId) throws ByteErrorException, UnsupportedEncodingException;

    /**
     * <h3> 用户管理 - 粉丝判断 </h3>
     * @param followerOpenId 需要检查的用户ID
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @return 关注信息
     * @throws ByteErrorException 异常
     */
    String fansCheck(String followerOpenId, String openId) throws ByteErrorException;

    /**
     * <h3> 用户管理 - 解密手机号码 </h3>
     * @param encryptedMobile 加密的手机号码
     * @return 解密后的手机号码
     */
    String decryptMobile(String encryptedMobile) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询授权账号视频列表 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量
     * @return 视频列表信息
     */
    ByteOpenUserVideoList getDouYinVideoList(String openId, int cursor, int count ) throws ByteErrorException;

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询特定视频的视频数据 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemsIds item_id数组，仅能查询access_token对应用户上传的视频
     * @return 视频数据
     * @throws ByteErrorException 异常信息
     */
    ByteOpenUserVideoData getDouYinVideoData(String openId, List<String> itemsIds) throws ByteErrorException;

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询抖音视频来源 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemsIds item_id数组，仅能查询access_token对应用户上传的视频
     * @return 视频来源信息
     * @throws ByteErrorException 异常
     */
    String getDouYinVideoSource(String openId, List<String> itemsIds) throws ByteErrorException;

    /**
     * <h3> 视频管理 - 抖音 - 查询视频 - 查询视频携带的地点信息 </h3>
     * @param keyword 查询关键字，例如美食
     * @param city 查询城市，例如上海、北京
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量（最大20）
     * @return 地点信息
     * @throws ByteErrorException 异常
     */
    String getDouYinVideoPoi( String keyword, String city, int cursor, int count ) throws ByteErrorException;

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
    String getItemCommentList(String openId, String itemId, String sortType, int cursor, int count) throws ByteErrorException;

    /**
     * <h3> 搜索管理 - 关键词视频搜索 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param keyword  关键词
     * @param count 每页数量
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @return 视频信息
     */
    String videoSearch(String openId, String keyword, int count, int cursor) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 用户数据 </h3>
     * @param type 数据类型[item:视频数据,fans:粉丝数据,like:点赞数,comment:评论数,share:分享数,profile:主页访问数]
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param dateType 近7/15天；输入7代表7天、15代表15天、30代表30天
     * @return 用户相关数据
     * @throws ByteErrorException 异常
     */
    ByteOpenUserItemResult getUserData(String type, String openId, int dateType) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 粉丝画像数据 </h3>
     * @param openId 用户ID
     * @param type 数据类型["":粉丝数据,source:粉丝来源,favourite:粉丝喜好,comment:粉丝热评]
     * @return 粉丝数据
     * @throws ByteErrorException 异常
     */
    String getFansData(String type, String openId) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 视频数据 </h3>
     * @param type 数据类型：[base:基础数据,like:点赞数据,comment:评论数据,play:播放数据,share:分享数据]
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param itemId item_id，仅能查询access_token对应用户上传的视频
     * @param dateType 近7/15天；输入7代表7天、15代表15天
     * @return 视频相关数据
     * @throws ByteErrorException 异常
     */
    String getItemData(String type, String openId, String itemId, int dateType) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人热榜 </h3>
     * @param hotListType 达人热榜类型 * `1` - 星图指数榜 * `2` - 涨粉指数榜 * `3` - 性价比指数榜 * `4` - 种草指数榜 * `5` - 精选指数榜 * `6` - 传播指数榜
     * @return 抖音星图达人热榜
     */
    ByteOpenStarHotListResult getStarHotList(int hotListType) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人指数 </h3>
     * @param openId 用户ID
     * @return 达人指数
     */
    ByteOpenStarAuthorScore getStarTopScoreDisplay(String openId) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 星图数据 - 获取抖音星图达人指数数据V2</h3>
     * @param uniqueId 达人抖音号
     * @return 达人指数数据
     */
    ByteOpenStarAuthorScore getStarAuthorScoreDisplay(String uniqueId) throws ByteErrorException;


    /**
     * <h3> 数据开放服务 - 抖音影视综艺榜单数据 - 获取抖音电影榜、抖音电视剧榜、抖音综艺榜 </h3>
     * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @param version 榜单版本：0默认为本周榜单
     * @return 榜单数据
     */
    ByteOpenRankItemResult getDiscoveryEntRankItem(int type, int version) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 抖音影视综艺榜单数据 - 获取抖音影视综榜单版本 </h3>
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量(最大20)
     * @param type 榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @return 榜单版本数据
     */
    ByteOpenRankVersionResult getDiscoveryEntRankVersion(int cursor, int count, int type) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取实时热点词 </h3>
     * <p> 热点榜约每两个小时刷新一次 </p>
     * @return 热点词
     * @throws ByteErrorException 异常
     */
    String getHotSearchSentences() throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取上升词</h3>
     * @param cursor 分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量（最大为20）
     * @return 上升词
     * @throws ByteErrorException 异常
     */
    String getHotSearchTrendingSentences(int cursor, int count) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 热点视频数据 - 获取热点词聚合的视频 </h3>
     * @param hotSentence 热点词
     * @return 视频信息
     * @throws ByteErrorException 异常
     */
    String getHotSearchVideos(String hotSentence) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 榜单数据 - 热门视频榜 </h3>
     * @return 热门视频榜单
     * @throws ByteErrorException 异常
     */
    ByteOpenHotVideoBillboardResult getHotVideoBillboard() throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 榜单数据 - 音乐榜数据 </h3>
     * @param billTye 音乐榜单类型  hot 热歌榜  soar 飙升榜 original 原创榜
     * @return 热歌榜数据
     * @throws ByteErrorException 异常
     */
    ByteOpenMusicBillboardList getMusicBillboard(String billTye) throws ByteErrorException;

    /**
     * <h3> 数据开放 - 榜单数据 - 话题榜单数据 </h3>
     * @return 话题榜单数据
     * @throws ByteErrorException 异常
     */
    ByteOpenTopicBillboardList getTopicBillboard() throws ByteErrorException;

    /**
     * <h3>数据开放 - 榜单数据 - 道具榜数据 </h3>
     * @return 道具榜数据
     * @throws ByteErrorException 异常
     */
    ByteOpenPropBillboardList getPropBillboard() throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 榜单数据 </h3>
     * @param cate 榜单类型 [hot_video:热门视频榜,sport:体育榜，amusement:搞笑榜,game:游戏榜,food:美食榜，drama:剧情榜,car:汽车榜,travel:旅游榜，cospa:二次元榜，stars:娱乐明星榜，live:直播榜，music:音乐榜，topic:话题榜，prop:道具榜]
     * @param type 榜单类型
     *          <p> 热门视频榜：[]</p>
     *          <p> 体育榜：[overall:总榜,basketball:篮球榜,soccer:足球榜,comprehensive:综合体育榜,fitness:运动健身榜,outdoors:户外运动榜,table_tennis:台球榜,culture:运动文化榜]</p>
     *          <p> 搞笑榜：[overall:总榜,new:新势力榜]</p>
     *          <p> 游戏榜：[console:单机主机榜,inf:游戏资讯榜]</p>
     *          <p> 美食榜：[overall:总榜,new:新势力榜,tutorial:教程榜,shop:探店榜]</p>
     *          <p> 剧情榜：[overall:总榜]</p>
     *          <p> 汽车榜：[overall:总榜,comment:评车,play:玩车,use:用车,driver:驾车]</p>
     *          <p> 旅游榜：[overall:总榜,new:新势力榜]</p>
     *          <p> 二次元：[overall:总榜,qing_man:轻漫,out_shot:出镜拍摄,painting:绘画,voice_control:声控,brain_cavity:脑洞,new:新势力榜]</p>
     *          <p> 娱乐明星：[]</p>
     *          <p> 直播榜：[]</p>
     *          <p> 音乐榜：[hot:热歌榜,soar:飙升榜,original:原创榜]</p>
     *          <p> 话题榜：[]</p>
     *          <p> 道具榜：[]</p>
     * @return 榜单数据
     * @throws ByteErrorException 异常
     */
    String getBillboard(String cate, String type) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 获取SDK分享视频数据 </h3>
     * @param beginDate  开始日期(yyyy-MM-dd)
     * @param endDate  结束日期(yyyy-MM-dd)
     * @return  分享数据
     * @throws ByteErrorException 异常
     */
    String getSDKShare(String beginDate, String endDate) throws ByteErrorException;

    /**
     * <h3> 数据开放服务 - 获取小程序点击量视频分布 </h3>
     * @param openId 通过/oauth/access_token/获取，用户唯一标志
     * @param mpId mp_id，小程序id
     * @param dateType 近7/15/30天；输入7代表7天、15代表15天、30代表30天
     * @return 视频分布
     * @throws ByteErrorException 异常
     */
    String getMpItemClickDistribution(String openId, String mpId, int dateType) throws ByteErrorException;

    String post(String uri, List<BasicNameValuePair> parameters) throws UnsupportedEncodingException;

    /**
     * <h3> POST 请求 </h3>
     * @param uri 请求地址
     * @param postData 请求参数
     * @param accessTokenKey 秘钥
     * @return 返回信息
     * @throws ByteErrorException 异常
     */
    String post(String uri, String postData, String accessTokenKey) throws ByteErrorException;

    String post(String uri, String openId, String postData, String accessTokenKey) throws ByteErrorException;

    String get(String uri, String accessTokenKey) throws ByteErrorException;

    String get(String uri, String openId, String accessTokenKey) throws ByteErrorException;
}
