package com.walnut.cloud.bytedance.open.bean.item;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenUserItemData implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String date;

    private int newIssue;

    private int newPlay;

    private int totalIssue;
}
