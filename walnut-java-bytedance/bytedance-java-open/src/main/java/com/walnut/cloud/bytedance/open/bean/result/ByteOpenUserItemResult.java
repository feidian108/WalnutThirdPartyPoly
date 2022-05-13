package com.walnut.cloud.bytedance.open.bean.result;

import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserItemData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenUserItemResult implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private List<ByteOpenUserItemData> list;
}
