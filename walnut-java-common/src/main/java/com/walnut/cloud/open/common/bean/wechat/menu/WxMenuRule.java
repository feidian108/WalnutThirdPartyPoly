package com.walnut.cloud.open.common.bean.wechat.menu;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * menu rule.
 *
 * @author Daniel Qian
 */
@Data
public class WxMenuRule implements Serializable {
  private static final long serialVersionUID = -4587181819499286670L;

  /**
   * 变态的微信接口，反序列化时这里反人类的使用和序列化时不一样的名字.
   */
  @SerializedName(value = "tag_id", alternate = "group_id")
  private String tagId;
  private String sex;
  private String country;
  private String province;
  private String city;
  @SerializedName("client_platform_type")
  private String clientPlatformType;
  private String language;

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
