package com.cdt.blog.exception;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.enums.IErrorInfo;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 22:47
 * @Description: 自定义异常类
 */
public class BlogException extends RuntimeException {

    private final IErrorInfo errorInfo;

    public BlogException(IErrorInfo iErrorInfo) {
        this.errorInfo = iErrorInfo;
    }

    /**
     * 将异常转换为 ResultVO 对象返回给前端
     *
     * @return 封装了异常信息的 ResultVO 对象
     */
    public Results toResultVO() {
        return Results.fromErrorInfo(errorInfo);
    }
}
