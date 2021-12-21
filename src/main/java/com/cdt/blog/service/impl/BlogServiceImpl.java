package com.cdt.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.dao.BlogTagsMapper;
import com.cdt.blog.dao.TagMapper;
import com.cdt.blog.dao.TypeMapper;
import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.dto.BlogDTO;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.BlogTags;
import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.model.entity.Type;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;
import com.cdt.blog.service.BlogService;
import com.cdt.blog.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chendongtao
 * @since 2021-12-16
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagsMapper blogTagsMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public BlogVO findById(Long id) {
        Blog blogPO = this.blogMapper.findById(id);
        if (Objects.isNull(blogPO)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        // 更新浏览量
        blogPO.setViews(blogPO.getViews() + 1);
        this.blogMapper.updateById(blogPO);

        return BlogVO.fromBlogPO(blogPO);
    }

    @Override
    public PageVO<BlogVO> getBlogs(Integer page, Integer limit) {
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        Page<Blog> res = this.blogMapper.findAll(new Page<>(page, limit), qw);
        List<BlogVO> blogVOS = res.getRecords().stream().
                map(BlogVO::fromBlogPO).collect(Collectors.toList());
        /*for (BlogVO blogVO : blogVOS) {
            // 分类
            Long typeId = blogVO.getTypeId();
            Type type = this.typeMapper.selectById(typeId);
            blogVO.setType(type);

            // 用户
            Long userId = blogVO.getUserId();
            User user = this.userMapper.selectById(userId);
            blogVO.setUser(user);

            // 标签
            Long blogPOId = blogVO.getId();
            QueryWrapper<BlogTags> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("blogs_id", blogPOId);
            List<BlogTags> blogTags = this.blogTagsMapper.selectList(queryWrapper);
            List<Tag> tags = new ArrayList<>();
            for (BlogTags blogTag : blogTags) {
                Tag tag = this.tagMapper.selectById(blogTag.getTagsId());
                if (!Objects.isNull(tag)) {
                    tags.add(tag);
                } else {
                    throw new BlogException(ErrorInfoEnum.INVALID_ID);
                }
            }
            blogVO.setTags(tags);
        }*/
        PageVO<BlogVO> pageVO = PageVO.<BlogVO>builder()
                .records(blogVOS.isEmpty() ? new ArrayList<>() : blogVOS)
                .current(res.getCurrent())
                .size(res.getSize())
                .total(res.getTotal()).build();
        return pageVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBlog(BlogDTO blogDTO) {
        blogDTO.setUpdateTime(DateTimeUtils.getNowDate());
        Blog blog = blogDTO.toBlogPO(false);
        this.blogMapper.insert(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlog(Long id) {
        int i = this.blogMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBlog(BlogDTO blogDTO, Long id) {
        Blog blog = this.blogMapper.findById(id);
        if (Objects.isNull(blog)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        Blog blogPO = blogDTO.toBlogPO(true);

        // 先删除blogTags中间表
        QueryWrapper<BlogTags> delWrapper = new QueryWrapper<>();
        delWrapper.eq("blogs_id", id);
        this.blogTagsMapper.delete(delWrapper);

        // 重新添加blogTags
        List<Tag> tags = blogPO.getTags();
        if (CollectionUtil.isNotEmpty(tags)) {
            for (Tag tag : tags) {
                // 已经存在该tag
                if (tag.getId() != null) {
                    BlogTags blogTags = new BlogTags();
                    blogTags.setBlogsId(id);
                    blogTags.setTagsId(tag.getId());
                    this.blogTagsMapper.insert(blogTags);
                    // 新加入的tag
                } else {
                    this.tagMapper.saveTag(tag);
                    BlogTags blogTags = new BlogTags();
                    blogTags.setBlogsId(id);
                    blogTags.setTagsId(tag.getId());
                    this.blogTagsMapper.insert(blogTags);
                }
            }
        }

        // type处理
        Type dtoType = blogDTO.getType();
        // 自定义分类
        if (dtoType.getId() == null) {
            this.typeMapper.saveType(dtoType);
            // 更新blog的type_id
            Long dtoTypeId = dtoType.getId();
            blogPO.setTypeId(dtoTypeId);
        } else {
            blogPO.setTypeId(dtoType.getId());
        }

        this.blogMapper.updateById(blogPO);
    }

    @Override
    public List<TimelineVO> timeline() {
        List<TimelineVO> res = new ArrayList<>();
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "create_time");
        List<Map<String, Object>> maps = this.blogMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id(String.valueOf(m.get("id")))
                .title(String.valueOf(m.get("title")))
                .createTime(DateTimeUtils.dateToString((Date) m.get("create_time")))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getCreateTime().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        return res;
    }

    @Override
    public List<TimelineVO> timelineNewest() {
        List<TimelineVO> res = new ArrayList<>();
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "create_time");
        List<Map<String, Object>> maps = this.blogMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id(String.valueOf(m.get("id")))
                .title(String.valueOf(m.get("title")))
                .createTime(DateTimeUtils.dateToString((Date) m.get("create_time")))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getCreateTime().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        return res;
    }
}
