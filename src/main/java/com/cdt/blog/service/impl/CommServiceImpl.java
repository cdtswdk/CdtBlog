package com.cdt.blog.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdt.blog.dao.BlogMapper;
import com.cdt.blog.model.comm.BlogSetting;
import com.cdt.blog.model.entity.Blog;
import com.cdt.blog.model.vo.BlogInfoVO;
import com.cdt.blog.service.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 11:15
 * @Description:
 */
@Service
public class CommServiceImpl implements CommService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private Setting setting;

    @Override
    public BlogInfoVO getBlogInfo() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        BlogSetting blogSetting = BlogSetting.fromSetting(setting);
        BlogInfoVO blogInfoVO = BlogInfoVO.fromBlogSetting(blogSetting);
        wrapper.select("sum(views) as total_views");
        List<Map<String, Object>> maps = this.blogMapper.selectMaps(wrapper);
        int totalViews = 0;
        if (!maps.isEmpty()) {
            totalViews = Convert.toInt(maps.get(0).get("total_views"), 0);
        }
        Integer count = this.blogMapper.selectCount(null);
        blogInfoVO.setBlogCount(count);
        blogInfoVO.setTotalViews(totalViews);
        return blogInfoVO;
    }
}
