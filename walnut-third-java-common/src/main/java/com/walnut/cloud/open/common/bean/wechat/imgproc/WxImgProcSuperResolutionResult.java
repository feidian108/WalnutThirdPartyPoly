package com.walnut.cloud.open.common.bean.wechat.imgproc;

import com.google.gson.annotations.SerializedName;
import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;


@Data
public class WxImgProcSuperResolutionResult implements Serializable {
  private static final long serialVersionUID = 8007440280170407021L;

  @SerializedName("media_id")
  private String mediaId;

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }

  public static WxImgProcSuperResolutionResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxImgProcSuperResolutionResult.class);
  }
}
