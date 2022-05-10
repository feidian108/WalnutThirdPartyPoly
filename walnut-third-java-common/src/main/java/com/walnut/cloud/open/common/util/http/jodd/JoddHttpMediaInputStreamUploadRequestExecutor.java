package com.walnut.cloud.open.common.util.http.jodd;

import com.walnut.cloud.open.common.bean.wechat.result.WxMediaUploadResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.InputStreamData;
import com.walnut.cloud.open.common.util.http.MediaInputStreamUploadRequestExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.up.ByteArrayUploadable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 文件输入流上传.
 */
public class JoddHttpMediaInputStreamUploadRequestExecutor extends MediaInputStreamUploadRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpMediaInputStreamUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMediaUploadResult execute(String uri, InputStreamData data, WxType wxType) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    request.form("media", new ByteArrayUploadable(this.toByteArray(data.getInputStream()), data.getFilename()));
    HttpResponse response = request.send();
    response.charset(StandardCharsets.UTF_8.name());

    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return WxMediaUploadResult.fromJson(responseContent);
  }

  public byte[] toByteArray(InputStream input) throws IOException {
    try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[4096];
      int n;
      while (-1 != (n = input.read(buffer))) {
        output.write(buffer, 0, n);
      }
      return output.toByteArray();
    }
  }
}
