package com.walnut.cloud.open.common.session;

import java.util.Enumeration;

/**
 * @author Daniel Qian
 */
public interface WxSession {

  Object getAttribute(String name);

  Enumeration<String> getAttributeNames();

  void setAttribute(String name, Object value);

  void removeAttribute(String name);

  void invalidate();

}
