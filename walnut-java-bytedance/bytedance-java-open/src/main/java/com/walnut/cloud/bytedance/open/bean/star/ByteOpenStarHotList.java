package com.walnut.cloud.bytedance.open.bean.star;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenStarHotList implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String uniqueId;

    private int follower;

    private String nickName;

    private int rank;

    private float score;

    private String tags;
}
