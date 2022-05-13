package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFans;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenUserInfo;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenFansAdapter implements JsonDeserializer<ByteOpenFans> {

    @Override
    public ByteOpenFans deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenFans fans = new ByteOpenFans();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        fans.setCursor(GsonHelper.getInteger(jsonObject, "cursor"));
        fans.setHasMore(GsonHelper.getBoolean(jsonObject, "has_more"));
        fans.setTotal(GsonHelper.getInteger(jsonObject, "total"));

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

        fans.setList(list);
        return fans;
    }
}
