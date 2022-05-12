package com.walnut.cloud.bytedance.open.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ByteOpenHostConfig {

    public static final String API_DEFAULT_HOST_URL = "https://open.douyin.com";
    public static final String TOU_DEFAULT_HOST_URL = "https://open.snssdk.com";
    public static final String GUA_DEFAULT_HOST_URL = "https://open-api.ixigua.com";

    /**
     * 对应于：https://open.douyin.com
     */
    private String apiHost;

    /**
     * 对应于：https://open.snssdk.com
     */
    private String touHost;

    /**
     * 对应于：https://open-api.ixigua.com
     */
    private String guaHost;

    public static String buildUrl(ByteOpenHostConfig hostConfig, String prefix, String path) {

        if (hostConfig == null) {
            return prefix + path;
        }
        if (hostConfig.getApiHost() != null && prefix.equals(API_DEFAULT_HOST_URL)) {
            return hostConfig.getApiHost() + path;
        }

        if (hostConfig.getTouHost() != null && prefix.equals(TOU_DEFAULT_HOST_URL)) {
            return hostConfig.getTouHost() + path;
        }

        if (hostConfig.getGuaHost() != null && prefix.equals(GUA_DEFAULT_HOST_URL)) {
            return hostConfig.getGuaHost() + path;
        }

        return prefix + path;
    }
}
