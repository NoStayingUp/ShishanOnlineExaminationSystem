package com.feidian.controller.teacher;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.vo.StuLoginVO;
import com.feidian.pojo.vo.TeacherLoginVO;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.service.TeacherService;
import com.feidian.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exam/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<TeacherLoginVO> login(@RequestBody TeacherLoginDTO teacherLoginDTO){

        TeacherLoginVO teacherLoginVO = teacherService.teacherLogin(teacherLoginDTO);
        //登录成功后创建jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put("teacherId",teacherLoginVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        teacherLoginVO.setToken(token);

        return Result.success(teacherLoginVO);
    }
}
