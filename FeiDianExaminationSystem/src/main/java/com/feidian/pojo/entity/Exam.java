package com.feidian.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {

    //考试记录id
    private int id;
    //考生id（学生id）
    private int stuId;
    //课程id
    private int courseId;
    //分数
    private float score;
    //考试时间
    private LocalDateTime examTime;
}
