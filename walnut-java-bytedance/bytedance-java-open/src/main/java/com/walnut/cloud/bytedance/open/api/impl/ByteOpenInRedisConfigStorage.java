package com.walnut.cloud.bytedance.open.api.impl;


import com.walnut.cloud.open.common.redis.bytedance.ByteRedisOps;
import com.walnut.cloud.open.common.redis.bytedance.JedisByteRedisOps;
import lombok.NonNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.util.concurrent.TimeUnit;

public class ByteOpenInRedisConfigStorage extends AbstractByteOpenInRedisConfigStorage {

    private final ByteRedisOps redisOps;

    public ByteOpenInRedisConfigStorage(Pool<Jedis> jedisPool) {
        this(jedisPool, null);
    }

    public ByteOpenInRedisConfigStorage(@NonNull Pool<Jedis> jedisPool, String keyPrefix) {
        this(new JedisByteRedisOps(jedisPool), keyPrefix);
    }

    public ByteOpenInRedisConfigStorage(@NonNull ByteRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String getClientToken() {
        return redisOps.getValue(this.clientTokenKey);
    }

    @Override
    public boolean isClientTokenExpired() {
        Long expire = redisOps.getExpire(this.clientTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public void expireClientToken() {
        redisOps.expire(this.clientTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateClientToken(String clientToken, int expiresInSeconds) {
        redisOps.setValue(this.clientTokenKey, clientToken, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public String getRefreshToken(String openId) {
        return redisOps.getValue(this.getKey(this.refreshTokenKey, openId));
    }

    @Override
    public void updateAccessToken(String openId, String accessToken, int expiresInSeconds) {
        redisOps.setValue(this.getKey(this.accessTokenKey, openId), accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public void setRefreshToken(String open, String refreshToken, int expiresInSeconds) {
        redisOps.setValue(this.getKey(this.refreshTokenKey, open), refreshToken, expiresInSeconds * 5 - 200, TimeUnit.SECONDS);
    }


    @Override
    public String getAccessToken(String openId) {
        return redisOps.getValue(this.getKey(this.accessTokenKey, openId));
    }

    @Override
    public boolean isAccessTokenExpired(String openId) {
        Long expire = redisOps.getExpire(this.getKey(this.accessTokenKey, openId));
        return expire == null || expire < 2;
    }

    @Override
    public boolean isRefreshTokenExpired(String openId) {
        Long expire = redisOps.getExpire(this.getKey(this.refreshTokenKey, openId));
        return expire == null || expire < 2;
    }
}
