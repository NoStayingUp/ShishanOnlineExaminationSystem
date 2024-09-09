package com.feidian.service.impl;

import com.feidian.mapper.CourseMapper;
import com.feidian.mapper.ExamMapper;
import com.feidian.mapper.StudentMapper;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Exam;
import com.feidian.pojo.entity.Student;
import com.feidian.pojo.vo.ExamSituVO;
import com.feidian.pojo.vo.ExamVO;
import com.feidian.service.ExamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 获取考试情况
     *
     * @param courseId
     * @return
     */
    public ExamSituVO getSitusByCourseId(Integer courseId) {

        //得到考试列表
        List<Exam> exams = examMapper.getListByCourseId(courseId);
        List<ExamVO> examSitus = new ArrayList<>();
        //获取课程名称
        List<Integer> ids = new ArrayList<>();
        ids.add(courseId);
        List<Course> courses = courseMapper.getCoursesByIds(ids);
        String courseName = courses.get(0).getName();

        //获取每个考生姓名
        for (Exam exam : exams) {
            int stuId = exam.getStuId();
            Student stu = studentMapper.getById(stuId);
            String stuName = stu.getName();
            //将其放入examVO中
            ExamVO examVO = ExamVO.builder()
                    .stuName(stuName)
                    .courseName(courseName)
                    .build();
            BeanUtils.copyProperties(exam, examVO);
            //将examVO加入考试情况列表
            examSitus.add(examVO);
        }
        //创建ExamSituVO对象
        ExamSituVO examSituVO = ExamSituVO.builder()
                .examSitus(examSitus)
                .build();

        return examSituVO;
    }
}
