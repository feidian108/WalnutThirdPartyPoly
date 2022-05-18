package com.walnut.cloud.bytedance.open.bean;


import com.walnut.cloud.bytedance.open.util.json.ByteOpenGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenCallback implements Serializable {

    private static final long serialVersionUID = -4069745419280727420L;

    private String logId;

    private String eventId;

    private String event;

    private String clientKey;

    private String toUserId;

    private String fromUserId;

    private ByteOpenEventContent content;


    public static ByteOpenCallback fromJson(String json) {
        return ByteOpenGsonBuilder.create().fromJson(json, ByteOpenCallback.class);
    }
}
