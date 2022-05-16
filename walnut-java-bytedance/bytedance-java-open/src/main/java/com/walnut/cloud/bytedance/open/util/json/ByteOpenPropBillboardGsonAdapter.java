package com.walnut.cloud.bytedance.open.util.json;

import com.google.gson.*;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenPropBillboard;
import com.walnut.cloud.bytedance.open.bean.data.billboard.ByteOpenPropBillboardList;
import com.walnut.cloud.open.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ByteOpenPropBillboardGsonAdapter implements JsonDeserializer<ByteOpenPropBillboardList> {


    @Override
    public ByteOpenPropBillboardList deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("data");

        ByteOpenPropBillboardList propBillboardList = new ByteOpenPropBillboardList();
        propBillboardList.setErrorCode(GsonHelper.getInteger(jsonObject, "error_code"));
        propBillboardList.setDescription(GsonHelper.getString(jsonObject, "description"));

        List<ByteOpenPropBillboard> list = new ArrayList<>();
        for (JsonElement element : jsonObject.getAsJsonArray("list")) {
            JsonObject json = element.getAsJsonObject();
            ByteOpenPropBillboard propBillboard = new ByteOpenPropBillboard();
            propBillboard.setRankChange(GsonHelper.getString(json, "rank_change"));
            propBillboard.setName(GsonHelper.getString(json, "name"));
            propBillboard.setDailyIssuePercent(GsonHelper.getString(json, "daily_issue_percent"));
            propBillboard.setRank(GsonHelper.getInteger(json, "rank"));
            propBillboard.setShootCnt(GsonHelper.getDouble(json, "shoot_cnt"));
            propBillboard.setDailyIssueCnt(GsonHelper.getDouble(json, "daily_issue_cnt"));
            propBillboard.setDailyCollectionCnt(GsonHelper.getDouble(json, "daily_collection_cnt"));
            propBillboard.setDailyPlayCnt(GsonHelper.getDouble(json, "daily_play_cnt"));
            propBillboard.setEffectValue(GsonHelper.getDouble(json, "effect_value"));
            propBillboard.setShowCnt(GsonHelper.getDouble(json, "show_cnt"));
            list.add(propBillboard);
        }

        propBillboardList.setList(list);
        return propBillboardList;
    }
}
