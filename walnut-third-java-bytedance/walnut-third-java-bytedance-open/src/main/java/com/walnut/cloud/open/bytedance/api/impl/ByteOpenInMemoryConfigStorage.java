package com.walnut.cloud.open.bytedance.api.impl;


import com.walnut.cloud.open.bytedance.api.ByteOpenConfigStorage;
import com.walnut.cloud.open.bytedance.bean.ByteOpenAccessToken;
import com.walnut.cloud.open.bytedance.bean.ByteOpenClientToken;
import com.walnut.cloud.open.bytedance.config.ByteOpenHostConfig;
import com.walnut.cloud.open.common.util.http.apache.ApacheHttpClientBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@Slf4j
public class ByteOpenInMemoryConfigStorage implements ByteOpenConfigStorage {

    private String clientKey;
    private String clientSecret;

    private volatile String openId;

    private String httpProxyHost;
    private int httpProxyPort;
    private String httpProxyUsername;
    private String httpProxyPassword;

    private String clientToken;
    private long clientExpiresTime;

    private int retrySleepMillis = 1000;

    private int maxRetryTimes = 5;
    private ApacheHttpClientBuilder apacheHttpClientBuilder;
    private ByteOpenHostConfig hostConfig = null;


    private Map<String, Lock> locks = new ConcurrentHashMap<>();

    private Map<String, Token> refreshTokens = new ConcurrentHashMap<>();
    private Map<String, Token> accessTokens = new ConcurrentHashMap<>();


    @Override
    public Lock getAccessTokenLock(String openId) {

        return this.locks.computeIfAbsent(String.join(":",
                "bytedance_open_access_token",
                this.clientKey, openId), key -> new ReentrantLock());

    }

    @Override
    public Lock getLockByKey(String key) {
        Lock lock = locks.get(key);
        if (lock == null) {
            synchronized (this) {
                lock = locks.get(key);
                if (lock == null) {
                    lock = new ReentrantLock();
                    locks.put(key, lock);
                }
            }
        }
        return lock;
    }

    @Override
    public boolean isClientTokenExpired() {
        return System.currentTimeMillis() > clientExpiresTime;
    }

    @Override
    public void expireClientToken() {
        this.clientExpiresTime = 0L;
    }

    private volatile Lock accessTokenLockInstance;

    @Override
    public Lock getClientTokenLock() {
        if (this.accessTokenLockInstance == null) {
            synchronized (this) {
                if (this.accessTokenLockInstance == null) {
                    this.accessTokenLockInstance = getLockByKey("clientTokenLock");
                }
            }
        }
        return this.accessTokenLockInstance;
    }

    @Override
    public void updateClientToken(ByteOpenClientToken clientToken) {
        updateClientToken(clientToken.getAccessToken(), clientToken.getExpiresIn());
    }

    @Override
    public void updateClientToken(String clientToken, int expiresInSeconds) {
        this.clientToken = clientToken;
        this.clientExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public void setByteOpenInfo(String clientKey, String clientSecret) {
        setClientKey(clientKey);
        setClientSecret(clientSecret);
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    private String getTokenString(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token == null || (token.expiresTime != null && System.currentTimeMillis() > token.expiresTime)) {
            return null;
        }
        return token.token;
    }

    private void expireToken(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token != null) {
            token.expiresTime = 0L;
        }
    }

    private void updateToken(Map<String, Token> map, String key, String tokenString, Integer expiresInSeconds) {
        Token token = map.get(key);
        if (token == null) {
            token = new Token();
            map.put(key, token);
        }
        token.token = tokenString;
        if (expiresInSeconds != null && expiresInSeconds != -1) {
            token.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
        }
    }

    @Override
    public String getRefreshToken(String openId) {
        return getTokenString(refreshTokens, openId);
    }

    @Override
    public void setRefreshToken(String openId, String refreshToken, int expiresInSeconds) {
        updateToken(refreshTokens, openId, refreshToken, expiresInSeconds * 5);
    }

    @Override
    public void updateRefreshToken(String openId, String refreshToken, int expiresInSeconds) {
        this.setRefreshToken(openId, refreshToken, expiresInSeconds);
    }

    @Override
    public String getAccessToken(String openId) {
        return getTokenString(accessTokens, openId);
    }


    @Override
    public boolean isAccessTokenExpired(String openId) {
        return getTokenString(accessTokens, openId) == null;
    }

    @Override
    public void expireAccessToken(String openId) {
        expireToken(accessTokens, openId);
    }

    @Override
    public void updateAccessToken(String openId, ByteOpenAccessToken accessToken) {
        updateAccessToken(openId, accessToken.getAccessToken(),
                accessToken.getExpiresIn());
    }

    @Override
    public void updateAccessToken(String openId, String accessToken, int expiresInSeconds) {
        updateToken(accessTokens, openId, accessToken, expiresInSeconds);
    }



    @Data
    private static class Token {
        private String token;
        private Long expiresTime;
    }


    @Override
    public ByteOpenHostConfig getHostConfig() {
        return this.hostConfig;
    }

    @Override
    public void setHostConfig(ByteOpenHostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    @Override
    public boolean isRefreshTokenExpired(String openId) {
        return getTokenString(refreshTokens, openId) == null;
    }
}
