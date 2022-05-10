package com.walnut.cloud.open.common.util.http;

public interface ResponseHandler<T> {
  /**
   * 响应结果处理.
   *
   * @param t 要处理的对象
   */
  void handle(T t);
}
