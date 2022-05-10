package com.walnut.cloud.open.common.util.http.apache;


import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import com.walnut.cloud.open.common.util.http.SimpleGetRequestExecutor;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Map;

public class ApacheSimpleGetRequestExecutor extends SimpleGetRequestExecutor<CloseableHttpClient, HttpHost> {
  public ApacheSimpleGetRequestExecutor(RequestHttp requestHttp) {
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
    HttpGet httpGet = new HttpGet(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
      httpGet.setConfig(config);
    }

    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      return handleResponse(wxType, responseContent);
    } finally {
      httpGet.releaseConnection();
    }
  }

  @Override
  public String execute(String uri, Map<String, String> headers, String queryParam, ByteType byteType) throws ByteErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    HttpGet httpGet = new HttpGet(uri);
    if( headers != null ) {
      headers.keySet().forEach(key-> httpGet.addHeader(key, headers.get(key)));
    }
    if (requestHttp.getRequestHttpProxy() != null) {
      RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
      httpGet.setConfig(config);
    }

    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      return handleResponse(byteType, responseContent);
    } finally {
      httpGet.releaseConnection();
    }
  }

}
