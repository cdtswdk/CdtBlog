package com.cdt.blog.model.comm;

import cn.hutool.setting.Setting;
import lombok.Data;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 10:58
 * @Description: 配置文件对应的类
 */
@Data
public class BlogSetting {
    private String title;
    private String desc;
    private List<String> covers; // 封面
    private String avatar;
    private String nickname;

    public static BlogSetting fromSetting(Setting setting) {
        return (BlogSetting) setting.toBean(new BlogSetting());
    }
}
