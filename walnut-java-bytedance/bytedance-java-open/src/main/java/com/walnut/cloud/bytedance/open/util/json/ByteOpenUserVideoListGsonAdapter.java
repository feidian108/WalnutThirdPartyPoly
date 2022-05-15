package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideo;
import com.walnut.cloud.bytedance.open.bean.item.ByteOpenUserVideoList;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenUserVideoListGsonAdapter implements JsonDeserializer<ByteOpenUserVideoList> {

    @Override
    public ByteOpenUserVideoList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        ByteOpenUserVideoList videoList = new ByteOpenUserVideoList();
        videoList.setCursor(GsonHelper.getLong(jsonObject, "cursor"));
        videoList.setHasMore(GsonHelper.getBoolean(jsonObject, "has_more"));

        List<ByteOpenUserVideo> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            JsonObject json = element.getAsJsonObject();
            ByteOpenUserVideo userVideo = new ByteOpenUserVideo();
            userVideo.setItemId(GsonHelper.getString(json, "item_id"));
            userVideo.setTitle(GsonHelper.getString(json, "title"));
            userVideo.setCover(GsonHelper.getString(json, "cover"));
            userVideo.setVideoStatus(GsonHelper.getInteger(json, "video_status"));
            userVideo.setCreateTime(GsonHelper.getLong(json, "create_time"));
            userVideo.setIsReviewed(GsonHelper.getBoolean(json, "is_reviewed"));
            userVideo.setIsTop(GsonHelper.getBoolean(json, "is_top"));
            userVideo.setMediaType(GsonHelper.getInteger(json, "media_type"));
            userVideo.setShareUrl(GsonHelper.getString(json, "share_url"));

            list.add(userVideo);
        }
        videoList.setList(list);
        return videoList;
    }
}
