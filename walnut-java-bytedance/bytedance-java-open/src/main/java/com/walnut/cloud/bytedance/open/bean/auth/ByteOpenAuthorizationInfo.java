package com.walnut.cloud.bytedance.open.bean.auth;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
public class ByteOpenAuthorizationInfo implements Serializable {

    private static final long serialVersionUID = -8713680081354754208L;

    private String logId;

    @JSONField(name ="error_code")
    private int errorCode;

    @JSONField(name ="description")
    private String description;

    @JSONField(name ="access_token")
    private String accessToken;

    @JSONField(name ="expires_in")
    private int expiresIn;

    @JSONField(name ="refresh_token")
    private String refreshToken;

    @JSONField(name ="refresh_expires_in")
    private int refreshExpiresIn;

    @JSONField(name ="open_id")
    private String openId;

    @JSONField(name ="scope")
    private String scope;

    @JSONField(name ="captcha")
    private String captcha;

    @JSONField(name ="desc_url")
    private String descUrl;
}
