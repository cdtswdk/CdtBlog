package com.cdt.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdt.blog.model.entity.Type;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chendongtao
 * @since 2021-12-16
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

}
