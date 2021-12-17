package com.cdt.blog.model.vo;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:03
 * @Description: 用于由 PO 转换成 VO
 */
public interface IConverter<T, E> {
    /**
     * VO 转换函数
     *
     * @param t 目标对象
     * @return 转换结果
     */
    E convertToVO(T t);
}
