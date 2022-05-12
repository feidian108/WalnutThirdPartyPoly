package com.walnut.cloud.bytedance.open.api;


import com.walnut.cloud.bytedance.open.bean.ByteOpenAccessToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenClientToken;
import com.walnut.cloud.bytedance.open.config.ByteOpenHostConfig;
import com.walnut.cloud.open.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.concurrent.locks.Lock;


public interface ByteOpenConfigStorage {


    String getClientKey();

    void setClientKey(String clientKey);


    String getClientSecret();


    void setClientSecret(String clientSecret);

    String getClientToken();

    boolean isClientTokenExpired();

    void expireClientToken();

    void updateClientToken(ByteOpenClientToken clientToken);

    void updateClientToken(String clientToken, int expiresInSeconds);

    Lock getClientTokenLock();

    /**
     * Gets http proxy host.
     *
     * @return the http proxy host
     */
    String getHttpProxyHost();

    /**
     * Gets http proxy port.
     *
     * @return the http proxy port
     */
    int getHttpProxyPort();

    /**
     * Gets http proxy username.
     *
     * @return the http proxy username
     */
    String getHttpProxyUsername();

    /**
     * Gets http proxy password.
     *
     * @return the http proxy password
     */
    String getHttpProxyPassword();

    /**
     * http 请求重试间隔
     */
    int getRetrySleepMillis();

    /**
     * http 请求最大重试次数
     */
    int getMaxRetryTimes();

    /**
     * Gets apache http client builder.
     *
     * @return the apache http client builder
     */
    ApacheHttpClientBuilder getApacheHttpClientBuilder();


    Lock getAccessTokenLock(String openId);
    /**
     * Gets lock by key.
     *
     * @param key the key
     * @return the lock by key
     */
    Lock getLockByKey(String key);


    /**
     * 是否自动刷新token
     *
     * @return the boolean
     */
    boolean autoRefreshToken();

    /**
     * Gets authorizer refresh token.
     *
     * @param openId the openId
     * @return the authorizer refresh token
     */
    String getRefreshToken(String openId);

    /**
     * Sets authorizer refresh token.
     *
     * @param openId                  the openId
     * @param authorizerRefreshToken the authorizer refresh token
     */
    void setRefreshToken(String openId, String authorizerRefreshToken, int expiresInSeconds);

    /**
     * setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) 方法重载方法
     *
     * @param openId                  the openId
     * @param authorizerRefreshToken the authorizer refresh token
     */
    void updateRefreshToken(String openId, String authorizerRefreshToken, int expiresInSeconds);

    /**
     * Gets authorizer access token.
     *
     * @param openId the openId
     * @return the authorizer access token
     */
    String getAccessToken(String openId);

    /**
     * Is access token expired boolean.
     *
     * @param openId the openId
     * @return the boolean
     */
    boolean isAccessTokenExpired(String openId);

    /**
     * 强制将access token过期掉
     *
     * @param openId the openId
     */
    void expireAccessToken(String openId);

    /**
     * 应该是线程安全的
     *
     * @param openId                 the openId
     * @param accessToken 要更新的WxAccessToken对象
     */
    void updateAccessToken(String openId, ByteOpenAccessToken accessToken);

    /**
     * 应该是线程安全的
     *
     * @param openId               the openId
     * @param authorizerAccessToken 新的accessToken值
     * @param expiresInSeconds      过期时间，以秒为单位
     */
    void updateAccessToken(String openId, String authorizerAccessToken, int expiresInSeconds);

    /**
     * 设置抖音开放平台基础信息
     *
     * @param clientKey     开放平台 clientKey
     * @param clientSecret 开放平台 clientSecret
     */
    void setByteOpenInfo(String clientKey, String clientSecret);

    ByteOpenHostConfig getHostConfig();

    /**
     * 设置字节跳动接口地址域名部分的自定义设置信息.
     *
     * @param hostConfig host config
     */
    void setHostConfig(ByteOpenHostConfig hostConfig);

    /**
     * Is RefreshToken expired boolean.
     *
     * @param openId the openId
     * @return the boolean
     */
    boolean isRefreshTokenExpired(String openId);
}
