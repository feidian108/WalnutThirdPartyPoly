package com.walnut.cloud.open.common.util.http.okhttp;

import com.walnut.cloud.open.common.util.http.InputStreamData;
import com.walnut.cloud.open.common.bean.wechat.result.WxMediaUploadResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.MediaInputStreamUploadRequestExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class OkHttpMediaInputStreamUploadRequestExecutor extends MediaInputStreamUploadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpMediaInputStreamUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMediaUploadResult execute(String uri, InputStreamData data, WxType wxType) throws WxErrorException, IOException {

    RequestBody body = new MultipartBody.Builder()
      .setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")))
      .addFormDataPart("media", data.getFilename(), RequestBody.create(this.toByteArray(data.getInputStream()), MediaType.parse("application/octet-stream")))
      .build();
    Request request = new Request.Builder().url(uri).post(body).build();

    Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
    String responseContent = Objects.requireNonNull(response.body()).string();
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
