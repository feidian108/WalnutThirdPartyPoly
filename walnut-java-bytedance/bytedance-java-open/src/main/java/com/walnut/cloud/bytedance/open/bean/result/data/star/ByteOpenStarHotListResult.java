package com.walnut.cloud.bytedance.open.bean.result.data.star;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenStarHotListResult implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String hotListDescription;

    private String hotListType;

    private int hotListUpdateTimestamp;

    private List<ByteOpenStarHotList> list;

}
