package com.walnut.cloud.open.common.enums.bytedance;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;


@Getter
public enum ByteMaErrorMsgEnum {

    CODE_40001(40001, "参数错误"),
    ;

    private final int code;
    private final String msg;

    ByteMaErrorMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static final Map<Integer, String> valueMap = Maps.newHashMap();

    static {
        for (ByteMaErrorMsgEnum value : ByteMaErrorMsgEnum.values()) {
            valueMap.put(value.code, value.msg);
        }
    }

    public static String findMsgByCode(int code) {
        return valueMap.getOrDefault(code, null);
    }
}
