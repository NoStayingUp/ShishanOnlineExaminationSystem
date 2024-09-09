package com.feidian.controller.teacher;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.vo.CourseVO;
import com.feidian.pojo.vo.StuLoginVO;
import com.feidian.pojo.vo.TeacherInfoVO;
import com.feidian.pojo.vo.TeacherLoginVO;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.service.CourseService;
import com.feidian.service.TeacherService;
import com.feidian.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 教师登录
     * @param teacherLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<TeacherLoginVO> login(@RequestBody TeacherLoginDTO teacherLoginDTO){

        TeacherLoginVO teacherLoginVO = teacherService.teacherLogin(teacherLoginDTO);
        //登录成功后创建jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",teacherLoginVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        teacherLoginVO.setToken(token);

        return Result.success(teacherLoginVO);
    }

    /**
     * 获取教师信息
     * @param teacherId
     * @return
     */
    @GetMapping("/info/{teacherId}")
    public Result<TeacherInfoVO> getById(@PathVariable("teacherId") Integer teacherId){
        log.info("查询教师信息,id为：{}",teacherId);

        //得到VO对象
        TeacherInfoVO teacherInfoVO = teacherService.getInfoById(teacherId);

        return Result.success(teacherInfoVO);
    }



    @GetMapping("/course/{teacherId}")
    public Result<CourseVO> getCourseInfo(@PathVariable("teacherId") Integer teacherId){
        log.info("查询考试课程信息：{}",teacherId);

        List<Course> courses = courseService.getListByTchId(teacherId);

        //build构建对象（设计模式的一种），需要在实体类上加@Builder注解
        CourseVO courseVO = CourseVO.builder()
                .courses(courses)
                .build();

        return Result.success(courseVO);
    }
}
