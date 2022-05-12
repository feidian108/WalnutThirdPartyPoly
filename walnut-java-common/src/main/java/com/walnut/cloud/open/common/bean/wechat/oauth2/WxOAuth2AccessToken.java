package com.walnut.cloud.open.common.bean.wechat.oauth2;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
 *
 * @author Daniel Qian
 */
@Data
public class WxOAuth2AccessToken implements Serializable {
  private static final long serialVersionUID = -1345910558078620805L;

  @SerializedName("access_token")
  private String accessToken;

  @SerializedName("expires_in")
  private int expiresIn = -1;

  @SerializedName("refresh_token")
  private String refreshToken;

  @SerializedName("openid")
  private String openId;

  @SerializedName("scope")
  private String scope;

  /**
   * https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11513156443eZYea&version=&lang=zh_CN.
   * 本接口在scope参数为snsapi_base时不再提供unionID字段。
   */
  @SerializedName("unionid")
  private String unionId;

  public static WxOAuth2AccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxOAuth2AccessToken.class);
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
