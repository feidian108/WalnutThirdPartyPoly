package com.walnut.cloud.bytedance.open.bean.data.billboard;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenTopicBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private int rank;

    private String rankChange;

    private String title;

    private double effectValue;
}
