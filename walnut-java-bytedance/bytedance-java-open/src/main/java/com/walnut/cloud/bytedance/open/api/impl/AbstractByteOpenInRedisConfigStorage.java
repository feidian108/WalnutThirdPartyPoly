package com.walnut.cloud.bytedance.open.api.impl;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractByteOpenInRedisConfigStorage extends ByteOpenInMemoryConfigStorage {

    protected static final String REFRESH_TOKEN_KEY = "bytedance_open_refresh_token:";
    protected static final String ACCESS_TOKEN_KEY = "bytedance_open_access_token:";

    protected static final String CLIENT_TOKEN_KEY = "bytedance_open_client_token:";

    protected static final String LOCK_KEY = "bytedance_lock:";

    protected String keyPrefix;

    protected String clientTokenKey;
    protected String refreshTokenKey;
    protected String accessTokenKey;
    protected String lockKey;

    @Override
    public void setClientKey(String clientKey) {
        super.setClientKey(clientKey);
        String prefix = StringUtils.isBlank(keyPrefix) ? "" :
                (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        clientTokenKey = prefix + CLIENT_TOKEN_KEY.concat(clientKey);
        refreshTokenKey = prefix + REFRESH_TOKEN_KEY.concat(clientKey);
        accessTokenKey = prefix + ACCESS_TOKEN_KEY.concat(clientKey);
        lockKey = prefix + LOCK_KEY.concat(clientKey);

    }

    protected String getKey(String prefix, String openId) {

        return prefix.endsWith(":") ? prefix.concat(openId) : prefix.concat(":").concat(openId);
    }
}
