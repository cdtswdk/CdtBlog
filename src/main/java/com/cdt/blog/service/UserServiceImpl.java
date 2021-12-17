package com.cdt.blog.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.setting.Setting;
import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.dto.UserDTO;
import com.cdt.blog.model.enums.UserRoleEnum;
import com.cdt.blog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.cdt.blog.model.enums.ErrorInfoEnum.USERNAME_PASSWORD_ERROR;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/8 22:09
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Setting setting;

    /**
     * 校验用户名和密码
     *
     * @param userDTO 用户对象
     * @return 校验成功就返回token
     */
    @Override
    public String checkUsernamePassword(UserDTO userDTO) {
        String username = setting.getStr("username");
        String password = setting.getStr("password");
        if (Objects.equals(username, userDTO.getUsername()) &&
                Objects.equals(password, MD5.create().digestHex(userDTO.getPassword()))) {
            return JwtUtils.createToken(username, CollUtil.newArrayList(UserRoleEnum.ADMIN.getValue()));
        } else {
            throw new BlogException(USERNAME_PASSWORD_ERROR);
        }
    }

    @Override
    public Results logout() {
        return Results.ok("登出成功", null);
    }
}
