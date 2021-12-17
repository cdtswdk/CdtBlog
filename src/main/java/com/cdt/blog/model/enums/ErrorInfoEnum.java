package com.cdt.blog.model.enums;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 22:40
 * @Description:
 */
public enum ErrorInfoEnum implements IErrorInfo {

    SUCCESS(0, "操作成功"),
    TOKEN_INVALID(4000, "无效的token"),
    TOKEN_EXPIRED(4001, "您的token已过期"),
    NOT_LOGIN(4002, "请先登录再进行访问"),
    RESOURCES_NOT_FOUND(4003, "找不到相应资源"),
    MISSING_PARAMETERS(4004, "参数缺失"),
    NO_AUTHORITY(4005, "对不起，您没有权限访问该接口"),
    INVALID_ID(4008, "你的id不合法"),
    USERNAME_PASSWORD_ERROR(4009, "用户名或密码错误"),
    UNKNOWN_ERROR(5000, "出现未知错误");

    private int code;
    private String msg;

    ErrorInfoEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
