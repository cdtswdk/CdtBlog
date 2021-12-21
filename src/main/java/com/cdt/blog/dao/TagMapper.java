package com.cdt.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdt.blog.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chendongtao
 * @since 2021-12-16
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagByBlogId(@Param("blogId") Long blogId);

    void saveTag(Tag tag);
}
