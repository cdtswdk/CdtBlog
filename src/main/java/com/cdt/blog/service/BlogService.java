package com.cdt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdt.blog.model.dto.BlogDTO;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;

import java.util.List;

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

    PageVO<BlogVO> getBlogs(Integer page, Integer limit);

    void insertBlog(BlogDTO blogDTO);

    void deleteBlog(Long id);

    void updateBlog(BlogDTO blogDTO, Long id);

    List<TimelineVO> timeline();

    List<TimelineVO> timelineNewest();
}
