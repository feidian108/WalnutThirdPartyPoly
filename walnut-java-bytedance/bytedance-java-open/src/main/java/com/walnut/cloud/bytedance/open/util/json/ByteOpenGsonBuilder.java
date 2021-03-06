package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walnut.cloud.bytedance.open.bean.ByteOpenAccessToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenCallback;
import com.walnut.cloud.bytedance.open.bean.ByteOpenClientToken;
import com.walnut.cloud.bytedance.open.bean.ByteOpenRefreshToken;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizationInfo;
import com.walnut.cloud.bytedance.open.bean.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.bytedance.open.bean.data.billboard.*;
import com.walnut.cloud.bytedance.open.bean.data.star.ByteOpenStarAuthorScore;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserItemData;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoList;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoSource;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenHotVideoBillboardResult;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenRankVersionResult;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenStarHotListResult;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenUserItemResult;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFans;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFansCheck;
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
        INSTANCE.registerTypeAdapter(ByteOpenClientToken.class, new ByteOpenClientTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenAuthorizationInfo.class, new ByteOpenAuthorizationInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenAuthorizerInfo.class, new ByteOpenAuthorizerInfoGsonAdapter());

        INSTANCE.registerTypeAdapter(ByteOpenFans.class, new ByteOpenFansAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenFollow.class, new ByteOpenFollowGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenFansCheck.class, new ByteOpenFansCheckGsonAdapter());

        INSTANCE.registerTypeAdapter(ByteOpenUserVideoList.class, new ByteOpenUserVideoListGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenUserVideoSource.class, new ByteOpenUserVideoSourceGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenUserItemData.class, new ByteOpenUserVideoDataGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenUserItemResult.class, new ByteOpenUserItemResultGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenStarHotListResult.class, new ByteOpenStarHotListGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenStarAuthorScore.class, new ByteOpenStarAuthorScoreGsonAdapter());

        INSTANCE.registerTypeAdapter(ByteOpenRankVersionResult.class, new ByteOpenRankVersionGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenCallback.class, new ByteOpenCallbackGsonAdapter());

        INSTANCE.registerTypeAdapter(ByteOpenBillboardList.class, new ByteOpenBillboardGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenHotVideoBillboardResult.class, new ByteOpenHotVideoBillboardGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenMusicBillboardList.class, new ByteOpenMusicBillboardGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenTopicBillboardList.class, new ByteOpenTopicBillboardGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenPropBillboardList.class, new ByteOpenPropBillboardGsonAdapter());
        INSTANCE.registerTypeAdapter(ByteOpenLiveBillboardList.class, new ByteOpenLiveBillboardGsonAdapter());

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
