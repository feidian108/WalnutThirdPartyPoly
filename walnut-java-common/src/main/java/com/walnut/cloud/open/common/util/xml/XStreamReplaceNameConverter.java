package com.walnut.cloud.open.common.util.xml;

public class XStreamReplaceNameConverter extends XStreamCDataConverter {
  @Override
  public String toString(Object obj) {
    return "<ReplaceName>" + super.toString(obj) + "</ReplaceName>";
  }
}
