package com.walnut.cloud.bytedance.open.bean.data.ent;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenRankVersion implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String activeTime;

    private String endTime;

    private String startTime;

    private int type;

    private int version;
}
