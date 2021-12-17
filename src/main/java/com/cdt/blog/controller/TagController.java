package com.cdt.blog.controller;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.impl.TagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 17:29
 * @Description:
 */
@Api("与文章标签相关的api接口")
@RestController
public class TagController {

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/tags")
    @ApiOperation("获取所有的标签")
    public Results<Set<String>> getAllTags() {
        return Results.ok(this.tagService.getAllTags());
    }

    @GetMapping("/tag/{name}")
    @ApiOperation("根据标签获取文章")
    @ApiImplicitParam(name = "name", value = "标签名称", dataType = "String", paramType = "path")
    public Results<PageVO<ArticleVO>> getArticle(
            @PathVariable("name") String tagName,
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit
    ) {
        PageVO<ArticleVO> articles = this.tagService.getArticleByTag(tagName, page, limit);
        return Results.ok(articles);
    }


}
