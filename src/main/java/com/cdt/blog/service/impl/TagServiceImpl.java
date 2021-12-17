package com.cdt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.dao.ArticleMapper;
import com.cdt.blog.model.entity.ArticlePO;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private ArticleMapper articleMapper;

    @Override
    public Set<String> getAllTags() {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("tags");
        List<ArticlePO> articlePOS = this.articleMapper.selectList(wrapper);
        Set<String> tags = articlePOS.stream().map(ArticlePO::getTags)
                .flatMap(s -> Arrays.stream(s.split(",")))
                .collect(Collectors.toSet());
        log.info("tags: {}", tags);
        return tags.isEmpty() ? new HashSet<>() : tags;
    }

    @Override
    public PageVO<ArticleVO> getArticleByTag(String tagName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn())).like("tags", tagName);
        Page<ArticlePO> articlePOPage = this.articleMapper.selectPage(new Page<>(page, limit), wrapper);
        List<ArticleVO> articleVOS = articlePOPage.getRecords().stream().
                map(ArticleVO::fromArticlePO).collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(articlePOPage.getTotal())
                .size(articlePOPage.getSize())
                .current(articlePOPage.getCurrent())
                .build();
        return pageVO;
    }
}
