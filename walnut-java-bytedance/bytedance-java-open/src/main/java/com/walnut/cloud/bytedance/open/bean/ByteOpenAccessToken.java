package com.walnut.cloud.bytedance.open.bean;

import com.walnut.cloud.bytedance.open.util.json.ByteOpenGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenAccessToken implements Serializable {

    private static final long serialVersionUID = -4069745419280727420L;

    private String logId;

    private Integer errorCode;

    private String accessToken;

    private int expiresIn = -1;

    private String refreshToken;

    private int refreshExpiresIn = -1;

    private String openId;

    private String captcha;

    private String description;

    private String descUrl;

    private String scope;

    public static ByteOpenAccessToken fromJson(String json) {
        return ByteOpenGsonBuilder.create().fromJson(json, ByteOpenAccessToken.class);
    }
}
