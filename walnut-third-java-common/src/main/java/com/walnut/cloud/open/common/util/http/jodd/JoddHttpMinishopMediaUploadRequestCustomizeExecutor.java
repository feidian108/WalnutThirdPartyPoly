package com.walnut.cloud.open.common.util.http.jodd;

import com.walnut.cloud.open.common.bean.wechat.result.WxMinishopImageUploadCustomizeResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.MinishopUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class JoddHttpMinishopMediaUploadRequestCustomizeExecutor extends MinishopUploadRequestCustomizeExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpMinishopMediaUploadRequestCustomizeExecutor(RequestHttp requestHttp, String respType, String imgUrl) {
    super(requestHttp, respType, imgUrl);
  }

  @Override
  public WxMinishopImageUploadCustomizeResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    if (this.uploadType.equals("0")) {
      request.form("resp_type", this.respType,
              "upload_type", this.uploadType,
              "media", file);
    }
    else {
      request.form("resp_type", this.respType,
              "upload_type", this.uploadType,
              "img_url", this.imgUrl);
    }
    HttpResponse response = request.send();
    response.charset(StandardCharsets.UTF_8.name());

    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    log.info("responseContent: " + responseContent);

    return WxMinishopImageUploadCustomizeResult.fromJson(responseContent);
  }
}
