package com.walnut.cloud.bytedance.open.api.impl;

import com.walnut.cloud.bytedance.open.api.ByteOpenConfigStorage;
import com.walnut.cloud.bytedance.open.api.ByteOpenOauthService;
import com.walnut.cloud.bytedance.open.api.ByteOpenService;
import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.error.bytedance.ByteError;
import com.walnut.cloud.open.common.error.bytedance.ByteErrorException;
import com.walnut.cloud.open.common.error.bytedance.ByteRuntimeException;
import com.walnut.cloud.open.common.util.http.RequestExecutor;
import com.walnut.cloud.open.common.util.http.RequestHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;


public abstract class ByteOpenServiceAbstractImpl<H, P> implements ByteOpenService, RequestHttp<H, P> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ByteOpenOauthService byteOpenOauthService = new ByteOpenOauthServiceImpl(this);


    private ByteOpenConfigStorage byteOpenConfigStorage;

    @Override
    public ByteOpenConfigStorage getByteOpenConfigStorage() {
        return byteOpenConfigStorage;
    }

    @Override
    public ByteOpenOauthService getByteOpenOauthService() {
        return byteOpenOauthService;
    }




    @Override
    public void setByteOpenConfigStorage(ByteOpenConfigStorage byteOpenConfigStorage) {
        this.byteOpenConfigStorage = byteOpenConfigStorage;
        this.initHttp();
    }

    public abstract void initHttp();

    protected <T, E> T execute(RequestExecutor<T, E> executor, String uri, Map<String, String> headers, E data) throws ByteErrorException {

        try {
            T result = executor.execute(uri, headers, data, ByteType.Open);
            this.log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, data, result);
            return result;
        } catch (ByteErrorException e) {
            ByteError error = e.getError();
            if (error.getErrorCode() != 0) {
                this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, data, error);
                throw new ByteErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, data, e.getMessage());
            throw new ByteRuntimeException(e);
        }
    }
}
