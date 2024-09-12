package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StuExamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //课程id
    private int courseId;

    //课程名称
    private String courseName;

    //考试状态(1为已经考过，0为未考
    private int status;

    //考试分数(未考则为空，考了则显示最新一次
    private float score;
}
