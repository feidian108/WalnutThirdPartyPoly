package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.ByteOpenClientToken;
import com.walnut.cloud.open.common.util.json.GsonHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;


@Slf4j
public class ByteOpenClientTokenGsonAdapter implements JsonDeserializer<ByteOpenClientToken> {

    @Override
    public ByteOpenClientToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ByteOpenClientToken clientToken = new ByteOpenClientToken();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        clientToken.setAccessToken(GsonHelper.getString(jsonObject, "access_token"));
        clientToken.setExpiresIn(GsonHelper.getInteger(jsonObject, "error_code"));
        clientToken.setDescription(GsonHelper.getString(jsonObject, "description"));
        clientToken.setExpiresIn(GsonHelper.getInteger(jsonObject, "expires_in"));
        return clientToken;
    }
}
