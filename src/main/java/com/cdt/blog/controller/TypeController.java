package com.cdt.blog.controller;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.vo.BlogVO;
import com.cdt.blog.model.vo.PageVO;
import com.cdt.blog.model.vo.TypeVO;
import com.cdt.blog.service.impl.TypeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/17 11:04
 * @Description:
 */
@RestController
@Api("与文章分类相关的api接口")
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeServiceImpl typeService;

    @GetMapping("/getTypes")
    @ApiOperation("获取所有的文章分类以及对应分类文章数")
    public Results<List<TypeVO>> getAllTypes() {
        return Results.ok(this.typeService.getAllTypes());
    }

    @GetMapping("/getType/{name}")
    @ApiOperation("根据分类名称获取文章集合")
    @ApiImplicitParam(name = "name", value = "分类名称", dataType = "String", paramType = "path")
    public Results<PageVO<BlogVO>> getBlog(
            @PathVariable("name") String typeName,
            @ApiParam("页码")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer limit
    ) {
        PageVO<BlogVO> articles = this.typeService.getBlogByTypeName(typeName, page, limit);
        return Results.ok(articles);
    }

}
