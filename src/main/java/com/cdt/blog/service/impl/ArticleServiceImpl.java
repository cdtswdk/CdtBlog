package com.cdt.blog.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdt.blog.dao.ArticleMapper;
import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.dto.ArticleDTO;
import com.cdt.blog.model.entity.ArticlePO;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;
import com.cdt.blog.service.ArticleService;
import com.cdt.blog.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:25
 * @Description:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageVO<ArticleVO> getArticles(int page, int limit) {
        QueryWrapper<ArticlePO> qw = new QueryWrapper<>();
        qw.select(ArticlePO.class, i -> !"content".equals(i.getColumn()));
        Page<ArticlePO> res = this.articleMapper.selectPage(new Page<>(page, limit), qw);
        List<ArticleVO> articleVOS = res.getRecords().stream().
                map(ArticleVO::fromArticlePO).collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .current(res.getCurrent())
                .size(res.getSize())
                .total(res.getTotal()).build();
        return pageVO;
    }

    @Override
    public String insertArticle(ArticleDTO articleDTO) {
        ArticlePO articlePO = articleDTO.toArticlePO(false);
        String id = IdUtil.objectId();
        articlePO.setId(id);
        this.articleMapper.insert(articlePO);
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ArticleVO findById(String id) {
        ArticlePO articlePO = this.articleMapper.selectById(id);
        if (Objects.isNull(articlePO)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        // 更新浏览量
        articlePO.setViews(articlePO.getViews() + 1);
        this.articleMapper.updateById(articlePO);
        return ArticleVO.fromArticlePO(articlePO);
    }

    @Override
    public void deleteArticle(String id) {
        int i = articleMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
    }

    @Override
    public void updateArticle(ArticleDTO articleDTO, String id) {
        ArticlePO po = this.articleMapper.selectById(id);
        if (Objects.isNull(po)) {
            throw new BlogException(ErrorInfoEnum.INVALID_ID);
        }
        ArticlePO articlePO = articleDTO.toArticlePO(true);
        articlePO.setId(id);
        this.articleMapper.updateById(articlePO);
    }

    @Override
    public List<TimelineVO> timeline() {
        List<TimelineVO> res = new ArrayList<>();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "gmt_create");
        List<Map<String, Object>> maps = this.articleMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id(String.valueOf(m.get("id")))
                .title(String.valueOf(m.get("title")))
                .gmtCreate(DateTimeUtils.dateToString((Date) m.get("gmt_create")))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getGmtCreate().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        return res;
    }

    @Override
    public TimelineVO timelineNewest() {
        List<TimelineVO> res = new ArrayList<>();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "gmt_create");
        wrapper.orderBy(true, false, "gmt_create");
        List<Map<String, Object>> maps = this.articleMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id(String.valueOf(m.get("id")))
                .title(String.valueOf(m.get("title")))
                .gmtCreate(DateTimeUtils.dateToString((Date) m.get("gmt_create")))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getGmtCreate().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        List<TimelineVO> result = res.stream()
                .sorted(Comparator.comparing(TimelineVO::getYear).reversed())
                .collect(Collectors.toList());
        if (!result.isEmpty()) {
            TimelineVO timelineVO = result.get(0);
            List<TimelineVO.Item> items = timelineVO.getItems();
            if (items.size() > 5) {
                timelineVO.setItems(items.subList(0, 4));
            }
            return timelineVO;
        }
        return null;
    }
}
