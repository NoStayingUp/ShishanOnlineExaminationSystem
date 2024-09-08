package com.feidian.controller.student;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.vo.StuLoginVO;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.service.StuService;
import com.feidian.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exam/student")
@Slf4j
public class StudentController {

    @Autowired
    private StuService stuService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<StuLoginVO> login(@RequestBody StuLoginDTO stuLoginDTO){

        StuLoginVO stuLoginVO = stuService.stuLogin(stuLoginDTO);
        //登录成功后创建jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put("stuId",stuLoginVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        stuLoginVO.setToken(token);

        return Result.success(stuLoginVO);
    }
}
