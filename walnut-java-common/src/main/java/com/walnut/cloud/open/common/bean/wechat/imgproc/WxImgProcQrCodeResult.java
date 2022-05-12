package com.walnut.cloud.open.common.bean.wechat.imgproc;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class WxImgProcQrCodeResult implements Serializable {
  private static final long serialVersionUID = -1194154790100866123L;

  @SerializedName("img_size")
  private ImgSize imgSize;

  @SerializedName("code_results")
  private List<CodeResults> codeResults;

  @Data
  public static class ImgSize implements Serializable {
    private static final long serialVersionUID = -8847603245514017839L;

    @SerializedName("w")
    private int w;
    @SerializedName("h")
    private int h;

    @Override
    public String toString() {
      return WxGsonBuilder.create().toJson(this);
    }
  }

  @Data
  public static class CodeResults implements Serializable {
    private static final long serialVersionUID = -6138135951229076759L;

    @SerializedName("type_name")
    private String typeName;

    @SerializedName("data")
    private String data;

    @SerializedName("pos")
    private Pos pos;

    @Override
    public String toString() {
      return WxGsonBuilder.create().toJson(this);
    }

    @Data
    public static class Pos implements Serializable {
      private static final long serialVersionUID = 7754894061212819602L;
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
        private static final long serialVersionUID = 8930443668927359677L;
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
  }

  public static WxImgProcQrCodeResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxImgProcQrCodeResult.class);
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
