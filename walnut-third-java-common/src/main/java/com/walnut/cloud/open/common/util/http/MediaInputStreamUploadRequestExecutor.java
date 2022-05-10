package com.walnut.cloud.open.common.util.http;

import com.walnut.cloud.open.common.util.http.apache.ApacheMediaInputStreamUploadRequestExecutor;
import com.walnut.cloud.open.common.util.http.jodd.JoddHttpMediaInputStreamUploadRequestExecutor;
import com.walnut.cloud.open.common.util.http.okhttp.OkHttpMediaInputStreamUploadRequestExecutor;
import com.walnut.cloud.open.common.bean.wechat.result.WxMediaUploadResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;

import java.io.IOException;


public abstract class MediaInputStreamUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaUploadResult, InputStreamData> {
  protected RequestHttp<H, P> requestHttp;

  public MediaInputStreamUploadRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, InputStreamData data, ResponseHandler<WxMediaUploadResult> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  public static RequestExecutor<WxMediaUploadResult, InputStreamData> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaInputStreamUploadRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddHttpMediaInputStreamUploadRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkHttpMediaInputStreamUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
