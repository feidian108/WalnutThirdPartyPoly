package com.walnut.cloud.open.common.bean.wechat.ocr;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class WxOcrCommResult implements Serializable {
  private static final long serialVersionUID = 455833771627756440L;

  @SerializedName("img_size")
  private WxOcrImgSize imgSize;
  @SerializedName("items")
  private List<Items> items;

  public static WxOcrCommResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxOcrCommResult.class);
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }

  @Data
  public static class Items implements Serializable {
    private static final long serialVersionUID = 3066181677009102791L;

    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private WxOcrPos pos;

    @Override
    public String toString() {
      return WxGsonBuilder.create().toJson(this);
    }
  }
}
