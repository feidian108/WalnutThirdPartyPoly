package com.walnut.cloud.bytedance.open.api;

import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;

import java.util.Map;


public interface ByteOpenService {

    ByteOpenOauthService getByteOpenOauthService();

    ByteOpenConfigStorage getByteOpenConfigStorage();

    void setByteOpenConfigStorage(ByteOpenConfigStorage byteOpenConfigStorage);

    String get(String url, Map<String, String> headers, String queryParam) throws ByteErrorException;

    String post(String url, Map<String, String> headers, String postData) throws ByteErrorException;
}
