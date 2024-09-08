package com.feidian.pojo.entity;

import lombok.Data;

@Data
public class Teacher {
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

    //所教授的课程
    private String taughtCourse;
}
