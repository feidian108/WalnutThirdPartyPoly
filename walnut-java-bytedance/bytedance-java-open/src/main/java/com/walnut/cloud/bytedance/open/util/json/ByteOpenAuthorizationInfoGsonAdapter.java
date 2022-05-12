package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.result.ByteOpenAuthorizationInfo;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;


public class ByteOpenAuthorizationInfoGsonAdapter implements JsonDeserializer<ByteOpenAuthorizationInfo> {

    @Override
    public ByteOpenAuthorizationInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ByteOpenAuthorizationInfo authorizationInfo = new ByteOpenAuthorizationInfo();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        authorizationInfo.setLogId(GsonHelper.getString(jsonObject, "log_id"));
        authorizationInfo.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        authorizationInfo.setAccessToken(GsonHelper.getString(jsonObject, "access_token"));
        authorizationInfo.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
        authorizationInfo.setRefreshToken(GsonHelper.getString(jsonObject, "refresh_token"));
        authorizationInfo.setRefreshExpiresIn(GsonHelper.getInteger(jsonObject, "refresh_expires_in"));
        authorizationInfo.setOpenId(GsonHelper.getString(jsonObject, "open_id"));
        authorizationInfo.setCaptcha(GsonHelper.getString(jsonObject, "captcha"));
        authorizationInfo.setScope(GsonHelper.getString(jsonObject, "scope"));
        authorizationInfo.setDescUrl(GsonHelper.getString(jsonObject, "desc_url"));
        authorizationInfo.setDescription(GsonHelper.getString(jsonObject, "description"));

        return authorizationInfo;
    }
}
