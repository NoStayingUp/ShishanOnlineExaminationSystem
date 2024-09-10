package com.feidian.pojo.vo;

import com.feidian.pojo.entity.Course;
import lombok.Data;

import java.util.List;

@Data
public class StuInfoVO {
    //学生id
    private int id;

    //姓名
    private String name;

    //性别
    private char gender;

    //电话
    private String phone;

    //学号
    private String stuNum;

    //密码
    private String password;

    //学院
    private String college;

    //所选择的课程列表
    private List<Course> chooseCourses;
}
