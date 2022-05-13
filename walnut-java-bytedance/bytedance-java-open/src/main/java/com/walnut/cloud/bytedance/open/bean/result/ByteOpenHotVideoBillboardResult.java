package com.walnut.cloud.bytedance.open.bean.result;

import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenHotVideoBillboard;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ByteOpenHotVideoBillboardResult implements Serializable {

    private static final long serialVersionUID = 2394736235020206855L;

    private List<ByteOpenHotVideoBillboard> list;
}
