package com.walnut.cloud.bytedance.open.bean.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenFollow implements Serializable {

    private static final long serialVersionUID = 2394736235020206855L;

    private int cursor;

    private boolean hasMore;

    private int total;

    private List<ByteOpenUserInfo> list;
}
