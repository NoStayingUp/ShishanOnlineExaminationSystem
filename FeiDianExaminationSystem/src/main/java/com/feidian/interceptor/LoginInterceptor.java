package com.feidian.interceptor;


import com.alibaba.druid.util.StringUtils;
import com.feidian.properties.JwtProperties;
import com.feidian.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求头中的token
        String token = request.getHeader(jwtProperties.getTokenName());
        log.info("jwt校验:{}", token);
        //2、校验令牌
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long id = Long.valueOf(claims.get("id").toString());
            //log.info("当前用户id：", id);
            //通过，放行
            return true;
        } catch (Exception ex) {
            //不通过，响应401状态码,表示为登录
            response.setStatus(401);
            return false;
        }
    }
}
