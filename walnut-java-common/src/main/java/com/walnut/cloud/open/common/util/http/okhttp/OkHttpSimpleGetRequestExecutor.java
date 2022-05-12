package com.walnut.cloud.open.common.util.http.okhttp;

import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import com.walnut.cloud.open.common.util.http.SimpleGetRequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;


public class OkHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
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

    //得到httpClient
    OkHttpClient client = requestHttp.getRequestHttpClient();
    Request request = new Request.Builder().url(uri).build();
    Response response = client.newCall(request).execute();
    return this.handleResponse(wxType, Objects.requireNonNull(response.body()).string());
  }

  @Override
  public String execute(String uri, Map<String, String> headers, String queryParam, ByteType byteType) throws ByteErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    //得到httpClient
    OkHttpClient client = requestHttp.getRequestHttpClient();
    Request request = new Request.Builder().url(uri).build();
    Response response = client.newCall(request).execute();
    return this.handleResponse(byteType, Objects.requireNonNull(response.body()).string());
  }

}
