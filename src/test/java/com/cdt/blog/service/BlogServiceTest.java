package com.cdt.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.CdtBlogApplication;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.service.impl.TagServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/16 22:47
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CdtBlogApplication.class})
public class BlogServiceTest {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagServiceImpl tagService;

    @Test
    public void insBlog() {
        Blog blog = this.blogMapper.selectById(1L);
        this.blogMapper.insert(blog);
    }

    @Test
    public void selBlog() {
        Blog blog = this.blogMapper.findById(1L);
        System.out.println(blog);
    }
    @Test
    public void selBlogAll() {
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        IPage<Blog> blogIPage = this.blogMapper.findAll(page, qw);
        System.out.println(blogIPage);
    }

    @Test
    public void selBlogByTag() {
        Page<Blog> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.like("name", "java");
        IPage<Blog> blogIPage = this.blogMapper.findBlogByTag(page,wrapper);
        System.out.println(blogIPage);
    }
}
