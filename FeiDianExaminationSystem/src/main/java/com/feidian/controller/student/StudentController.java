package com.feidian.controller.student;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.StuUpdateDTO;
import com.feidian.pojo.dto.TestCacheDTO;
import com.feidian.pojo.dto.TestSubmitDTO;
import com.feidian.pojo.vo.TestCacheVO;
import com.feidian.pojo.vo.*;
import com.feidian.properties.JwtProperties;
import com.feidian.result.Result;
import com.feidian.service.StuService;
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
@RequestMapping("/exam/student")
@Slf4j
@CrossOrigin
public class StudentController {

    @Autowired
    private StuService stuService;
    @Autowired
    private TestService testService;
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
    @Cacheable(cacheNames = "student",key = "#stuId") //key: student::1
    public Result<StuInfoVO> getInfo(@PathVariable("stuId") Integer stuId){
        log.info("获取学生信息");

        StuInfoVO stuInfoVO = stuService.getInfoById(stuId);

        return Result.success(stuInfoVO);
    }


    /**
     * 修改学生手机号
     * @param stuUpdateDTO
     * @return
     */
    @PutMapping("/info/update")
    @CacheEvict(cacheNames = "student",allEntries = true)//删除student下所有的缓存数据
    public Result update(/*@PathVariable("stuId") Integer stuId, */@RequestBody StuUpdateDTO stuUpdateDTO){

        log.info("开始修改学生绑定手机号");

        String phone = stuUpdateDTO.getPhone();
        int stuId = stuUpdateDTO.getStuId();
        stuService.updatePhoneById(stuId,phone);

        return Result.success();
    }

    /**
     * 根据学生id获取考试列表
     * @param stuId
     * @return
     */
    @GetMapping("/examInfo/{stuId}")
    @Cacheable(cacheNames = "studentExam",key = "#stuId") //key: studentExam::1
    public Result<List<StuExamVO>> getExamList(@PathVariable Integer stuId){

        log.info("开始获取考试列表");

        List<StuExamVO> stuExamVOS = stuService.getStuExamListById(stuId);

        return Result.success(stuExamVOS);
    }

    /**
     * 通过课程id获取试题信息
     * @param courseId
     * @return
     */
    @GetMapping("//examInfo/test/{courseId}")
    @Cacheable(cacheNames = "studentExamTest",key = "#courseId") //key: studentExamTest::1
    public Result<List<StuTestVO>> getExamTests(@PathVariable("courseId") Integer courseId){

        log.info("开始获取考试试题");

        List<StuTestVO> stuExamVOList = stuService.getTestListByCourseId(courseId);

        return Result.success(stuExamVOList);
    }

    /**
     * 提交答案，获取成绩
     * @param testSubmitDTO
     * @return
     */
    @PostMapping("/examInfo/test/submit")
    @CacheEvict(cacheNames = "studentExamTest", allEntries = true)
    public Result<ScoreVO> getScore(@RequestBody TestSubmitDTO testSubmitDTO){

        log.info("提交试题:{}",testSubmitDTO);

        ScoreVO scoreVO = stuService.getScore(testSubmitDTO);

        return Result.success(scoreVO);
    }

    /**
     * 添加考试试题缓存
     * @param stuId
     * @param courseId
     * @param testCacheDTOs
     * @return
     */
    @PostMapping("/examInfo/testCache/sava/{stuId}/{courseId}")
    public Result saveTestCache(@PathVariable("stuId") Integer stuId, @PathVariable("courseId") Integer courseId, @RequestBody List<TestCacheDTO> testCacheDTOs){
        log.info("保存考试试题缓存");

        stuService.addTestCache(stuId,courseId,testCacheDTOs);

        return Result.success();
    }

    /**
     * 获取考生考试试题缓存
     * @param stuId
     * @param courseId
     * @return
     */
    @GetMapping("/examInfo/testCache/get/{stuId}/{courseId}")
    public Result<List<TestCacheVO>> getTestCache(@PathVariable("stuId") Integer stuId, @PathVariable("courseId") Integer courseId){

        log.info("获取考试试题缓存");

        List<TestCacheVO> testCacheVOS = stuService.getTestCache(stuId,courseId);

        return Result.success(testCacheVOS);
    }
}
