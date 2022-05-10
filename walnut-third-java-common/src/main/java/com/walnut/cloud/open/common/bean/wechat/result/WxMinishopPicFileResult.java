package com.walnut.cloud.open.common.bean.wechat.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxMinishopPicFileResult implements Serializable {
  private String mediaId;
  private String payMediaId;
}
