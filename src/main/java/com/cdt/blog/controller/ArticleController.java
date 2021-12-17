package com.cdt.blog.controller;

import cn.hutool.core.map.MapUtil;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.dto.ArticleDTO;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;
import com.cdt.blog.service.impl.ArticleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 23:36
 * @Description:
 */
@Api("与文章相关的api接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @GetMapping("/getArticle/{id}")
    @ApiOperation("根据id查询文章信息")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results<ArticleVO> getArticle(@PathVariable String id) {
        ArticleVO articleVO = this.articleServiceImpl.findById(id);
        return Results.ok(articleVO);
    }

    @ApiOperation("批量获取文章")
    @GetMapping("/getArticles")
    public Results<PageVO> getArticles(
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit) {
        return Results.ok(articleServiceImpl.getArticles(page, limit));
    }

    @PostMapping("/auth/insArticle")
    @ApiOperation("新增文章")
    public Results postArticles(@ApiParam(name = "文章信息", value = "传入json格式", required = true)
                                @RequestBody @Valid ArticleDTO articleDTO) {
        String id = this.articleServiceImpl.insertArticle(articleDTO);
        return Results.ok(MapUtil.of("id", id));
    }

    @DeleteMapping("/auth/delArticle/{id}")
    @ApiOperation("根据id删除文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results deleteArticle(@PathVariable String id) {
        this.articleServiceImpl.deleteArticle(id);
        return Results.ok("删除成功", null);
    }

    @PutMapping("/auth/modifyArticle/{id}")
    @ApiOperation("修改文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results<Map<String, Object>> putArticles(
            @ApiParam(name = "要修改的文章信息", value = "传入json格式", required = true)
            @RequestBody ArticleDTO articleDTO,
            @PathVariable String id) {
        this.articleServiceImpl.updateArticle(articleDTO, id);
        return Results.ok("更新成功", MapUtil.of("id", id));
    }

    @GetMapping("/timeline")
    @ApiOperation("获取文章时间线")
    public Results<List<TimelineVO>> getTimeline() {
        List<TimelineVO> timeline = this.articleServiceImpl.timeline();
        return Results.ok(timeline);
    }

    @GetMapping("/timelineNewest")
    @ApiOperation("获取最新文章时间线")
    public Results<TimelineVO> getTimelineNewest() {
        TimelineVO timelineNewest = this.articleServiceImpl.timelineNewest();
        return Results.ok(timelineNewest);
    }
}
