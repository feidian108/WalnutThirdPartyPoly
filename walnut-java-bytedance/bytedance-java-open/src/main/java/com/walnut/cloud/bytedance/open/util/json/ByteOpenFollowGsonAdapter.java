package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.result.user.ByteOpenFollow;
import com.walnut.cloud.bytedance.open.bean.result.user.ByteOpenUserInfo;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenFollowGsonAdapter implements JsonDeserializer<ByteOpenFollow> {


    @Override
    public ByteOpenFollow deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenFollow follow = new ByteOpenFollow();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        follow.setCursor(GsonHelper.getInteger(jsonObject, "cursor"));
        follow.setHasMore(GsonHelper.getBoolean(jsonObject, "has_more"));

        List<ByteOpenUserInfo> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenUserInfo userInfo = new ByteOpenUserInfo();
            JsonObject fansJson = element.getAsJsonObject();
            userInfo.setAvatar(GsonHelper.getString(fansJson, "avatar"));
            userInfo.setOpenId(GsonHelper.getString(fansJson, "open_id"));
            userInfo.setUnionId(GsonHelper.getString(fansJson, "union_id"));

            userInfo.setCountry(GsonHelper.getString(fansJson, "country"));
            userInfo.setProvince(GsonHelper.getString(fansJson, "province"));
            userInfo.setCity(GsonHelper.getString(fansJson, "city"));
            userInfo.setNickname(GsonHelper.getString(fansJson, "nickname"));
            userInfo.setGender(GsonHelper.getInteger(fansJson, "gender"));
            list.add(userInfo);
        }

        follow.setList(list);
        return follow;
    }
}
