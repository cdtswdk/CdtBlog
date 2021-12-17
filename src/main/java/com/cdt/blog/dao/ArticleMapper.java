package com.cdt.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdt.blog.model.entity.ArticlePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:01
 * @Description:
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {
}
