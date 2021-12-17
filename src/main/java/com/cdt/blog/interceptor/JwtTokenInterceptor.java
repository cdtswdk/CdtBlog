package com.cdt.blog.interceptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.cdt.blog.constants.JwtConstants;
import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import com.cdt.blog.model.enums.UserRoleEnum;
import com.cdt.blog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/8 22:25
 * @Description:
 */
@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // token 的前缀是否正确。
        String token = request.getHeader(JwtConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new BlogException(ErrorInfoEnum.NOT_LOGIN);
        }
        // token 是否过期
        token = token.replace(JwtConstants.TOKEN_PREFIX, StrUtil.EMPTY);
        if (JwtUtils.isTokenExpired(token)) {
            throw new BlogException(ErrorInfoEnum.TOKEN_EXPIRED);
        }
        // token 中的角色是否包含 ADMIN
        Claims tokenBody = JwtUtils.getTokenBody(token);
        String[] roles = Optional.ofNullable(tokenBody.get(JwtConstants.ROLE_CLAIMS))
                .map(r -> r.toString().split(","))
                .orElse(new String[0]);
        if (!ArrayUtil.contains(roles, UserRoleEnum.ADMIN.getValue())) {
            throw new BlogException(ErrorInfoEnum.NO_AUTHORITY);
        }
        return true;
    }
}
