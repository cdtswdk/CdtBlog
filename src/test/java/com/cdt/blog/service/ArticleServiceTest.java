package com.cdt.blog.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.cdt.blog.CdtBlogApplication;
import com.cdt.blog.dao.ArticleMapper;
import com.cdt.blog.model.entity.ArticlePO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 16:15
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CdtBlogApplication.class})
public class ArticleServiceTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void solveTime() {
        List<ArticlePO> articles = articleMapper.selectList(null);
        for (int i = 0; i < articles.size(); i++) {
            ArticlePO po = articles.get(i);
            LocalDateTime dateTime = LocalDateTime.now().plusYears(RandomUtil.randomInt(-5, 5));
            long epochMilli = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            Date date = Date.from(dateTime.toInstant(ZoneOffset.UTC));
            po.setGmtCreate(date);
            po.setGmtUpdate(date);
            articleMapper.updateById(po);
        }
    }

    @Test
    public void insArticle() {
        ArticlePO article = articleMapper.selectById("61a9cf761dce36848be7f927");
        for (int i = 0; i < 20; i++) {
            article.setId(IdUtil.objectId());
            article.setTitle("测试数据" + i);
            articleMapper.insert(article);
        }
    }
}
