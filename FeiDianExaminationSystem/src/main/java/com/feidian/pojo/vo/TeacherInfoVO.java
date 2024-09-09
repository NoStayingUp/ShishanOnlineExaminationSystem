package com.feidian.pojo.vo;

import com.feidian.pojo.entity.Course;
import lombok.Data;

import java.util.List;

@Data
public class TeacherInfoVO {
    //教师id
    private int id;

    //姓名
    private String name;

    //性别
    private char gender;

    //电话
    private String phone;

    //工号
    private String workNum;

    //密码
    private String password;

    //学院
    private String college;

    //所教授的课程列表
    private List<Course> taughtCourses;
}
