package com.shenmou.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.entity.User;
import com.shenmou.springboot.exception.ServiceException;
import com.shenmou.springboot.service.IUserService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
        }
        String userName;
        try {
            userName = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User user = userService.getOne(queryWrapper);
        if(user == null){
            throw new ServiceException(Constants.CODE_401, "用户不存在请重新登录");
        }
        // 用户密码加签验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        return true;
    }
}
