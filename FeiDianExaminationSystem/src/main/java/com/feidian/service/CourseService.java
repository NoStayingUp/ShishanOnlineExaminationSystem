package com.feidian.service;

import com.feidian.pojo.entity.Course;

import java.util.List;

public interface CourseService {

    /**
     * 通过教师id获取教授课程的列表
     * @param teacherId
     * @return
     */
    List<Course> getListByTchId(Integer teacherId);
}
