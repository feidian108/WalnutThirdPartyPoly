package com.walnut.cloud.bytedance.open.bean.data.billboard;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenLiveBillboard implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private int rank;

    private String cover;

    private String title;

    private String nickname;

    private double hotValue;
}
