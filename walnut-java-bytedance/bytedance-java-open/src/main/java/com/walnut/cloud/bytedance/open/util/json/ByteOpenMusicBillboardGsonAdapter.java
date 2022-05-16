package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenMusicBillboard;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenMusicBillboardList;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenMusicBillboardGsonAdapter implements JsonDeserializer<ByteOpenMusicBillboardList> {

    @Override
    public ByteOpenMusicBillboardList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        ByteOpenMusicBillboardList musicBillboardList = new ByteOpenMusicBillboardList();

        musicBillboardList.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        musicBillboardList.setDescription(GsonHelper.getString(jsonObject, "description"));

        List<ByteOpenMusicBillboard> list = new ArrayList<>();

        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            JsonObject json = element.getAsJsonObject();
            ByteOpenMusicBillboard musicBillboard = new ByteOpenMusicBillboard();
            musicBillboard.setCover(GsonHelper.getString(json, "cover"));
            musicBillboard.setAuthor(GsonHelper.getString(json, "author"));
            musicBillboard.setRank(GsonHelper.getInteger(json, "rank"));
            musicBillboard.setDuration(GsonHelper.getInteger(json, "duration"));
            musicBillboard.setTitle(GsonHelper.getString(json, "title"));
            musicBillboard.setShareUrl(GsonHelper.getString(json, "share_url"));
            musicBillboard.setUseCount(GsonHelper.getLong(json, "user_count"));
            list.add(musicBillboard);
        }
        musicBillboardList.setList(list);
        return musicBillboardList;
    }
}
