package com.walnut.cloud.open.common.util.http.okhttp;

import com.walnut.cloud.open.common.bean.wechat.result.WxMinishopImageUploadCustomizeResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.MinishopUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Slf4j
public class OkHttpMinishopMediaUploadRequestCustomizeExecutor extends MinishopUploadRequestCustomizeExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpMinishopMediaUploadRequestCustomizeExecutor(RequestHttp requestHttp, String respType, String imgUrl) {
    super(requestHttp, respType, imgUrl);
  }

  @Override
  public WxMinishopImageUploadCustomizeResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {

    RequestBody body = null;
    if (this.uploadType.equals("0")) {
      body = new MultipartBody.Builder()
              .setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")))
              .addFormDataPart("resp_type", this.respType)
              .addFormDataPart("upload_type", this.uploadType)
              .addFormDataPart("media", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
              .build();
    }
    else {
      body = new MultipartBody.Builder()
              .setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")))
              .addFormDataPart("resp_type", this.respType)
              .addFormDataPart("upload_type", this.uploadType)
              .addFormDataPart("img_url", this.imgUrl)
              .build();
    }
    Request request = new Request.Builder().url(uri).post(body).build();

    Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
    String responseContent = Objects.requireNonNull(response.body()).string();
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    log.info("responseContent: " + responseContent);

    return WxMinishopImageUploadCustomizeResult.fromJson(responseContent);
  }

}
