package com.walnut.cloud.open.bytedance.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walnut.cloud.open.bytedance.bean.ByteOpenAccessToken;
import com.walnut.cloud.open.bytedance.bean.ByteOpenCallback;
import com.walnut.cloud.open.bytedance.bean.ByteOpenClientToken;
import com.walnut.cloud.open.bytedance.bean.ByteOpenRefreshToken;
import com.walnut.cloud.open.bytedance.bean.result.ByteOpenAuthorizationInfo;
import com.walnut.cloud.open.bytedance.bean.result.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.open.bytedance.bean.result.user.ByteOpenFans;
import com.walnut.cloud.open.bytedance.bean.result.user.ByteOpenFollow;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


@Slf4j
public class ByteOpenGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();
    private static volatile Gson GSON_INSTANCE;

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(ByteOpenAccessToken.class, new ByteOpenAccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenRefreshToken.class, new ByteOpenRefreshTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenAuthorizationInfo.class, new ByteOpenAuthorizationInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenAuthorizerInfo.class, new ByteOpenAuthorizerInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenFans.class, new ByteOpenFansAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenFollow.class, new ByteOpenFollowGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenClientToken.class, new ByteOpenClientTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenCallback.class, new ByteOpenCallbackGsonAdapter());

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
