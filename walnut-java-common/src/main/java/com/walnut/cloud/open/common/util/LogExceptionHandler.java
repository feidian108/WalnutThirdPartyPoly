package com.walnut.cloud.open.common.util;

import com.walnut.cloud.open.common.api.wechat.WxErrorExceptionHandler;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Daniel Qian
 */
@Slf4j
public class LogExceptionHandler implements WxErrorExceptionHandler {
  @Override
  public void handle(WxErrorException e) {
    log.error("Error happens", e);
  }

}
