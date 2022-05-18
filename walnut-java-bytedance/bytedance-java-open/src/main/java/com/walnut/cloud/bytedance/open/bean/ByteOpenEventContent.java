package com.walnut.cloud.bytedance.open.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenEventContent implements Serializable {

    private static final long serialVersionUID = -2394736235020206855L;

    private String challenge;

    private String itemId;

    private String shareId;

    private String scopes;

    private String description;

    private String scene;

    private String object;

    private ByteOpenCommentContent commentContent;

}
