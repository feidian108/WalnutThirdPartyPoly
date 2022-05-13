package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserItemData;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenUserItemResult;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenUserItemResultGsonAdapter implements JsonDeserializer<ByteOpenUserItemResult> {


    @Override
    public ByteOpenUserItemResult deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        ByteOpenUserItemResult userItemResult = new ByteOpenUserItemResult();
        List<ByteOpenUserItemData> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("result_list")) {
            ByteOpenUserItemData itemData = new ByteOpenUserItemData();
            JsonObject json = element.getAsJsonObject();
            itemData.setDate(GsonHelper.getString(json, "date"));
            itemData.setNewIssue(GsonHelper.getInteger(json, "new_issue"));
            itemData.setNewPlay(GsonHelper.getInteger(json, "new_play"));
            itemData.setTotalIssue(GsonHelper.getInteger(json, "total_issue"));
            list.add(itemData);
        }
        userItemResult.setList(list);
        return userItemResult;
    }
}
