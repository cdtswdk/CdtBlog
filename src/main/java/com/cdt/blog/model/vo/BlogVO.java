package com.cdt.blog.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.model.entity.Type;
import com.cdt.blog.model.entity.User;
import com.cdt.blog.utils.DateTimeUtils;
import lombok.Data;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/16 21:50
 * @Description:
 */
@Data
public class BlogVO {

    private Long id;
    private String content;
    private String description;
    private String title;
    private String createTime;
    private String updateTime;

    private Integer views;

    private List<Tag> tags;

    private Long typeId;

    private Long userId;

    private Type type;

    private User user;


    /**
     * 根据 PO 创建 VO 对象
     *
     * @param blog PO对象
     * @return VO对象
     */
    public static BlogVO fromBlogPO(Blog blog) {
        return new Converter().convertToVO(blog);
    }

    private static class Converter implements IConverter<Blog, BlogVO> {
        @Override
        public BlogVO convertToVO(Blog blog) {
            final BlogVO vo = new BlogVO();
            BeanUtil.copyProperties(blog, vo, CopyOptions.create()
                    .ignoreNullValue().ignoreError());
            vo.setCreateTime(DateTimeUtils.dateToString(blog.getCreateTime()));
            vo.setUpdateTime(DateTimeUtils.dateToString(blog.getUpdateTime()));
            return vo;
        }
    }
}
