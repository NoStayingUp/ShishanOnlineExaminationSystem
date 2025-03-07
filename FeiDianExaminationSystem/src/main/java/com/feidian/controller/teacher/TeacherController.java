package com.feidian.controller.teacher;

import com.feidian.mapper.ExamMapper;
import com.feidian.pojo.dto.DelTestDTO;
import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.dto.TestDTO;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Test;
import com.feidian.pojo.vo.*;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.service.CourseService;
import com.feidian.service.ExamService;
import com.feidian.service.TeacherService;
import com.feidian.service.TestService;
import com.feidian.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam/teacher")
@Slf4j
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TestService testService;
    @Autowired
    private ExamService examService;
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
    @Cacheable(cacheNames = "teacher",key = "#teacherId") //key: teacher::1
    public Result<TeacherInfoVO> getById(@PathVariable("teacherId") Integer teacherId){
        log.info("查询教师信息,id为：{}",teacherId);

        //得到VO对象
        TeacherInfoVO teacherInfoVO = teacherService.getInfoById(teacherId);

        return Result.success(teacherInfoVO);
    }


    /**
     * 获取课程信息
     * @param teacherId
     * @return
     */
    @GetMapping("/course/{teacherId}")
    @Cacheable(cacheNames = "teacherCourse",key = "#teacherId") //key: teacherCourse::1
    public Result<CourseVO> getCourseInfo(@PathVariable("teacherId") Integer teacherId){
        log.info("查询考试课程信息：{}",teacherId);

        List<Course> courses = courseService.getListByTchId(teacherId);

        //build构建对象（设计模式的一种），需要在实体类上加@Builder注解
        CourseVO courseVO = CourseVO.builder()
                .courses(courses)
                .build();

        return Result.success(courseVO);
    }

    /**
     * 通过课程id获取试题列表
     * @param courseId
     * @return
     */
    @GetMapping("/course/test/{courseId}")
    @Cacheable(cacheNames = "courseTest",key = "#courseId") //key: courseTest::1
    public Result<TestVO> getTestsByCourseId(@PathVariable("courseId") Integer courseId){

        log.info("通过课程id获取试题列表");

        //先获得Test对象列表
        List<Test> tests = testService.getListByCourseId(courseId);

        //然后创建TestVO对象
        TestVO testVO = TestVO.builder()
                .tests(tests)
                .build();

        return Result.success(testVO);
    }


    /**
     * 通过试题id列表批量删除试题
     * @return
     */
    @DeleteMapping("/course/test/delete")
    @CacheEvict(cacheNames = {"courseTest", "studentExamTest"}, allEntries = true)//删除courseTest和studentExamTest下所有的缓存数据
    public Result deleteTestByIds(@RequestParam List<Integer> ids){

        log.info("通过试题id列表批量删除试题");

        testService.deleteByIds(ids);

        return Result.success();
    }

    /**
     * 增加试题
     * @param testDTO
     * @return
     */
    @PostMapping("/course/test/add")
    @CacheEvict(cacheNames = {"courseTest", "studentExamTest"}, allEntries = true)//删除courseTest和studentExamTest下所有的缓存数据
    public Result addTest(@RequestBody TestDTO testDTO){

        log.info("增加试题");

        testService.add(testDTO);

        return Result.success();
    }


    /**
     * 修改试题
     * @param test
     * @return
     */
    @PutMapping("/course/test/update")
    @CacheEvict(cacheNames = {"courseTest", "studentExamTest"}, allEntries = true)//删除courseTest和studentExamTest下所有的缓存数据
    public Result updateTest(@RequestBody Test test){

        log.info("修改试题");

        testService.update(test);

        return Result.success();
    }

    /**
     * 获取考试情况
     * @param courseId
     * @return
     */
    @GetMapping("/examSitu/{courseId}")
    @Cacheable(cacheNames = "examDetailByCourseId",key = "#courseId") //key: examDetailByCourseId::1
    public Result<ExamSituVO> getExamSitusByCourseId(@PathVariable("courseId") Integer courseId){

        log.info("获取考试情况");

        ExamSituVO examSituVO = examService.getSitusByCourseId(courseId);

        return Result.success(examSituVO);
    }

    /**
     * 通过课程id和学生id获取某个学生某个课程的详细考试信息
     * @param courseId
     * @param stuId
     * @return
     */
    @GetMapping("/examSitu/{courseId}/{stuId}")
    @Cacheable(cacheNames = "examDetailByCourseIdAndStuId",key = "#courseId + '_' + #stuId") //key: examDetailByCourseIdAndStuId::1_1
    public Result<StuExamDetailVO> getStuExamDetailByStuIdAndCourseId(
            @PathVariable("courseId") Integer courseId,
            @PathVariable("stuId") Integer stuId){

        log.info("通过课程id和学生id获取某个学生某个课程的详细考试信息");

        StuExamDetailVO stuExamSituVO = examService.getStuDetailByStuIdAndCourseId(stuId,courseId);

        return Result.success(stuExamSituVO);
    }
}
