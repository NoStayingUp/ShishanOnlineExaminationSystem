package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.NoDataException;
import com.feidian.mapper.CourseMapper;
import com.feidian.mapper.TeacherMapper;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Teacher;
import com.feidian.service.CourseService;
import com.feidian.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 通过教师id获取教授课程的列表
     * @param teacherId
     * @return
     */
    public List<Course> getListByTchId(Integer teacherId) {

        //首先获得教师对象
        Teacher teacher = teacherMapper.getById(teacherId);
        if(teacher == null){
            //则教师不存在
            throw new AccountNotFoundException("教师不存在");
        }
        //获得教授课程的id
        String taughtCourse = teacher.getTaughtCourse();
        List<Integer> courseIds = JSONUtil.jsonStrToList(taughtCourse);
        if(courseIds == null || courseIds.size() < 1){
            //表示该教师没有教授任何一门课程
            throw new NoDataException("数据为空");
        }
        //然后得到课程列表
        List<Course> courses = courseMapper.getCoursesByIds(courseIds);

        return courses;
    }
}
