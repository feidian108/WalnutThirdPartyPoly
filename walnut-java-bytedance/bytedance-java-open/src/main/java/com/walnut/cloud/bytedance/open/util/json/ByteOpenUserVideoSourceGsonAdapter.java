package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoSource;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenUserVideoSourceGsonAdapter implements JsonDeserializer<ByteOpenUserVideoSource> {


    @Override
    public ByteOpenUserVideoSource deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("source");

        ByteOpenUserVideoSource videoSource = new ByteOpenUserVideoSource();

        List<String> sources = new ArrayList<>();
        JsonArray jsonArray = GsonHelper.getAsJsonArray(jsonObject.get("source"));
        if (jsonArray != null && !jsonArray.isJsonNull()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.get(i).getAsJsonObject();
                if (jsonObject == null || jsonObject.isJsonNull()) {
                    continue;
                }
                String id = GsonHelper.getString(jsonObject, "id");
                if (id == null) {
                    continue;
                }
                sources.add(id);
            }
        }
        videoSource.setSource(sources);
        return videoSource;
    }
}
