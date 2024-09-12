package com.feidian.interceptor;


import com.alibaba.druid.util.StringUtils;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
            Result.error("未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置401状态码
            response.setContentType("application/json; charset=UTF-8"); // 设置响应的内容类型和字符编码
            response.setCharacterEncoding("UTF-8"); // 显式设置字符编码（虽然content-type已经包含了）

            try (PrintWriter out = response.getWriter()) {
                // 写入JSON格式的错误信息
                // 注意："{未登录}" 不是一个有效的JSON对象，这里我改为一个有效的JSON对象
                out.print("{\"error\":\"未登录，请提供有效的认证信息\"}");
            }
            return false;
        }
    }
}
