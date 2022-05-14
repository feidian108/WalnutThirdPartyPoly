package com.walnut.cloud.bytedance.open.bean.result;

import com.walnut.cloud.bytedance.open.bean.data.ent.ByteOpenRankVersion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenRankVersionResult implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private int errorCode;

    private String description;

    private int cursor;

    private boolean hasMore;

    private List<ByteOpenRankVersion> list;
}
