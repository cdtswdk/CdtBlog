package com.cdt.blog.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:24
 * @Description:
 */
@Data
@Builder
public class PageVO<T> {
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;
}
