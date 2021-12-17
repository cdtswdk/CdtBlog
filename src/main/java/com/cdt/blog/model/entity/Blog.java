package com.cdt.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
@TableName("t_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 内容
    private String content;

    // 标题
    private String title;

    // 首图
    private String firstPicture;

    // 标记
    private String flag;

    // 浏览次数
    private Integer views;

    // 赞赏开启
    private Boolean appreciation;

    // 描述
    private String description;

    // 评论开启
    private Boolean commentabled;

    // 发布
    private Boolean published;

    // 推荐开启
    private Boolean recommend;

    // 版权开启
    private Boolean shareStatement;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 分类
    private Long typeId;

    // 用户
    private Long userId;

    /*// 标签
    @Transient
    private List<Tag> tags = new ArrayList<>();

    // 评论
    @Transient
    private List<Comment> comments = new ArrayList<>();*/
}
