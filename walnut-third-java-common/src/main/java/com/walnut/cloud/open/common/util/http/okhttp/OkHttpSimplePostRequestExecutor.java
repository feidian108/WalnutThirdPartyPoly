package com.walnut.cloud.open.common.util.http.okhttp;

import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import com.walnut.cloud.open.common.util.http.SimplePostRequestExecutor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;


@Slf4j
public class OkHttpSimplePostRequestExecutor extends SimplePostRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpSimplePostRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public String execute(String uri, String postEntity, WxType wxType) throws WxErrorException, IOException {
    RequestBody body = RequestBody.Companion.create(postEntity, MediaType.parse("text/plain; charset=utf-8"));
    Request request = new Request.Builder().url(uri).post(body).build();
    Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
    return this.handleResponse(wxType, Objects.requireNonNull(response.body()).string());
  }

  @Override
  public String execute(String uri, Map<String, String> headers, String postEntity, ByteType byteType) throws ByteErrorException, IOException {
    RequestBody body = RequestBody.Companion.create(postEntity, MediaType.parse("text/plain; charset=utf-8"));
    Request request = new Request.Builder().url(uri).post(body).build();
    Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
    return this.handleResponse(byteType, Objects.requireNonNull(response.body()).string());
  }
}
