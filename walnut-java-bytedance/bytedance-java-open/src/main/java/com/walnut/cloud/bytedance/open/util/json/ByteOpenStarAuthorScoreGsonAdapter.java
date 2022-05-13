package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.star.ByteOpenStarAuthorScore;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;

public class ByteOpenStarAuthorScoreGsonAdapter implements JsonDeserializer<ByteOpenStarAuthorScore> {

    @Override
    public ByteOpenStarAuthorScore deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenStarAuthorScore starAuthorScore = new ByteOpenStarAuthorScore();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        starAuthorScore.setUniqueId(GsonHelper.getString(jsonObject, "unique_id"));
        starAuthorScore.setNickName(GsonHelper.getString(jsonObject, "nick_name"));
        starAuthorScore.setFollower(GsonHelper.getInteger(jsonObject, "follower"));
        starAuthorScore.setCooperationScore(GsonHelper.getFloat(jsonObject, "cooperation_score"));
        starAuthorScore.setCpScore(GsonHelper.getFloat(jsonObject, "cp_score"));
        starAuthorScore.setGrowthScore(GsonHelper.getFloat(jsonObject, "growth_score"));
        starAuthorScore.setSpreadScore(GsonHelper.getFloat(jsonObject, "spread_score"));
        starAuthorScore.setShopScore(GsonHelper.getFloat(jsonObject, "shop_score"));
        starAuthorScore.setStarScore(GsonHelper.getFloat(jsonObject, "star_score"));
        starAuthorScore.setUpdateTimestamp(GsonHelper.getInteger(jsonObject, "update_timestamp"));
        return starAuthorScore;
    }
}
