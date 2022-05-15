package com.walnut.cloud.bytedance.open.bean.item;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenUserVideo implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String title;

    private Boolean isTop;

    private Boolean isReviewed;

    private Long createTime;

    private Integer mediaType;

    private String cover;

    private String itemId;

    private String shareUrl;

    private Integer videoStatus;

    private ByteOpenUserItemStatic statistics;
}
