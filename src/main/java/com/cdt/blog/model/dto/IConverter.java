package com.cdt.blog.model.dto;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 11:11
 * @Description:
 */
public interface IConverter<T, E> {
    /**
     * 将对应的 DTO 转换为 PO
     *
     * @param t 需要转换的 DTO 类
     * @return 转换结果
     */
    E convertToPO(T t);
}
