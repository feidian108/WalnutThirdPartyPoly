package com.walnut.cloud.open.common.requestexecuter.ocr;


import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.RequestExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import com.walnut.cloud.open.common.util.http.ResponseHandler;

import java.io.File;
import java.io.IOException;

public abstract class OcrDiscernRequestExecutor<H, P> implements RequestExecutor<String, File> {
  protected RequestHttp<H, P> requestHttp;

  public OcrDiscernRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, File data, ResponseHandler<String> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  public static RequestExecutor<String, File> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new OcrDiscernApacheHttpRequestExecutor(requestHttp);
      default:
        return null;
    }
  }
}
