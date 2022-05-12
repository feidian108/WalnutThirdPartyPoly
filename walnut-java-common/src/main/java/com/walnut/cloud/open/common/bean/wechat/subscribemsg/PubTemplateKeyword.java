package com.walnut.cloud.open.common.bean.wechat.subscribemsg;

import lombok.Data;

import java.io.Serializable;


@Data
public class PubTemplateKeyword implements Serializable {
  private static final long serialVersionUID = -1100641668859815647L;

  private int kid;
  private String name;
  private String example;
  private String rule;
}
