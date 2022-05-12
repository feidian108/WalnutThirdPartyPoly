package com.walnut.cloud.open.bytedance.util.json;

import com.google.gson.*;
import com.walnut.cloud.open.bytedance.bean.result.user.ByteOpenFans;
import com.walnut.cloud.open.bytedance.bean.result.user.ByteOpenFansInfo;
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

        List<ByteOpenFansInfo> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            ByteOpenFansInfo fansInfo = new ByteOpenFansInfo();
            JsonObject fansJson = element.getAsJsonObject();
            fansInfo.setAvatar(GsonHelper.getString(fansJson, "avatar"));
            fansInfo.setOpenId(GsonHelper.getString(fansJson, "open_id"));
            fansInfo.setUnionId(GsonHelper.getString(fansJson, "union_id"));

            fansInfo.setCountry(GsonHelper.getString(fansJson, "country"));
            fansInfo.setProvince(GsonHelper.getString(fansJson, "province"));
            fansInfo.setCity(GsonHelper.getString(fansJson, "city"));
            fansInfo.setNickname(GsonHelper.getString(fansJson, "nickname"));
            fansInfo.setGender(GsonHelper.getInteger(fansJson, "gender"));
            list.add(fansInfo);
        }

        fans.setList(list);
        return fans;
    }
}
