package com.cdt.blog.controller;

import cn.hutool.core.map.MapUtil;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.dto.UserDTO;
import com.cdt.blog.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/8 22:15
 * @Description:
 */
@Api("与用户相关的api接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Results<Map<String, Object>> login(@ApiParam(name = "用户登录信息", value = "传入json格式", required = true)
                                              @RequestBody @Valid UserDTO userDTO) {
        String token = this.userService.checkUsernamePassword(userDTO);
        return Results.ok("登录成功", MapUtil.of("token", token));
    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Results logout() {
        return this.userService.logout();
    }
}
