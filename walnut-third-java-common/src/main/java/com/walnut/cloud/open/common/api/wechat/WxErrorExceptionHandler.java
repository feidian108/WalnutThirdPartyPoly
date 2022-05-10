package com.walnut.cloud.open.common.api.wechat;


import com.walnut.cloud.open.common.error.wechat.WxErrorException;

/**
 * WxErrorException处理器.
 *
 * @author Daniel Qian
 */
public interface WxErrorExceptionHandler {

  void handle(WxErrorException e);

}
