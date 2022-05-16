package com.walnut.cloud.bytedance.open.bean.data.billboard;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenBillboardList implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private int errorCode;

    private String description;

    private List<ByteOpenBillboard> list;
}
