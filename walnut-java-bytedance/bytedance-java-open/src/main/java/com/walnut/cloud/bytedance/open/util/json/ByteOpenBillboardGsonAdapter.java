package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenBillboard;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenBillboardList;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenVideo;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenBillboardGsonAdapter implements JsonDeserializer<ByteOpenBillboardList> {

    @Override
    public ByteOpenBillboardList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        ByteOpenBillboardList billboardList = new ByteOpenBillboardList();

        billboardList.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        billboardList.setDescription(GsonHelper.getString(jsonObject, "description"));

        List<ByteOpenBillboard> list = new ArrayList<>();

        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            JsonObject jsonArray = element.getAsJsonObject();
            ByteOpenBillboard billboard = new ByteOpenBillboard();
            billboard.setNickname(GsonHelper.getString(jsonArray, "nickname"));
            billboard.setAvatar(GsonHelper.getString(jsonArray, "avatar"));
            billboard.setFollowerCount(GsonHelper.getLong(jsonArray, "follower_count"));
            billboard.setOnbillbaordTimes(GsonHelper.getInteger(jsonArray, "onbillbaord_times"));
            billboard.setEffectValue(GsonHelper.getDouble(jsonArray, "effect_value"));
            billboard.setRank(GsonHelper.getInteger(jsonArray, "rank"));
            billboard.setRankChange(GsonHelper.getString(jsonArray, "rank_change"));

            List<ByteOpenVideo> videoList = new ArrayList<>();
            for (JsonElement element1 : jsonArray.getAsJsonArray("video_list")) {
                JsonObject json = element1.getAsJsonObject();
                ByteOpenVideo video = new ByteOpenVideo();
                video.setTitle(GsonHelper.getString(json, "title"));
                video.setItemCover(GsonHelper.getString(json, "item_cover"));
                video.setShareUrl(GsonHelper.getString(json, "share_url"));
                videoList.add(video);
            }
            billboard.setVideoList(videoList);
            list.add(billboard);
        }
        billboardList.setList(list);
        return billboardList;
    }
}
