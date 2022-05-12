package com.walnut.cloud.open.common.util.http;

import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteError;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.apache.ApacheSimplePostRequestExecutor;
import com.walnut.cloud.open.common.util.http.jodd.JoddHttpSimplePostRequestExecutor;
import com.walnut.cloud.open.common.util.http.okhttp.OkHttpSimplePostRequestExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 */
public abstract class SimplePostRequestExecutor<H, P> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P> requestHttp;

  public SimplePostRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, String data, ResponseHandler<String> handler, WxType wxType)
    throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  @Override
  public void execute(String uri, Map<String, String> headers, String data, ResponseHandler<String> handler, ByteType byteType)
          throws ByteErrorException, IOException {
    handler.handle(this.execute(uri, headers, data, byteType));
  }

  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheSimplePostRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddHttpSimplePostRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkHttpSimplePostRequestExecutor(requestHttp);
      default:
        throw new IllegalArgumentException("非法请求参数");
    }
  }

  @NotNull
  public String handleResponse(WxType wxType, String responseContent) throws WxErrorException {
    if (responseContent.isEmpty()) {
      throw new WxErrorException("无响应内容");
    }

    if (responseContent.startsWith("<xml>")) {
      //xml格式输出直接返回
      return responseContent;
    }

    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return responseContent;
  }

  @NotNull
  public String handleResponse(ByteType byteType, String responseContent) throws ByteErrorException {
    if (responseContent.isEmpty()) {
      throw new ByteErrorException("无响应内容");
    }
    ByteError error = ByteError.fromJson(responseContent, byteType);

    if (error.getErrorCode() != 0) {
      throw new ByteErrorException(error);
    }
    return responseContent;
  }
}
