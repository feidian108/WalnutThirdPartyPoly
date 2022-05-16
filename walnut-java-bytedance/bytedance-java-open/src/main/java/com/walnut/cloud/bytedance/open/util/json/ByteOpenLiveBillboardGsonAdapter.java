package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenLiveBillboard;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenLiveBillboardList;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenLiveBillboardGsonAdapter implements JsonDeserializer<ByteOpenLiveBillboardList> {


    @Override
    public ByteOpenLiveBillboardList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        ByteOpenLiveBillboardList liveBillboardList = new ByteOpenLiveBillboardList();

        liveBillboardList.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        liveBillboardList.setDescription(GsonHelper.getString(jsonObject, "description"));

        List<ByteOpenLiveBillboard> list = new ArrayList<>();

        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            JsonObject json = element.getAsJsonObject();
            ByteOpenLiveBillboard liveBillboard = new ByteOpenLiveBillboard();
            liveBillboard.setCover(GsonHelper.getString(json, "cover"));
            liveBillboard.setNickname(GsonHelper.getString(json, "nickname"));
            liveBillboard.setRank(GsonHelper.getInteger(json, "rank"));
            liveBillboard.setTitle(GsonHelper.getString(json, "title"));
            liveBillboard.setHotValue(GsonHelper.getDouble(json, "hot_value"));
            list.add(liveBillboard);
        }
        liveBillboardList.setList(list);
        return liveBillboardList;
    }
}
