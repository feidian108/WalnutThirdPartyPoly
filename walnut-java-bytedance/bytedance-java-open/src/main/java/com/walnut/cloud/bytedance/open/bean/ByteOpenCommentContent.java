package com.walnut.cloud.bytedance.open.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenCommentContent implements Serializable {

    private static final long serialVersionUID = -2394736235020206855L;

    private String commentId;

    private String commentUserId;

    private String content;

    private Long createTime;

    private Long diggCount;

    private Long replyCommentTotal;

    private String replyToCommentId;

    private String replyToItemId;

    private String atUserId;

}
