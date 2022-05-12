package com.walnut.cloud.open.common.bean.wechat.ocr;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * OCR身份证识别结果.
 *
 */
@Data
public class WxOcrIdCardResult implements Serializable {
  private static final long serialVersionUID = 8184352486986729980L;

  @SerializedName("type")
  private String type;
  @SerializedName("name")
  private String name;
  @SerializedName("id")
  private String id;
  @SerializedName("valid_date")
  private String validDate;

  public static WxOcrIdCardResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxOcrIdCardResult.class);
  }

}
