package com.cdt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.vo.BlogVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chendongtao
 * @since 2021-12-16
 */
public interface BlogService extends IService<Blog> {

    BlogVO findById(Long id);

    Object getArticles(Integer page, Integer limit);
}
