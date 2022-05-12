package com.walnut.cloud.open.bytedance.bean.result.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenFansInfo implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String openId;

    private String unionId;

    private String nickname;

    private String avatar;

    private String country;

    private String province;

    private String city;

    private int gender;
}
