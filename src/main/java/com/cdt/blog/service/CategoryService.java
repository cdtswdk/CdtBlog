package com.cdt.blog.service;

import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.CategoryVO;
import com.cdt.blog.model.vo.PageVO;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 9:59
 * @Description:
 */
public interface CategoryService {
    List<CategoryVO> getAllCategories();

    PageVO<ArticleVO> getArticleByCategoryName(String categoryName, Integer page, Integer limit);
}
