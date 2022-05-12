package com.walnut.cloud.open.bytedance.enums;


import com.walnut.cloud.open.bytedance.api.ByteOpenConfigStorage;
import com.walnut.cloud.open.bytedance.config.ByteOpenHostConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.walnut.cloud.open.bytedance.config.ByteOpenHostConfig.*;


public interface ByteOpenApiUrl {

    default String getUrl(ByteOpenConfigStorage config) {

        ByteOpenHostConfig hostConfig = null;
        if (config != null) {
            hostConfig = config.getHostConfig();
        }
        return buildUrl(hostConfig, this.getPrefix(), this.getPath());

    }

    String getPath();

    String getPrefix();

    @AllArgsConstructor
    @Getter
    enum OAuth2 implements ByteOpenApiUrl {

        DOU_OAUTH2_ACCESS_TOKEN_URL(API_DEFAULT_HOST_URL, "/platform/oauth/connect?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&optionalScope=%s&state=%s"),

        TOU_OAUTH2_ACCESS_TOKEN_URL(TOU_DEFAULT_HOST_URL, "/oauth/authorize?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&state=%s"),

        GUA_OAUTH2_ACCESS_TOKEN_URL(GUA_DEFAULT_HOST_URL, "/oauth/connect?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&optionalScope=%s&state=%s"),


        DOU_OAUTH_GET_ACCESS_TOKEN_URL(API_DEFAULT_HOST_URL, "/oauth/access_token/"),
        TOU_OAUTH_GET_ACCESS_TOKEN_URL(TOU_DEFAULT_HOST_URL, "/oauth/access_token/"),
        GUA_OAUTH_GET_ACCESS_TOKEN_URL(GUA_DEFAULT_HOST_URL, "/oauth/access_token/"),


        DOU_OAUTH_REFRESH_ACCESS_TOKEN_URL(API_DEFAULT_HOST_URL, "/oauth/refresh_token/"),
        DOU_OAUTH_RENEW_REFRESH_TOKEN_URL(API_DEFAULT_HOST_URL, "/oauth/renew_refresh_token/")

        ;

        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum User implements ByteOpenApiUrl {

        DOU_GET_FANS_LIST_URL(API_DEFAULT_HOST_URL, "/fans/list/?open_id=%s&cursor=%s&count=%s"),
        DOU_GET_FOLLOW_LIST_URL(API_DEFAULT_HOST_URL, "/following/list/?open_id=%s&cursor=%s&count=%s"),
        ;

        private final String prefix;
        private final String path;
    }
}
