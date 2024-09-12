package com.feidian.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExamDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    //考试id
    private int id;
    //考生姓名
    private String stuName;
    //课程名称
    private String courseName;
    //考试时间
    private LocalDateTime examTime;
    //考试分数
    private float score;
}
