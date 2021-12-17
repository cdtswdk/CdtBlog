package com.cdt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.dao.TypeMapper;
import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Type;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author chendongtao
 * @since 2021-12-16
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public BlogVO findById(Long id) {
        Blog blogPO = this.blogMapper.selectById(id);
        if (Objects.isNull(blogPO)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        // 更新浏览量
        blogPO.setViews(blogPO.getViews() + 1);
        this.blogMapper.updateById(blogPO);

        BlogVO blogVO = BlogVO.fromBlogPO(blogPO);
        Long typeId = blogVO.getTypeId();
        Type type = this.typeMapper.selectById(typeId);
        blogVO.setType(type);

        return blogVO;
    }

    @Override
    public PageVO<BlogVO> getArticles(Integer page, Integer limit) {
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        qw.select(Blog.class, i -> !"content".equals(i.getColumn()));
        Page<Blog> res = this.blogMapper.selectPage(new Page<>(page, limit), qw);
        List<BlogVO> blogVOS = res.getRecords().stream().
                map(BlogVO::fromBlogPO).collect(Collectors.toList());
        for (BlogVO blogVO : blogVOS) {
            Long typeId = blogVO.getTypeId();
            Type type = this.typeMapper.selectById(typeId);
            blogVO.setType(type);
        }
        PageVO<BlogVO> pageVO = PageVO.<BlogVO>builder()
                .records(blogVOS.isEmpty() ? new ArrayList<>() : blogVOS)
                .current(res.getCurrent())
                .size(res.getSize())
                .total(res.getTotal()).build();
        return pageVO;
    }
}
