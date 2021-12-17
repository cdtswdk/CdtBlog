package com.cdt.blog.service;

import com.cdt.blog.CdtBlogApplication;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.model.entity.Blog;
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

    @Test
    public void insBlog() {
        Blog blog = this.blogMapper.selectById(1L);
        for (int i = 0; i < 10; i++) {
            blog.setTitle("测试标题数据" + i);
            blog.setContent("测试内容数据" + i);
            this.blogMapper.insert(blog);
        }
    }
}
