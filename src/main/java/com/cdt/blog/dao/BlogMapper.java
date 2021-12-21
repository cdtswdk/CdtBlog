package com.cdt.blog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Tag;
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
public interface BlogMapper extends BaseMapper<Blog> {


    Blog findById(Long id);

    Page<Blog> findAll(Page<Blog> page, @Param(Constants.WRAPPER) QueryWrapper<Blog> qw);

    Page<Blog> findBlogByTag(Page<Blog> page, @Param(Constants.WRAPPER) QueryWrapper<Tag> qw);
}
