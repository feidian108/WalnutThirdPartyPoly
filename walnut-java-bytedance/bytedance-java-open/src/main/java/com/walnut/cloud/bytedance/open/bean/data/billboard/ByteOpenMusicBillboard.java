package com.walnut.cloud.bytedance.open.bean.data.billboard;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenMusicBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String title;

    private String cover;

    private String author;

    private String shareUrl;

    private int duration;

    private Long useCount;

    private int rank;
}
