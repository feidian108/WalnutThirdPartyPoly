package com.walnut.cloud.open.common.error.bytedance;


import com.walnut.cloud.open.common.enums.bytedance.ByteMaErrorMsgEnum;
import com.walnut.cloud.open.common.enums.bytedance.ByteOpenErrorMsgEnum;
import com.walnut.cloud.open.common.enums.bytedance.ByteType;
import com.walnut.cloud.open.common.util.json.ByteGsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ByteError implements Serializable {

    private static final long serialVersionUID = 7869786563361406291L;

    private int errorCode;

    private String description;

    private int subErrorCode;

    private String subDescription;

    private Long now;

    private String json;

    private String logId;

    public ByteError(int errorCode, String description, int subErrorCode, String subDescription, Long now, String logId) {
        this.errorCode = errorCode;
        this.description = description;
        this.subErrorCode = subErrorCode;
        this.subDescription = subDescription;
        this.now = now;
        this.logId = logId;
    }

    public static ByteError fromJson(String json) {
        return fromJson(json, null);
    }

    public static ByteError fromJson(String json, ByteType type) {
        final ByteError byteError = ByteGsonBuilder.create().fromJson(json, ByteError.class);
        if (byteError.getErrorCode() == 0 || type == null) {
            return byteError;
        }

        switch (type) {
            case Open: {
                final String msg = ByteOpenErrorMsgEnum.findMsgByCode(byteError.getErrorCode());
                if (msg != null) {
                    byteError.setDescription(msg);
                }
                break;
            }
            case MiniApp: {
                final String msg = ByteMaErrorMsgEnum.findMsgByCode(byteError.getErrorCode());
                if (msg != null) {
                    byteError.setDescription(msg);
                }
                break;
            }
            default:
                return byteError;
        }

        return byteError;
    }

    @Override
    public String toString() {
        if (this.json == null) {
            return "错误代码：" + this.errorCode + ", 错误信息：" + this.description;
        }

        return "错误代码：" + this.errorCode + ", 错误信息：" + this.description + "，字节跳动原始报文：" + this.json;
    }
}
