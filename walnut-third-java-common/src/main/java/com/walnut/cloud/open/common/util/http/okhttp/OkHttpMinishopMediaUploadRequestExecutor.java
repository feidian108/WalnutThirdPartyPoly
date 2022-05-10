package com.walnut.cloud.open.common.util.http.okhttp;

import com.walnut.cloud.open.common.bean.wechat.result.WxMinishopImageUploadResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.MinishopUploadRequestExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Slf4j
public class OkHttpMinishopMediaUploadRequestExecutor extends MinishopUploadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpMinishopMediaUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMinishopImageUploadResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {

    RequestBody body = new MultipartBody.Builder()
      .setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")))
      .addFormDataPart("media",
        file.getName(),
        RequestBody.create(MediaType.parse("application/octet-stream"), file))
      .build();
    Request request = new Request.Builder().url(uri).post(body).build();

    Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
    String responseContent = response.body().string();
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    log.info("responseContent: " + responseContent);

    return WxMinishopImageUploadResult.fromJson(responseContent);
  }

}
