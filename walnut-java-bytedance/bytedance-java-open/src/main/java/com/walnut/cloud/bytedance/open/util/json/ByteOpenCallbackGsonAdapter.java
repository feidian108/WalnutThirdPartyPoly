package com.walnut.cloud.bytedance.open.util.json;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.ByteOpenCallback;
import com.walnut.cloud.bytedance.open.bean.ByteOpenCommentContent;
import com.walnut.cloud.bytedance.open.bean.ByteOpenEventContent;
import com.walnut.cloud.open.common.util.json.GsonHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
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

        ByteOpenEventContent eventContent = new ByteOpenEventContent();

        switch (GsonHelper.getString(jsonObject, "event")) {
            case "verify_webhook":
                JsonObject json = jsonElement.getAsJsonObject().getAsJsonObject("content");
                eventContent.setChallenge(GsonHelper.getString(json, "challenge"));
                break;
            case "authorize":
                eventContent.setScopes(GsonHelper.getString(jsonObject.getAsJsonObject("content"), "scopes"));
                break;
            case "item_comment_reply":

                JSONObject commentJson = JSONObject.parseObject(GsonHelper.getString(jsonObject, "content"));


                ByteOpenCommentContent commentContent = new ByteOpenCommentContent();
                commentContent.setCommentId(commentJson.getString("comment_id"));
                commentContent.setCommentUserId(commentJson.getString( "comment_user_id"));
                commentContent.setContent(commentJson.getString("content"));
                commentContent.setCreateTime(commentJson.getLong( "create_time"));
                commentContent.setDiggCount(commentJson.getLong( "digg_count"));
                commentContent.setReplyCommentTotal(commentJson.getLong("reply_comment_total"));
                commentContent.setReplyToCommentId(commentJson.getString( "reply_to_comment_id"));
                commentContent.setReplyToItemId(commentJson.getString("reply_to_item_id"));
                commentContent.setAtUserId(commentJson.getString( "at_user_id"));
                eventContent.setCommentContent(commentContent);
            default:
                break;
        }

        callback.setContent(eventContent);
        return callback;
    }
}
