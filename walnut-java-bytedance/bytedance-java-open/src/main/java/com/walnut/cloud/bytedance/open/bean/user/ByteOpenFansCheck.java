package com.walnut.cloud.bytedance.open.bean.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenFansCheck implements Serializable {

    private static final long serialVersionUID = -2394736235020206855L;

    private int errorCode;

    private String description;

    private boolean isFollower;

    private long followTime;
}
