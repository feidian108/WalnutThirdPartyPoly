package com.walnut.cloud.open.bytedance.bean;


import com.walnut.cloud.open.bytedance.util.json.ByteOpenGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenCallback implements Serializable {

    private static final long serialVersionUID = -4069745419280727420L;

    private String logId;

    private String eventId;

    private String event;

    private String clientKey;

    private String fromUserId;

    private String content;


    public static ByteOpenCallback fromJson(String json) {
        return ByteOpenGsonBuilder.create().fromJson(json, ByteOpenCallback.class);
    }
}
