package com.cdt.blog.service;

import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 17:30
 * @Description:
 */
public interface TagService {

    List<Tag> getAllTags();

    PageVO<BlogVO> getBlogByTag(String tagName, Integer page, Integer limit);

    List<Tag> getTagByTagName(String tagName);
}
