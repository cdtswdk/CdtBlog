package com.cdt.blog.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 10:02
 * @Description:
 */
@Data
@Builder
public class CategoryVO {
    private String name;
    private String count;

    public static CategoryVO fromMap(Map<String, Object> map) {
        return new Converter().convertToVO(map);
    }

    private static class Converter implements IConverter<Map<String, Object>, CategoryVO> {

        @Override
        public CategoryVO convertToVO(Map<String, Object> map) {
            CategoryVO vo = CategoryVO.builder()
                    .name(String.valueOf(map.get("name")))
                    .count(String.valueOf(map.get("count")))
                    .build();
            return vo;
        }
    }
}
