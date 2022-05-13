package com.walnut.cloud.bytedance.open.bean.data.star;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenStarAuthorScore implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String uniqueId;

    private String nickName;

    private int follower;

    private float cooperationScore;

    private float cpScore;

    private float growthScore;

    private float spreadScore;

    private float shopScore;

    private float starScore;

    private int updateTimestamp;
}
