package com.cdt.blog.service;

import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.dto.UserDTO;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/8 22:08
 * @Description:
 */
public interface UserService {

    String checkUsernamePassword(UserDTO userDTO);

    Results logout();
}
