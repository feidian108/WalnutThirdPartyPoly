package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walnut.cloud.bytedance.open.bean.ByteOpenAccessToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenCallback;
import com.walnut.cloud.bytedance.open.bean.ByteOpenClientToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenRefreshToken;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizationInfo;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenStarHotListResult;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenUserItemResult;
import com.walnut.cloud.bytedance.open.bean.star.ByteOpenStarAuthorScoreV2;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFans;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFollow;
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
        INSTANCE.registerTypeAdapter(ByteOpenUserItemResult.class, new ByteOpenUserItemResultGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenStarHotListResult.class, new ByteOpenStarHotListGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenStarAuthorScoreV2.class, new ByteOpenStarAuthorScoreV2GsonAdapter());
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
