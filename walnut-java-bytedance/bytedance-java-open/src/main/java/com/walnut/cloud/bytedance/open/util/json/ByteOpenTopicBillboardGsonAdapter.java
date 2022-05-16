package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenTopicBillboard;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenTopicBillboardList;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenTopicBillboardGsonAdapter implements JsonDeserializer<ByteOpenTopicBillboardList> {


    @Override
    public ByteOpenTopicBillboardList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        ByteOpenTopicBillboardList topicBillboardList = new ByteOpenTopicBillboardList();
        topicBillboardList.setErrorCode(GsonHelper.getInteger(jsonObject , "error_code"));
        topicBillboardList.setDescription(GsonHelper.getString(jsonObject, "description"));

        List<ByteOpenTopicBillboard> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenTopicBillboard topicBillboard = new ByteOpenTopicBillboard();
            JsonObject json = element.getAsJsonObject();
            topicBillboard.setRank(GsonHelper.getInteger(json, "rank"));
            topicBillboard.setTitle(GsonHelper.getString(json, "title"));
            topicBillboard.setRankChange(GsonHelper.getString(json, "rank_change"));
            topicBillboard.setEffectValue(GsonHelper.getDouble(json, "effect_value"));

            list.add(topicBillboard);
        }
        topicBillboardList.setList(list);
        return topicBillboardList;
    }
}
