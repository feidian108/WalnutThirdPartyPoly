package com.walnut.cloud.bytedance.open.bean.data.billboard;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenHotVideoBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String author;

    private int diggCount;

    private String itemCover;

    private int playCount;

    private int commentCount;

    private String hotWords;

    private double hotValue;

    private int rank;

    private String shareUrl;

    private String title;
}
