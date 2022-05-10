package com.walnut.cloud.open.bytedance.api.impl;


import com.walnut.cloud.open.bytedance.api.ByteOpenConfigStorage;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.util.http.HttpType;
import com.walnut.cloud.open.common.util.http.SimpleGetRequestExecutor;
import com.walnut.cloud.open.common.util.http.SimplePostRequestExecutor;
import com.walnut.cloud.open.common.util.http.apache.ApacheHttpClientBuilder;
import com.walnut.cloud.open.common.util.http.apache.DefaultApacheHttpClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;


@Slf4j
public class ByteOpenServiceApacheHttpClientImpl extends ByteOpenServiceAbstractImpl<CloseableHttpClient, HttpHost> {

    private CloseableHttpClient httpClient;

    private HttpHost httpProxy;

    @Override
    public void initHttp() {
        ByteOpenConfigStorage configStorage = this.getByteOpenConfigStorage();
        ApacheHttpClientBuilder apacheHttpClientBuilder = configStorage.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(configStorage.getHttpProxyHost())
                .httpProxyPort(configStorage.getHttpProxyPort())
                .httpProxyUsername(configStorage.getHttpProxyUsername())
                .httpProxyPassword(configStorage.getHttpProxyPassword());

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();

    }

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }

    @Override
    public String get(String url, Map<String, String> headers, String queryParam) throws ByteErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, headers, queryParam);
    }

    @Override
    public String post(String url, Map<String, String> headers, String postData) throws ByteErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, headers, postData);
    }
}
