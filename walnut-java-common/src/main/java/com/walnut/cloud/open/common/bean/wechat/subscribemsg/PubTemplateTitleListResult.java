package com.walnut.cloud.open.common.bean.wechat.subscribemsg;

import com.walnut.cloud.open.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ArBing
 */
@Data
public class PubTemplateTitleListResult implements Serializable {
  private static final long serialVersionUID = -7718911668757837527L;

  private int count;

  private List<TemplateItem> data;

  public static PubTemplateTitleListResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, PubTemplateTitleListResult.class);
  }

  @Data
  public static class TemplateItem implements Serializable {
    private static final long serialVersionUID = 6888726696879905332L;

    private Integer type;

    private Integer tid;

    private String categoryId;

    private String title;
  }
}
