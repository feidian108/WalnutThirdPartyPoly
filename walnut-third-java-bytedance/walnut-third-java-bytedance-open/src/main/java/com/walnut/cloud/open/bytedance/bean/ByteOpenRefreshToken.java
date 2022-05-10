package com.walnut.cloud.open.bytedance.bean;


import com.walnut.cloud.open.bytedance.util.json.ByteOpenGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenRefreshToken implements Serializable {

    private static final long serialVersionUID = -4069745419280727420L;

    private String logId;

    private Integer errorCode;

    private String description;

    private String refreshToken;

    private String captcha;

    private String descUrl;

    private int expiresIn = -1;

    public static ByteOpenRefreshToken fromJson(String json) {
        return ByteOpenGsonBuilder.create().fromJson(json, ByteOpenRefreshToken.class);
    }
}
