package com.cdt.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.dao.TypeMapper;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Type;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TypeVO;
import com.cdt.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/17 11:03
 * @Description:
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<TypeVO> getAllTypes() {

        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.select("type_id", "count(1) as count").groupBy("type_id");
        List<Map<String, Object>> maps = this.blogMapper.selectMaps(blogQueryWrapper);
        for (Map<String, Object> map : maps) {
            Long type_id = (Long) map.get("type_id");
            Type type = this.typeMapper.selectById(type_id);
            map.put("name", type.getName());
        }
        List<TypeVO> typeVOS = maps.stream().map(TypeVO::fromMap).collect(Collectors.toList());
        return typeVOS.isEmpty() ? new ArrayList<>() : typeVOS;
    }

    @Override
    public PageVO<BlogVO> getBlogByTypeName(String typeName, Integer page, Integer limit) {
        QueryWrapper<Type> qw = new QueryWrapper<>();
        qw.eq("name", typeName);
        Type type = this.typeMapper.selectOne(qw);
        QueryWrapper<Blog> bqw = new QueryWrapper<>();
        bqw.eq("type_id", type.getId());
        Page<Blog> blogPage = this.blogMapper.findAll(new Page<>(page, limit), bqw);
        List<BlogVO> blogVOS = blogPage.getRecords().stream().map(BlogVO::fromBlogPO).collect(Collectors.toList());

        PageVO<BlogVO> pageVO = PageVO.<BlogVO>builder()
                .records(blogVOS)
                .size(blogPage.getSize())
                .current(blogPage.getCurrent())
                .total(blogPage.getTotal())
                .build();
        return pageVO;
    }
}
