package com.cdt.blog.service;

import com.cdt.blog.model.dto.ArticleDTO;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 10:10
 * @Description:
 */
public interface ArticleService {
    PageVO<ArticleVO> getArticles(int page, int limit);

    String insertArticle(ArticleDTO articleDTO);

    ArticleVO findById(String id);

    void deleteArticle(String id);

    void updateArticle(ArticleDTO articleDTO, String id);

    List<TimelineVO> timeline();

    TimelineVO timelineNewest();
}
