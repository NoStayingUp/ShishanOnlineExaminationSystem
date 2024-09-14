package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.NoDataException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.exception.WrongDataException;
import com.feidian.mapper.*;
import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.TestCacheDTO;
import com.feidian.pojo.dto.TestSubmit;
import com.feidian.pojo.dto.TestSubmitDTO;
import com.feidian.pojo.entity.*;
import com.feidian.pojo.vo.*;
import com.feidian.service.StuService;
import com.feidian.utils.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class StuServiceImpl implements StuService {


    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestCacheMapper testCacheMapper;

    /**
     * 学生登录
     *
     * @param stuLoginDTO
     * @return
     */
    public StuLoginVO stuLogin(StuLoginDTO stuLoginDTO){

        String stuNum = stuLoginDTO.getStuNum();
        String password = stuLoginDTO.getPassword();

        //通过学号获取学生信息
        Student stu = studentMapper.getStuByNum(stuNum);
        //判断该学生是否存在
        if (stu == null) {
            //账号不存在
            throw new AccountNotFoundException("账号不存在");
        }

        //存在则进行下一步判断
        //对传过来的明文密码进行加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(stu.getPassword())){
            //密码错误
            throw new PasswordErrorException("密码错误");
        }

        //如果都正确，则返回数据
        StuLoginVO stuLoginVO = new StuLoginVO();
        stuLoginVO.setId(stu.getId());

        return stuLoginVO;
    }

    /**
     * 根据学生id获取学生信息
     * @param stuId
     * @return
     */
    public StuInfoVO getInfoById(Integer stuId) {

        //首先获得学生对象
        Student student = studentMapper.getById(stuId);

        if(student == null){
            throw new AccountNotFoundException("该学生id不存在");
        }
        //然后将其json字符串转为对应数据类型
        List<Integer> list = JSONUtil.jsonStrToList(student.getChooseCourse());
        if(list == null || list.size() == 0){
            throw new NoDataException("该学生没有选择任何课程");
        }
        //然后获取对应课程列表
        List<Course> chooseCourses = courseMapper.getCoursesByIds(list);
        //创建VO对象，并对其进行赋值
        StuInfoVO stuInfoVO = new StuInfoVO();
        //对象属性拷贝
        BeanUtils.copyProperties(student,stuInfoVO);
        stuInfoVO.setChooseCourses(chooseCourses);

        return stuInfoVO;
    }

    /**
     * 修改学生手机号
     * @param stuId
     * @param phone
     */
    public void updatePhoneById(Integer stuId, String phone) {

        Student student = studentMapper.getById(stuId);
        if(student == null){
            throw new AccountNotFoundException("该学生不存在");
        }
        if(phone.length() != 11){
            throw new WrongDataException("手机号格式错误");
        }

        studentMapper.updatePhoneById(stuId,phone);

    }

    /**
     * 根据学生id获取考试列表
     * @param stuId
     * @return
     */
    public List<StuExamVO> getStuExamListById(Integer stuId) {

        //先通过学生id得到学生对象
        Student stu = studentMapper.getById(stuId);
        if(stu == null){
            throw new AccountNotFoundException("该学生id不存在");
        }
        //再通过学生对象获得课程列表
        String chooseCourse = stu.getChooseCourse();
        List<Integer> courseIds = JSONUtil.jsonStrToList(chooseCourse);
        if(courseIds == null || courseIds.size() == 0){
            throw new NoDataException("该学生未选任何课程");
        }
        List<Course> courses = courseMapper.getCoursesByIds(courseIds);
        //创建返回列表
        List<StuExamVO> stuExamVOList = new ArrayList<>();
        //遍历课程列表，获取考试数据
        for (Course course : courses) {
            //创建VO对象,继承数据
            StuExamVO stuExamVO = StuExamVO.builder()
                    .courseId(course.getId())
                    .courseName(course.getName())
                    .build();
            //然后通过courseId和stuId查考试记录表,得到该考生该门课程的考试记录
            List<Exam> examList = examMapper.getListByStuIdAndCourseId(stuId, course.getId());
            //如果考试记录为空
            if (examList == null || examList.size() == 0) {
                //则表示该学生还未考这门课,status设为0，score设为空
                stuExamVO.setStatus(0);
            }else {
                //如果不为空
                //则表示该学生考了这门课，status设为1，score设为最近一次考试成绩
                stuExamVO.setStatus(1);
                //数据库无序情况下这样比较
                /*Exam earlyExam = examList.get(0);
                int n = examList.size();
                for (int i = 1; i < n; i++) {
                    //如果当前时间不是最新一次考试时间，那么更新最新一次考试
                    if(!earlyExam.getExamTime().isAfter(examList.get(i).getExamTime())){
                        earlyExam = examList.get(i);
                    }
                    //否则进入下一轮比较
                }*/
                //有序情况下，直接取最后一个,设置分数
                Exam exam = examList.get(examList.size() - 1);
                float score = exam.getScore();
                stuExamVO.setScore(score);
            }
            stuExamVOList.add(stuExamVO);
        }
        return stuExamVOList;
    }

    /**
     * 通过课程id获取试题信息
     *
     * @param courseId
     * @return
     */
    public List<StuTestVO> getTestListByCourseId(Integer courseId) {
        //验证课程id是否存在
        List<Integer> ids = new ArrayList<>();
        ids.add(courseId);
        List<Course> courses = courseMapper.getCoursesByIds(ids);
        if(courses == null || courses.size() == 0){
            throw new AccountNotFoundException("该课程id不存在");
        }

        //先通过课程id获取试题列表
        List<Test> testList = testMapper.getListByCourseId(courseId);
        if(testList == null || testList.size() == 0){
            throw new NoDataException("该课程无试题");
        }
        //然后通过试题列表得到StuTestVO列表
        List<StuTestVO> stuTestVOList = new ArrayList<>();
        for (Test test : testList) {
            StuTestVO stuTestVO = StuTestVO.builder()
                    .id(test.getId())
                    .body(test.getBody())
                    .build();
            stuTestVOList.add(stuTestVO);
        }

        return stuTestVOList;
    }

    /**
     * 提交答案，获取成绩
     * @param testSubmitDTO
     * @return
     */
    public ScoreVO getScore(TestSubmitDTO testSubmitDTO) {
        //首先判断传过来的数据是否为空
        List<TestSubmit> testSubmitList = testSubmitDTO.getTestSubmitList();
        if(testSubmitList.size() == 0 || testSubmitList == null){
            throw new NoDataException("不能提交空答案！");
        }

        float score = 0;
        //首先由课程id获取试题列表
        List<Test> testList = testMapper.getListByCourseId(testSubmitDTO.getCourseId());
        //判断课程是否存在
        if(testList == null || testList.size() == 0){
            throw new NoDataException("提交的课程id不存在");
        }
        //判断学生存不存在
        Student student = studentMapper.getById(testSubmitDTO.getStuId());
        if(student == null){
            throw new AccountNotFoundException("该学生不存在");
        }
        //然后遍历提交的试题
        for (TestSubmit testSubmit : testSubmitList) {
            int id = testSubmit.getId();
            String answer = testSubmit.getAnswer();
            for (Test test : testList) {
                //如果答案正确，则加分
                if(test.getId() == id && test.getAnswer().equals(answer)) {
                    score += 1;
                }
            }
        }
        //得到分数后还要将该考试记录存入考试表
        Exam exam = Exam.builder()
                .stuId(testSubmitDTO.getStuId())
                .courseId(testSubmitDTO.getCourseId())
                .score(score)
                .examTime(LocalDateTime.now())
                .build();
        examMapper.add(exam);

        //删除对应考试缓存
        testCacheMapper.delByStuIdAndCourseId(exam.getStuId(),exam.getCourseId());

        //然后返回数据
        ScoreVO scoreVO = ScoreVO.builder()
                .score(score)
                .build();

        return scoreVO;
    }

    /**
     * 添加学生考试试题缓存
     * @param stuId
     * @param courseId
     * @param testCacheDTOs
     */
    public void addTestCache(Integer stuId, Integer courseId, List<TestCacheDTO> testCacheDTOs) {
        //验证学生是否存在
        Student student = studentMapper.getById(stuId);
        if(student == null){
            throw new AccountNotFoundException("该学生不存在");
        }
        //判断课程是否存在
        List<Integer> ids = new ArrayList<>();
        ids.add(courseId);
        List<Course> courses = courseMapper.getCoursesByIds(ids);
        if(courses == null || courses.size() == 0){
            throw new AccountNotFoundException("该课程不存在");
        }
        //判断缓存是否存在
        if(testCacheDTOs == null || testCacheDTOs.size() == 0){
            throw new NoDataException("无题目缓存");
        }
        //首先获取TestCache列表
        List<TestCache> testCaches = new ArrayList<>();
        for (TestCacheDTO testCacheDTO : testCacheDTOs) {
            TestCache testCache = TestCache.builder()
                    .stuId(stuId)
                    .courseId(courseId)
                    .testId(testCacheDTO.getTestId())
                    .answer(testCacheDTO.getAnswer())
                    .build();
            testCaches.add(testCache);
        }
        //然后将TestCache列表存入数据库
        testCacheMapper.addList(testCaches);

    }

    /**
     * 获取考生考试试题缓存
     * @param stuId
     * @param courseId
     * @return
     */
    public List<TestCacheVO> getTestCache(Integer stuId, Integer courseId) {

        //验证学生是否存在
        Student student = studentMapper.getById(stuId);
        if(student == null){
            throw new AccountNotFoundException("该学生不存在");
        }
        //判断课程是否存在
        List<Integer> ids = new ArrayList<>();
        ids.add(courseId);
        List<Course> courses = courseMapper.getCoursesByIds(ids);
        if(courses == null || courses.size() == 0){
            throw new AccountNotFoundException("该课程不存在");
        }

        //通过学生id和课程id获取缓存列表
        List<TestCacheVO> testCacheVOList = testCacheMapper.getList(stuId,courseId);

        //判断缓存是否存在
//        if(testCacheVOList == null || testCacheVOList.size() == 0){
//            throw new NoDataException("无缓存");
//        }

        return testCacheVOList;
    }
}
