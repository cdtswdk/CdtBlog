package com.cdt.blog.model.enums;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 22:30
 * @Description:
 */
public interface IErrorInfo {
    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMsg();

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();
}
