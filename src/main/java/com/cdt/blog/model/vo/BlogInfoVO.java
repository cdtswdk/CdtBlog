package com.cdt.blog.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cdt.blog.model.comm.BlogSetting;
import lombok.Data;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 11:12
 * @Description:
 */
@Data
public class BlogInfoVO {

    private String title;
    private String desc;
    private List<String> covers;
    private String avatar;
    private String nickname;
    private int blogCount;
    private int totalViews;

    public static BlogInfoVO fromBlogSetting(BlogSetting blogSetting) {
        return new Converter().convertToVO(blogSetting);
    }

    private static class Converter implements IConverter<BlogSetting, BlogInfoVO> {

        @Override
        public BlogInfoVO convertToVO(BlogSetting blogSetting) {
            BlogInfoVO vo = new BlogInfoVO();
            BeanUtil.copyProperties(blogSetting, vo);
            return vo;
        }
    }
}
