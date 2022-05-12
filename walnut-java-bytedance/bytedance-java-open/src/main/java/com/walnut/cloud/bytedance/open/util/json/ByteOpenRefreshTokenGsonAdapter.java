package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.ByteOpenRefreshToken;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;


public class ByteOpenRefreshTokenGsonAdapter implements JsonDeserializer<ByteOpenRefreshToken> {

    @Override
    public ByteOpenRefreshToken deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenRefreshToken refreshToken = new ByteOpenRefreshToken();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        refreshToken.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        refreshToken.setRefreshToken(GsonHelper.getString(jsonObject, "refresh_token"));
        refreshToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
        refreshToken.setCaptcha(GsonHelper.getString(jsonObject, "captcha"));
        refreshToken.setDescription(GsonHelper.getString(jsonObject, "description"));
        refreshToken.setDescUrl(GsonHelper.getString(jsonObject, "desc_url"));
        refreshToken.setLogId(GsonHelper.getString(jsonObject, "log_id"));
        return refreshToken;
    }
}
