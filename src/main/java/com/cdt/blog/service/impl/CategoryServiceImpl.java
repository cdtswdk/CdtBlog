package com.cdt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.dao.ArticleMapper;
import com.cdt.blog.model.entity.ArticlePO;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.CategoryVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 9:59
 * @Description:
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public List<CategoryVO> getAllCategories() {

        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("category as 'name'", "count(1) as 'count'")
                .groupBy("category");

        List<CategoryVO> vos = this.articleMapper.selectMaps(wrapper).stream()
                .map(CategoryVO::fromMap).collect(Collectors.toList());
        return vos.isEmpty() ? new ArrayList<>() : vos;
    }

    @Override
    public PageVO<ArticleVO> getArticleByCategoryName(String categoryName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .eq("category", categoryName);
        Page<ArticlePO> pagePO = this.articleMapper.selectPage(new Page<>(page, limit), wrapper);
        List<ArticleVO> articleVOS = pagePO.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS)
                .size(pagePO.getSize())
                .current(pagePO.getCurrent())
                .total(pagePO.getTotal())
                .build();
        return pageVO;
    }
}
