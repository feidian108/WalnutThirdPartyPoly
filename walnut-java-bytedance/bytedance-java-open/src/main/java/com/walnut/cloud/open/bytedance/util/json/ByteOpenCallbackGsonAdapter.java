package com.walnut.cloud.open.bytedance.util.json;


import com.google.gson.*;
import com.walnut.cloud.open.bytedance.bean.ByteOpenCallback;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;


public class ByteOpenCallbackGsonAdapter implements JsonDeserializer<ByteOpenCallback> {
    @Override
    public ByteOpenCallback deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ByteOpenCallback callback = new ByteOpenCallback();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        callback.setLogId(GsonHelper.getString(jsonObject, "log_id"));
        callback.setClientKey(GsonHelper.getString(jsonObject, "client_key"));
        callback.setEventId(GsonHelper.getString(jsonObject, "event_id"));
        callback.setEvent(GsonHelper.getString(jsonObject, "event"));
        callback.setFromUserId(GsonHelper.getString(jsonObject, "from_user_id"));


        switch (GsonHelper.getString(jsonObject, "event")) {
            case "verify_webhook":
                callback.setContent(jsonObject.getAsJsonObject("content").toString());
                break;
            case "authorize":
                JsonArray jsonArray = GsonHelper.getAsJsonArray(jsonObject.getAsJsonObject("content").get("scopes"));
                callback.setContent(jsonArray.toString());
                break;
            case "item_comment_reply":
                callback.setContent(GsonHelper.getString(jsonObject, "content"));
            default:
                break;
        }

        return callback;
    }
}
