package com.cdt.blog.controller;


import cn.hutool.core.map.MapUtil;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.dto.BlogDTO;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TimelineVO;
import com.cdt.blog.service.impl.BlogServiceImpl;
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
 * @author chendongtao
 * @since 2021-12-16
 */
@Api("与文章相关的api接口")
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/getBlog/{id}")
    @ApiOperation("根据id查询博客信息")
    @ApiImplicitParam(name = "id", value = "博客id", required = true, dataType = "String", paramType = "path")
    public Results<BlogVO> getBlog(@PathVariable Long id) {
        BlogVO blogVO = this.blogService.findById(id);
        return Results.ok(blogVO);
    }

    @ApiOperation("批量获取文章")
    @GetMapping("/getBlogs")
    public Results<PageVO> getBlogs(
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit) {
        return Results.ok(blogService.getBlogs(page, limit));
    }

    @PostMapping("/auth/insBlog")
    @ApiOperation("新增博客")
    public Results postBlogs(@ApiParam(name = "博客信息", value = "传入json格式", required = true)
                             @RequestBody @Valid BlogDTO blogDTO) {
        this.blogService.insertBlog(blogDTO);
        return Results.ok("增加成功", null);
    }

    @DeleteMapping("/auth/delBlog/{id}")
    @ApiOperation("根据id删除博客")
    @ApiImplicitParam(name = "id", value = "博客id", required = true, dataType = "String", paramType = "path")
    public Results deleteBlog(@PathVariable Long id) {
        this.blogService.deleteBlog(id);
        return Results.ok("删除成功", null);
    }

    @PutMapping("/auth/modifyBlog/{id}")
    @ApiOperation("修改博客")
    @ApiImplicitParam(name = "id", value = "博客id", required = true, dataType = "Long", paramType = "path")
    public Results<Map<String, Object>> putBlogs(
            @ApiParam(name = "要修改的博客信息", value = "传入json格式", required = true)
            @RequestBody BlogDTO BlogDTO,
            @PathVariable Long id) {
        this.blogService.updateBlog(BlogDTO, id);
        return Results.ok("更新成功", MapUtil.of("id", id));
    }

    @GetMapping("/timeline")
    @ApiOperation("获取文章时间线")
    public Results<List<TimelineVO>> getTimeline() {
        List<TimelineVO> timeline = this.blogService.timeline();
        return Results.ok(timeline);
    }

    @GetMapping("/timelineNewest")
    @ApiOperation("获取最新文章时间线")
    public Results<List<TimelineVO>> getTimelineNewest() {
        List<TimelineVO> timelineNewest = this.blogService.timelineNewest();
        return Results.ok(timelineNewest);
    }
}
