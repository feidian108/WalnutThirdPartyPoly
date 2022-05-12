package com.walnut.cloud.open.bytedance.bean;


import com.walnut.cloud.open.bytedance.util.json.ByteOpenGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenClientToken implements Serializable {

    private static final long serialVersionUID = -4069745419280727420L;

    private int errorCode;

    private String description;

    private String accessToken;

    private int expiresIn = -1;

    public static ByteOpenClientToken fromJson(String json) {
        return ByteOpenGsonBuilder.create().fromJson(json, ByteOpenClientToken.class);
    }
}
