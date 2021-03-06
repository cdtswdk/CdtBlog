package com.cdt.blog.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.cdt.blog.model.entity.ArticlePO;
import com.cdt.blog.model.enums.ArticleTypeEnum;
import com.cdt.blog.utils.DateTimeUtils;
import lombok.Data;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:17
 * @Description:
 */
@Data
public class ArticleVO {
    private String id;
    private String author;
    private String title;
    private String content;
    private String[] tags;
    private String type;
    private String category;
    private String gmtCreate;
    private String gmtUpdate;
    private String tabloid;
    private Integer views;

    /**
     * 根据 PO 创建 VO 对象
     *
     * @param articlePO PO对象
     * @return VO对象
     */
    public static ArticleVO fromArticlePO(ArticlePO articlePO) {
        return new Converter().convertToVO(articlePO);
    }

    private static class Converter implements IConverter<ArticlePO, ArticleVO> {
        @Override
        public ArticleVO convertToVO(ArticlePO article) {
            final ArticleVO vo = new ArticleVO();
            BeanUtil.copyProperties(article, vo, CopyOptions.create()
                    .ignoreNullValue().ignoreError());
            vo.setTags(article.getTags().split(","));
            for (ArticleTypeEnum item : ArticleTypeEnum.values()) {
                if (item.getFlag() == article.getType()) {
                    vo.setType(item.getNotes());
                }
            }
            vo.setGmtCreate(DateTimeUtils.dateToString(article.getGmtCreate()));
            vo.setGmtUpdate(DateTimeUtils.dateToString(article.getGmtUpdate()));
            return vo;
        }
    }
}
