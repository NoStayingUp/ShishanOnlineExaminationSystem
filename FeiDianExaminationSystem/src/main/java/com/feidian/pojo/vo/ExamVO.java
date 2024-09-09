package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExamVO {

    //考试记录id
    private int id;
    //考生id（学生id）
    private int stuId;
    //课程id
    private int courseId;
    //考生姓名
    private String stuName;
    //课程名称
    private String courseName;
    //分数
    private float score;
    //考试时间
    private LocalDateTime examTime;
}
