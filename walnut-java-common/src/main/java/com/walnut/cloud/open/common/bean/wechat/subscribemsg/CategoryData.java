package com.walnut.cloud.open.common.bean.wechat.subscribemsg;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryData implements Serializable {
  private static final long serialVersionUID = -5935548352317679892L;

  private int id;
  private String name;
}
