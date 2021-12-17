package com.cdt.blog.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_blog_tags")
public class BlogTags implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogsId;

    private Long tagsId;


}
