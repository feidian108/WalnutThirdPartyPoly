package com.walnut.cloud.open.common.util.http.jodd;

import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import com.walnut.cloud.open.common.util.http.SimpleGetRequestExecutor;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JoddHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public String execute(String uri, String queryParam, WxType wxType) throws WxErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    HttpRequest request = HttpRequest.get(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    HttpResponse response = request.send();
    response.charset(StandardCharsets.UTF_8.name());

    return handleResponse(wxType, response.bodyText());
  }

  @Override
  public String execute(String uri, Map<String, String> headers, String queryParam, ByteType byteType) throws ByteErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    HttpRequest request = HttpRequest.get(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    HttpResponse response = request.send();
    response.charset(StandardCharsets.UTF_8.name());

    return handleResponse(byteType, response.bodyText());
  }


}
