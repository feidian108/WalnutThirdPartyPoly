package com.walnut.cloud.open.common.bean.wechat.ocr;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class WxOcrPos implements Serializable {
  private static final long serialVersionUID = 4204160206873907920L;

  @SerializedName("left_top")
  private Coordinate leftTop;
  @SerializedName("right_top")
  private Coordinate rightTop;
  @SerializedName("right_bottom")
  private Coordinate rightBottom;
  @SerializedName("left_bottom")
  private Coordinate leftBottom;

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }

  @Data
  public static class Coordinate implements Serializable {
    private static final long serialVersionUID = 8675059935386304399L;
    @SerializedName("x")
    private int x;
    @SerializedName("y")
    private int y;

    @Override
    public String toString() {
      return WxGsonBuilder.create().toJson(this);
    }
  }
}
