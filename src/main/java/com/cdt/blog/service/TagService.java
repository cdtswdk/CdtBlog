package com.cdt.blog.service;

import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;

import java.util.Set;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 17:30
 * @Description:
 */
public interface TagService {

    Set<String> getAllTags();

    PageVO<ArticleVO> getArticleByTag(String tagName, Integer page, Integer limit);
}
