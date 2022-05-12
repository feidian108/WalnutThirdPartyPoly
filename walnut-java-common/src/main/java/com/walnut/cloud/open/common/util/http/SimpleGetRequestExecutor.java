package com.walnut.cloud.open.common.util.http;

import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteError;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.apache.ApacheSimpleGetRequestExecutor;
import com.walnut.cloud.open.common.util.http.jodd.JoddHttpSimpleGetRequestExecutor;
import com.walnut.cloud.open.common.util.http.okhttp.OkHttpSimpleGetRequestExecutor;

import java.io.IOException;
import java.util.Map;

/**
 * 简单的GET请求执行器.
 * 请求的参数是String, 返回的结果也是String
 *
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P> requestHttp;

  public SimpleGetRequestExecutor(RequestHttp<H, P> requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, String data, ResponseHandler<String> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  @Override
  public void execute(String uri, Map<String, String> headers, String data, ResponseHandler<String> handler, ByteType wxType) throws ByteErrorException, IOException {
    handler.handle(this.execute(uri, headers, data, wxType));
  }

  public static RequestExecutor<String, String> create(RequestHttp<?, ?> requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheSimpleGetRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddHttpSimpleGetRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkHttpSimpleGetRequestExecutor(requestHttp);
      default:
        throw new IllegalArgumentException("非法请求参数");
    }
  }

  protected String handleResponse(WxType wxType, String responseContent) throws WxErrorException {
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }

    return responseContent;
  }

  protected String handleResponse(ByteType byteType, String responseContent) throws ByteErrorException {
    ByteError error = ByteError.fromJson(responseContent, byteType);
    if (error.getErrorCode() != 0) {
      throw new ByteErrorException(error);
    }

    return responseContent;
  }
}
