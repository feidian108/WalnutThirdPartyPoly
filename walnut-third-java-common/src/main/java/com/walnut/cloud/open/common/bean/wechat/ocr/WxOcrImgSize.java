package com.walnut.cloud.open.common.bean.wechat.ocr;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class WxOcrImgSize implements Serializable {
  private static final long serialVersionUID = 5234409123551074168L;

  @SerializedName("w")
  private int w;
  @SerializedName("h")
  private int h;

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
