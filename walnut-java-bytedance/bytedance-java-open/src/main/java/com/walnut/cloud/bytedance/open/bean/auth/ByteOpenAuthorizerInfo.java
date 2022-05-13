package com.walnut.cloud.bytedance.open.bean.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class ByteOpenAuthorizerInfo implements Serializable {

    private static final long serialVersionUID = 2394736235020206855L;

    private int errorCode;

    private String description;

    private String clientKey;

    private String unionId;

    private String openId;

    private String nickname;

    private int gender;

    private String avatar;

    private String avatarLarger;

    private String captcha;

    private String country;

    private String province;

    private String city;

    private String descUrl;

    private String district;

    private String eAccountRole;

    private String logId;


}
