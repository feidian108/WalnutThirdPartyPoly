package com.walnut.cloud.bytedance.open.bean.item;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenUserItemStatic implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private Integer diggCount;

    private Integer downloadCount;

    private Integer playCount;

    private Integer shareCount;

    private Integer forwardCount;

    private Integer commentCount;
}
