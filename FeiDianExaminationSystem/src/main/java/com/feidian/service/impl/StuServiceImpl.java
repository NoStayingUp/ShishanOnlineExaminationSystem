package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.mapper.CourseMapper;
import com.feidian.mapper.ExamMapper;
import com.feidian.mapper.StudentMapper;
import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Exam;
import com.feidian.pojo.entity.Student;
import com.feidian.pojo.entity.Teacher;
import com.feidian.pojo.vo.*;
import com.feidian.service.StuService;
import com.feidian.utils.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //加密

        //存在则进行下一步判断
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


        //然后将其json字符串转为对应数据类型
        List<Integer> list = JSONUtil.jsonStrToList(student.getChooseCourse());
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
        //再通过学生对象获得课程列表
        String chooseCourse = stu.getChooseCourse();
        List<Integer> courseIds = JSONUtil.jsonStrToList(chooseCourse);
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
}
