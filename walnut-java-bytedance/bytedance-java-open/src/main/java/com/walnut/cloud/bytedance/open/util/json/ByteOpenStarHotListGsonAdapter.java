package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.star.ByteOpenStarHotList;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenStarHotListResult;
import com.walnut.cloud.open.common.util.json.GsonHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ByteOpenStarHotListGsonAdapter implements JsonDeserializer<ByteOpenStarHotListResult> {

    @Override
    public ByteOpenStarHotListResult deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenStarHotListResult starHotListResult = new ByteOpenStarHotListResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        starHotListResult.setHotListType(GsonHelper.getString( jsonObject, "hot_list_type"));
        starHotListResult.setHotListDescription(GsonHelper.getString(jsonObject, "hot_list_description"));
        starHotListResult.setHotListUpdateTimestamp(GsonHelper.getInteger(jsonObject, "hot_list_update_timestamp"));

        List<ByteOpenStarHotList> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenStarHotList starHotList = new ByteOpenStarHotList();
            JsonObject json = element.getAsJsonObject();
            starHotList.setUniqueId(GsonHelper.getString(json, "unique_id"));
            starHotList.setFollower(GsonHelper.getInteger(json, "follower"));
            starHotList.setNickName(GsonHelper.getString(json, "nick_name"));
            starHotList.setRank(GsonHelper.getInteger(json, "rank"));
            starHotList.setScore(GsonHelper.getFloat(json, "score"));
            starHotList.setTags(String.valueOf(json.get("tags")));
            list.add(starHotList);
        }
        starHotListResult.setList(list);
        return starHotListResult;
    }
}
