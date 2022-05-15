package com.walnut.cloud.bytedance.open.bean.item;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenUserVideoList implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private Long cursor;

    private boolean hasMore;

    private List<ByteOpenUserVideo> list;
}
