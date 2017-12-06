package com.donggua.enums;

/**
 * 是否禁用状态
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-07-27 下午 04:40
 */
public enum ValidStatus implements EnumKeyValue{

    DISABLE(0, "禁用"), ENABLED(1, "启用");

    private Integer code;

    private String message;

    ValidStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ValidStatus getByCode(Integer code) {
        for (ValidStatus type : values()) {
            if (type.code().equals(code)) {
                return type;
            }
        }
        return null;
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
