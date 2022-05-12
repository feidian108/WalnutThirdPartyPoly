package com.walnut.cloud.open.common.bean.wechat.subscribemsg;

import lombok.Data;

import java.io.Serializable;


@Data
public class TemplateInfo implements Serializable {
  private static final long serialVersionUID = 6971785763573992264L;

  private String priTmplId;
  private String title;
  private String content;
  private String example;
  private int type;
}
