package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.ent.ByteOpenRankVersion;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenRankVersionResult;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenRankVersionGsonAdapter implements JsonDeserializer<ByteOpenRankVersionResult> {
    
    @Override
    public ByteOpenRankVersionResult deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenRankVersionResult rankVersionResult = new ByteOpenRankVersionResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        
        rankVersionResult.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        rankVersionResult.setDescription(GsonHelper.getString(jsonObject, "description"));
        rankVersionResult.setHasMore(GsonHelper.getBoolean(jsonObject, "has_more"));
        rankVersionResult.setCursor(GsonHelper.getInteger(jsonObject, "cursor"));

        List<ByteOpenRankVersion> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenRankVersion rankVersion = new ByteOpenRankVersion();
            JsonObject json = element.getAsJsonObject();
            rankVersion.setStartTime(GsonHelper.getString(json, "start_time"));
            rankVersion.setEndTime(GsonHelper.getString(json, "end_time"));
            rankVersion.setActiveTime(GsonHelper.getString(json, "active_time"));
            rankVersion.setType(GsonHelper.getInteger(json, "type"));
            rankVersion.setVersion(GsonHelper.getInteger(json, "version"));
            list.add(rankVersion);
        }
        rankVersionResult.setList(list);
        return rankVersionResult;
    }
}
