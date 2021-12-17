package com.cdt.blog.controller;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.vo.ArticleVO;
import com.cdt.blog.model.vo.CategoryVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/4 9:58
 * @Description:
 */
@RestController
@Api("与文章分类相关的api接口")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/categories")
    @ApiOperation("获取所有的文章分类以及对应分类文章数")
    public Results<List<CategoryVO>> getAllCategories() {
        return Results.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/category/{name}")
    @ApiOperation("根据分类名称获取文章集合")
    @ApiImplicitParam(name = "name", value = "标签名称", dataType = "String", paramType = "path")
    public Results<PageVO<ArticleVO>> getArticle(
            @PathVariable("name") String categoryName,
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit
    ) {
        PageVO<ArticleVO> articles = this.categoryService.getArticleByCategoryName(categoryName, page, limit);
        return Results.ok(articles);
    }
}
