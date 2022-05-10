package com.walnut.cloud.open.common.util.http;

import com.walnut.cloud.open.common.util.http.apache.ApacheMinishopMediaUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.util.http.jodd.JoddHttpMinishopMediaUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.util.http.okhttp.OkHttpMinishopMediaUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.bean.wechat.result.WxMinishopImageUploadCustomizeResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;

import java.io.File;
import java.io.IOException;

public abstract class MinishopUploadRequestCustomizeExecutor<H, P> implements RequestExecutor<WxMinishopImageUploadCustomizeResult, File> {
  protected RequestHttp<H, P> requestHttp;
  protected String respType;
  protected String uploadType;
  protected String imgUrl;

  public MinishopUploadRequestCustomizeExecutor(RequestHttp requestHttp, String respType, String imgUrl) {
    this.requestHttp = requestHttp;
    this.respType = respType;
    if (imgUrl == null || imgUrl.isEmpty()) {
      this.uploadType = "0";
    }
    else {
      this.uploadType = "1";
      this.imgUrl = imgUrl;
    }
  }

  @Override
  public void execute(String uri, File data, ResponseHandler<WxMinishopImageUploadCustomizeResult> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  public static RequestExecutor<WxMinishopImageUploadCustomizeResult, File> create(RequestHttp requestHttp, String respType, String imgUrl) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMinishopMediaUploadRequestCustomizeExecutor(requestHttp, respType, imgUrl);
      case JODD_HTTP:
        return new JoddHttpMinishopMediaUploadRequestCustomizeExecutor(requestHttp, respType, imgUrl);
      case OK_HTTP:
        return new OkHttpMinishopMediaUploadRequestCustomizeExecutor(requestHttp, respType, imgUrl);
      default:
        return null;
    }
  }
}
