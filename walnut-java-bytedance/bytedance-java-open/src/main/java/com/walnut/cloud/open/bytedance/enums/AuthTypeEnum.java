package com.walnut.cloud.open.bytedance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum AuthTypeEnum {

    DOUYIN("douyin"),

    SNSSDK("snssdk"),

    XIGUA("xigua");

    private final String code;
}
