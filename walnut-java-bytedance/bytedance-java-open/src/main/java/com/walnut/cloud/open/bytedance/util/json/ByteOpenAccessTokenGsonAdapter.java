package com.walnut.cloud.open.bytedance.util.json;


import com.google.gson.*;
import com.walnut.cloud.open.bytedance.bean.ByteOpenAccessToken;
import com.walnut.cloud.open.common.util.json.GsonHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;


@Slf4j
public class ByteOpenAccessTokenGsonAdapter implements JsonDeserializer<ByteOpenAccessToken> {

    @Override
    public ByteOpenAccessToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ByteOpenAccessToken authorizerAccessToken = new ByteOpenAccessToken();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");
        authorizerAccessToken.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        authorizerAccessToken.setAccessToken(GsonHelper.getString(jsonObject, "access_token"));
        authorizerAccessToken.setRefreshToken(GsonHelper.getString(jsonObject, "refresh_token"));
        authorizerAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
        authorizerAccessToken.setOpenId(GsonHelper.getString(jsonObject, "open_id"));
        authorizerAccessToken.setCaptcha(GsonHelper.getString(jsonObject, "captcha"));
        authorizerAccessToken.setDescription(GsonHelper.getString(jsonObject, "description"));
        authorizerAccessToken.setDescUrl(GsonHelper.getString(jsonObject, "desc_url"));
        authorizerAccessToken.setRefreshExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "refresh_expires_in"));
        authorizerAccessToken.setScope(GsonHelper.getString(jsonObject, "scope"));
        authorizerAccessToken.setLogId(GsonHelper.getString(jsonObject, "log_id"));
        return authorizerAccessToken;
    }
}
