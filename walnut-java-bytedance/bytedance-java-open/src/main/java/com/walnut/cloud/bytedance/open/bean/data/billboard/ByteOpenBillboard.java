package com.walnut.cloud.bytedance.open.bean.data.billboard;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String nickname;

    private String avatar;

    private Long followerCount;

    private int onbillbaordTimes;

    private double effectValue;

    private int rank;

    private String rankChange;

    private List<ByteOpenVideo> videoList;
}
