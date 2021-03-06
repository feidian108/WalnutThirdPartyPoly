package com.walnut.cloud.bytedance.open.bean.result;


import com.walnut.cloud.bytedance.open.bean.data.ent.ByteOpenRankItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenRankItemResult implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String activeTime;

    private int errorCode;

    private String description;

    private List<ByteOpenRankItem> list;
}
