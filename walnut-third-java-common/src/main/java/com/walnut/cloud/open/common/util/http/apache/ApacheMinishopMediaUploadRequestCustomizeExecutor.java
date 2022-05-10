package com.walnut.cloud.open.common.util.http.apache;

import com.walnut.cloud.open.common.bean.wechat.result.WxMinishopImageUploadCustomizeResult;
import com.walnut.cloud.open.common.enums.wechat.WxType;
import com.walnut.cloud.open.common.error.wechat.WxError;
import com.walnut.cloud.open.common.error.wechat.WxErrorException;
import com.walnut.cloud.open.common.util.http.MinishopUploadRequestCustomizeExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ApacheMinishopMediaUploadRequestCustomizeExecutor extends MinishopUploadRequestCustomizeExecutor<CloseableHttpClient, HttpHost> {
  public ApacheMinishopMediaUploadRequestCustomizeExecutor(RequestHttp requestHttp, String respType, String imgUrl) {
    super(requestHttp, respType, imgUrl);
  }

  @Override
  public WxMinishopImageUploadCustomizeResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
      httpPost.setConfig(config);
    }
    if (this.uploadType.equals("0")) {
      if (file == null) {
        throw new WxErrorException("上传文件为空");
      }
      HttpEntity entity = MultipartEntityBuilder
        .create()
        .addBinaryBody("media", file)
        .addTextBody("resp_type", this.respType)
        .addTextBody("upload_type", this.uploadType)
        .setMode(HttpMultipartMode.RFC6532)
        .build();
      httpPost.setEntity(entity);
    }
    else {
      HttpEntity entity = MultipartEntityBuilder
              .create()
              .addTextBody("resp_type", this.respType)
              .addTextBody("upload_type", this.uploadType)
              .addTextBody("img_url", this.imgUrl)
              .setMode(HttpMultipartMode.RFC6532)
              .build();
      httpPost.setEntity(entity);
    }
    try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent, wxType);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      log.info("responseContent: " + responseContent);
      return WxMinishopImageUploadCustomizeResult.fromJson(responseContent);
    } finally {
      httpPost.releaseConnection();
    }
  }
}
