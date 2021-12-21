package com.cdt.blog.controller;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.entity.Tag;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.impl.TagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/3 17:29
 * @Description:
 */
@Api("与文章标签相关的api接口")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/getTags")
    @ApiOperation("获取所有的标签")
    public Results<List<Tag>> getAllTags() {
        return Results.ok(this.tagService.getAllTags());
    }

    @GetMapping("/getTag/{name}")
    @ApiOperation("根据标签获取文章")
    @ApiImplicitParam(name = "name", value = "标签名称", dataType = "String", paramType = "path")
    public Results<PageVO<BlogVO>> getArticle(
            @PathVariable("name") String tagName,
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit
    ) {
        PageVO<BlogVO> blog = this.tagService.getBlogByTag(tagName, page, limit);
        return Results.ok(blog);
    }

    @GetMapping("/getTagsByName")
    @ApiOperation("根据标签名模糊搜索标签")
    public Results<List<Tag>> getTagsByName(
            @ApiParam("标签名")
            @RequestParam(value = "tagName", required = false) String tagName) {
        List<Tag> tags = this.tagService.getTagByTagName(tagName);
        return Results.ok(tags);
    }
}
