package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenHotVideoBillboard;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenHotVideoBillboardResult;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenHotVideoBillboardGsonAdapter implements JsonDeserializer<ByteOpenHotVideoBillboardResult> {

    @Override
    public ByteOpenHotVideoBillboardResult deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ByteOpenHotVideoBillboardResult hotVideoBillboardResult = new ByteOpenHotVideoBillboardResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        List<ByteOpenHotVideoBillboard> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenHotVideoBillboard hotVideoBillboard = new ByteOpenHotVideoBillboard();
            JsonObject json = element.getAsJsonObject();

            hotVideoBillboard.setAuthor(GsonHelper.getString(json, "author"));
            hotVideoBillboard.setTitle(GsonHelper.getString(json, "title"));
            hotVideoBillboard.setHotWords(GsonHelper.getString(json, "hot_words"));
            hotVideoBillboard.setHotValue(GsonHelper.getDouble(json, "hot_value"));
            hotVideoBillboard.setCommentCount(GsonHelper.getInteger(json, "comment_count"));
            hotVideoBillboard.setDiggCount(GsonHelper.getInteger(json, "digg_count"));
            hotVideoBillboard.setItemCover(GsonHelper.getString(json, "item_cover"));
            hotVideoBillboard.setPlayCount(GsonHelper.getInteger(json, "play_count"));
            hotVideoBillboard.setRank(GsonHelper.getInteger(json, "rank"));
            hotVideoBillboard.setShareUrl(GsonHelper.getString(json, "share_url"));

            list.add(hotVideoBillboard);
        }
        hotVideoBillboardResult.setList(list);
        return hotVideoBillboardResult;
    }
}
