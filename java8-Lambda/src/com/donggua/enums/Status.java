package com.donggua.enums;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-11-28 上午 10:54
 */
public enum Status implements EnumKeyValue{

    // ========== 基本操作提示 ============
    SUCCESS(200, "操作成功"),
    ERROR(9999, "操作失败"),

    // ========== 通用的错误以9开头 ==========
    // 400 - Bad Reques
    BAD_REQUEST(9000, "提交参数不匹配"),

    // 400 - bad request parameter
    BAD_REQUEST_PARAMETER(9000, "参数验证失败");

    private Integer code;

    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
