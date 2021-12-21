package com.cdt.blog.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 16:26
 * @Description:
 */
@Data
@Builder
public class TimelineVO {
    private String year;
    private List<Item> items;

    @Data
    @Builder
    public static class Item {
        private String id;
        private String createTime;
        private String title;
    }
}
