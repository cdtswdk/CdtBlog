package com.cdt.blog.controller;


import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.impl.BlogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return Results.ok(blogService.getArticles(page, limit));
    }
}
