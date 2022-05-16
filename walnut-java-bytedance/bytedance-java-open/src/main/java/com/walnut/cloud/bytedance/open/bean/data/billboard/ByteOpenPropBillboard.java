package com.walnut.cloud.bytedance.open.bean.data.billboard;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenPropBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String rankChange;

    private String name;

    private String dailyIssuePercent;

    private int rank;

    private double shootCnt;

    private double dailyIssueCnt;

    private double dailyCollectionCnt;

    private double dailyPlayCnt;

    private double effectValue;

    private double showCnt;
}
