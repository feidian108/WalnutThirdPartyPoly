package com.walnut.cloud.open.common.util.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walnut.cloud.open.common.error.bytedance.ByteError;

import java.util.Objects;


public class ByteGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    private static volatile Gson GSON_INSTANCE;

    static {
        INSTANCE.disableHtmlEscaping();
        //INSTANCE.registerTypeAdapter(OpenAccessToken.class, new OpenAccessTokenAdapter());
        INSTANCE.registerTypeAdapter(ByteError.class, new ByteErrorAdapter());
    }

    public static Gson create() {
        if (Objects.isNull(GSON_INSTANCE)) {
            synchronized (INSTANCE) {
                if (Objects.isNull(GSON_INSTANCE)) {
                    GSON_INSTANCE = INSTANCE.create();
                }
            }
        }
        return GSON_INSTANCE;
    }
}
