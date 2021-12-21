package com.cdt.blog.model.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdt.blog.model.entity.*;
import com.cdt.blog.utils.DateTimeUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author chendongtao
 * @since 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "博客类", description = "前端传入的博客信息")
public class BlogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotEmpty(message = "赞赏开启")
    @ApiModelProperty(notes = "appreciation", example = "true")
    private Boolean appreciation;

    @NotEmpty(message = "评论开启")
    @ApiModelProperty(notes = "commentabled", example = "true")
    private Boolean commentabled;

    @NotEmpty(message = "文章内容不能为空")
    @ApiModelProperty(notes = "文章内容", example = "与子同行")
    private String content;

    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    @NotEmpty(message = "发布")
    @ApiModelProperty(notes = "published", example = "true")
    private Boolean published;

    @NotEmpty(message = "推荐不能为空")
    @ApiModelProperty(notes = "recommend", example = "true")
    private Boolean recommend;

    @NotEmpty(message = "版权开启")
    @ApiModelProperty(notes = "shareStatement", example = "true")
    private Boolean shareStatement;

    @NotEmpty(message = "文章标题不能为空")
    @ApiModelProperty(notes = "文章标题", example = "快速入门SpringBoot")
    private String title;

    private Date updateTime;

    private Integer views;

    private String typeName;

    private Type type;

    private User user;

    //    private List<Tag> tags = new ArrayList<>();
    private List<Tag> tags;

    private List<Comment> comments = new ArrayList<>();

    private List<Tag> newTags;

    public Blog toBlogPO(boolean isUpdate) {
        Blog blog = new BlogDTO.Converter().convertToPO(this);
        blog.setViews(isUpdate ? null : 0);
        blog.setCreateTime(isUpdate ? null : blog.getUpdateTime());
        return blog;
    }

    private static class Converter implements IConverter<BlogDTO, Blog> {

        @Override
        public Blog convertToPO(BlogDTO blogDTO) {
            Blog blog = new Blog();
            BeanUtil.copyProperties(blogDTO, blog);
            blog.setUpdateTime(DateTimeUtils.getNowDate());
            return blog;
        }
    }
}
