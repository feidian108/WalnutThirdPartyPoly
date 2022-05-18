package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.user.ByteOpenFansCheck;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;

public class ByteOpenFansCheckGsonAdapter implements JsonDeserializer<ByteOpenFansCheck> {


    @Override
    public ByteOpenFansCheck deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ByteOpenFansCheck fansCheck = new ByteOpenFansCheck();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        fansCheck.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        fansCheck.setDescription(GsonHelper.getString(jsonObject, "description"));
        fansCheck.setFollower(GsonHelper.getBoolean(jsonObject, "follower"));
        fansCheck.setFollowTime(GsonHelper.getLong(jsonObject, "follow_time"));

        return fansCheck;
    }
}
