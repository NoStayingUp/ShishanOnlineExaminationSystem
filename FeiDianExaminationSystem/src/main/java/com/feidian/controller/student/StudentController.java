package com.feidian.controller.student;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.StuUpdateDTO;
import com.feidian.pojo.vo.StuInfoVO;
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

    /**
     * 学生登录
     * @param stuLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<StuLoginVO> login(@RequestBody StuLoginDTO stuLoginDTO){

        StuLoginVO stuLoginVO = stuService.stuLogin(stuLoginDTO);
        //登录成功后创建jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",stuLoginVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        stuLoginVO.setToken(token);

        return Result.success(stuLoginVO);
    }

    /**
     * 根据学生id获取学生信息
     * @param stuId
     * @return
     */
    @GetMapping("/info/{stuId}")
    public Result<StuInfoVO> getInfo(@PathVariable("stuId") Integer stuId){
        log.info("获取学生信息");

        StuInfoVO stuInfoVO = stuService.getInfoById(stuId);

        return Result.success(stuInfoVO);
    }


    /**
     * 修改学生手机号
     * @param stuId
     * @param stuUpdateDTO
     * @return
     */
    @PutMapping("/info/update/{stuId}")
    public Result update(@PathVariable Integer stuId, @RequestBody StuUpdateDTO stuUpdateDTO){

        log.info("开始修改学生绑定手机号");

        String phone = stuUpdateDTO.getPhone();
        stuService.updatePhoneById(stuId,phone);

        return Result.success();
    }
}
