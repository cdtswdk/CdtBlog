package com.cdt.blog.controller;

import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import com.cdt.blog.model.vo.BlogInfoVO;
import com.cdt.blog.service.impl.CommServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 22:31
 * @Description:
 */
@Api("通用接口")
@RestController
public class CommController {

    @Autowired
    private CommServiceImpl commService;

    @ApiOperation("检查服务端是否正常")
    @GetMapping("/ping")
    public Results ping() {
        return Results.ok("欢迎访问CdtBlog API", null);
    }

    @ApiOperation("异常测试")
    @GetMapping("/exception")
    public Results exceptionTest() {
        throw new BlogException(ErrorInfoEnum.MISSING_PARAMETERS);
    }

    @ApiOperation("获取博客信息")
    @GetMapping("/blogInfo")
    public Results<BlogInfoVO> getBlogInfo() {
        BlogInfoVO blogInfo = this.commService.getBlogInfo();
        return Results.ok(blogInfo);
    }
}
