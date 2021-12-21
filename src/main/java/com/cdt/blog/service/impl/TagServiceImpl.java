package com.cdt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.dao.BlogTagsMapper;
import com.cdt.blog.dao.TagMapper;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 17:30
 * @Description:
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagsMapper blogTagsMapper;

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = this.tagMapper.selectList(null);
        log.info("tags: {}", tags);
        return tags.isEmpty() ? new ArrayList<>() : tags;
    }

    @Override
    public PageVO<BlogVO> getBlogByTag(String tagName, Integer page, Integer limit) {
        QueryWrapper<Tag> qw = new QueryWrapper<>();
        qw.like("name", tagName);
        Page<Blog> blogPage = this.blogMapper.findBlogByTag(new Page<>(page, limit), qw);
        List<BlogVO> blogVOS = blogPage.getRecords().stream().
                map(BlogVO::fromBlogPO).collect(Collectors.toList());
        PageVO<BlogVO> pageVO = PageVO.<BlogVO>builder()
                .records(blogVOS.isEmpty() ? new ArrayList<>() : blogVOS)
                .total(blogPage.getTotal())
                .size(blogPage.getSize())
                .current(blogPage.getCurrent())
                .build();
        return pageVO;
    }

    @Override
    public List<Tag> getTagByTagName(String tagName) {
        QueryWrapper<Tag> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(tagName)) {
            qw.like("name", tagName);
        }
        return this.tagMapper.selectList(qw);
    }
}
