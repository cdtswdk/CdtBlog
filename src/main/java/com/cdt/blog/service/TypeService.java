package com.cdt.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdt.blog.model.entity.Type;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;

/**
 * @author chendongtao
 * @since 2021-12-16
 */
public interface TypeService extends IService<Type> {

    Object getAllTypes();

    PageVO<BlogVO> getBlogByTypeName(String typeName, Integer page, Integer limit);
}
