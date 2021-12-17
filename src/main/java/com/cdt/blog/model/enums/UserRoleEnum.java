package com.cdt.blog.model.enums;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/8 22:13
 * @Description:
 */
public enum UserRoleEnum {
    ADMIN("ADMIN");

    private final String value;

    UserRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
