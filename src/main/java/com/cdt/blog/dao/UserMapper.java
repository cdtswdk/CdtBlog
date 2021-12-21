package com.cdt.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdt.blog.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chendongtao
 * @since 2021-12-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUserByBlogId(@Param("blogId") Long blogId);
}
