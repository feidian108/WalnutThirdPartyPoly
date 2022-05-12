package com.walnut.cloud.bytedance.open.util.json;


import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.result.auth.ByteOpenAuthorizerInfo;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;


public class ByteOpenAuthorizerInfoGsonAdapter implements JsonDeserializer<ByteOpenAuthorizerInfo> {

    @Override
    public ByteOpenAuthorizerInfo deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ByteOpenAuthorizerInfo authorizerInfo = new ByteOpenAuthorizerInfo();
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        authorizerInfo.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        authorizerInfo.setDescription(GsonHelper.getString(jsonObject, "description"));
        authorizerInfo.setClientKey(GsonHelper.getString(jsonObject, "client_key"));
        authorizerInfo.setUnionId(GsonHelper.getString(jsonObject, "union_id"));
        authorizerInfo.setOpenId(GsonHelper.getString(jsonObject, "open_id"));
        authorizerInfo.setNickname(GsonHelper.getString(jsonObject, "nickname"));
        authorizerInfo.setGender(GsonHelper.getInteger(jsonObject, "gender"));
        authorizerInfo.setAvatar(GsonHelper.getString(jsonObject, "avatar"));
        authorizerInfo.setAvatarLarger(GsonHelper.getString(jsonObject, "avatar_larger"));
        authorizerInfo.setCaptcha(GsonHelper.getString(jsonObject, "captcha"));
        authorizerInfo.setCountry(GsonHelper.getString(jsonObject, "country"));
        authorizerInfo.setProvince(GsonHelper.getString(jsonObject, "province"));
        authorizerInfo.setCity(GsonHelper.getString(jsonObject, "city"));
        authorizerInfo.setDescUrl(GsonHelper.getString(jsonObject, "desc_url"));
        authorizerInfo.setDistrict(GsonHelper.getString(jsonObject, "district"));
        authorizerInfo.setEAccountRole(GsonHelper.getString(jsonObject, "e_account_role"));
        authorizerInfo.setLogId(GsonHelper.getString(jsonObject, "log_id"));
        return authorizerInfo;
    }
}
