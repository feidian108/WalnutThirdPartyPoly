package com.walnut.cloud.open.common.bean.wechat;

import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;


/**
 * access token.
 *
 * @author Daniel Qian
 */
@Data
public class WxAccessToken implements Serializable {
  private static final long serialVersionUID = 8709719312922168909L;

  private String accessToken;

  private int expiresIn = -1;

  public static WxAccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
  }

}
