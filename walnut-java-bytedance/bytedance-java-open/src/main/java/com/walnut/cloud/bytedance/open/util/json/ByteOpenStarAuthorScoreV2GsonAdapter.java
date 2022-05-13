package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.star.ByteOpenStarAuthorScoreV2;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;

public class ByteOpenStarAuthorScoreV2GsonAdapter implements JsonDeserializer<ByteOpenStarAuthorScoreV2> {

    @Override
    public ByteOpenStarAuthorScoreV2 deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenStarAuthorScoreV2 starAuthorScoreV2 = new ByteOpenStarAuthorScoreV2();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        starAuthorScoreV2.setUniqueId(GsonHelper.getString(jsonObject, "unique_id"));
        starAuthorScoreV2.setNickName(GsonHelper.getString(jsonObject, "nick_name"));
        starAuthorScoreV2.setCooperationScore(GsonHelper.getFloat(jsonObject, "cooperation_score"));
        starAuthorScoreV2.setCpScore(GsonHelper.getFloat(jsonObject, "cp_score"));
        starAuthorScoreV2.setGrowthScore(GsonHelper.getFloat(jsonObject, "growth_score"));
        starAuthorScoreV2.setSpreadScore(GsonHelper.getFloat(jsonObject, "spread_score"));
        starAuthorScoreV2.setShopScore(GsonHelper.getFloat(jsonObject, "shop_score"));
        starAuthorScoreV2.setStarScore(GsonHelper.getFloat(jsonObject, "star_score"));
        starAuthorScoreV2.setUpdateTimestamp(GsonHelper.getInteger(jsonObject, "update_timestamp"));
        return starAuthorScoreV2;
    }
}
