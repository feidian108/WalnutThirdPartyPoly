package com.walnut.cloud.open.common.util.json;


import com.google.gson.*;
import com.walnut.cloud.open.common.error.bytedance.ByteError;

import java.lang.reflect.Type;


public class ByteErrorAdapter implements JsonDeserializer<ByteError> {

    @Override
    public ByteError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        ByteError.ByteErrorBuilder errorBuilder = ByteError.builder();
        JsonObject wxErrorJsonObject = json.getAsJsonObject();
        JsonObject data = wxErrorJsonObject.getAsJsonObject("data");
        JsonObject extra = wxErrorJsonObject.getAsJsonObject("extra");

        if (data.get("error_code") != null && !data.get("error_code").isJsonNull()) {
            errorBuilder.errorCode(GsonHelper.getAsPrimitiveInt(data.get("error_code")));
        }
        if (data.get("description") != null && !data.get("description").isJsonNull()) {
            errorBuilder.description(GsonHelper.getAsString(data.get("description")));
        }
        if( extra.get("sub_error_code") != null && !extra.get("sub_error_code").isJsonNull() ) {
            errorBuilder.subErrorCode(GsonHelper.getAsPrimitiveInt(extra.get("sub_error_code")));
        }
        if( extra.get("sub_description") != null && !extra.get("sub_description").isJsonNull() ) {
            errorBuilder.subDescription(GsonHelper.getAsString(extra.get("sub_description")));
        }

        if( extra.get("now") != null && !extra.get("now").isJsonNull() ) {
            errorBuilder.now(GsonHelper.getAsLong(extra.get("now")));
        }

        if( extra.get("log_id") != null && !extra.get("log_id").isJsonNull() ) {
            errorBuilder.logId(GsonHelper.getAsString(extra.get("log_id")));
        }
        errorBuilder.json(json.toString());

        return errorBuilder.build();
    }
}
