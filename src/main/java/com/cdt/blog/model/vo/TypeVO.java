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
public class TypeVO {
    private Long id;
    private String name;
    private String count;

    public static TypeVO fromMap(Map<String, Object> map) {
        return new Converter().convertToVO(map);
    }

    private static class Converter implements IConverter<Map<String, Object>, TypeVO> {

        @Override
        public TypeVO convertToVO(Map<String, Object> map) {
            TypeVO vo = TypeVO.builder()
                    .id((Long) map.get("type_id"))
                    .name(String.valueOf(map.get("name")))
                    .count(String.valueOf(map.get("count")))
                    .build();
            return vo;
        }
    }
}
