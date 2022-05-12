package com.walnut.cloud.open.bytedance.bean.result.auth;


import com.walnut.cloud.open.bytedance.bean.result.ByteOpenAuthorizationInfo;
import lombok.Data;

import java.io.Serializable;


@Data
public class ByteOpenQueryAuthResult implements Serializable {

    private static final long serialVersionUID = 2394736235020206855L;

    private ByteOpenAuthorizationInfo data;

    private String message;
}