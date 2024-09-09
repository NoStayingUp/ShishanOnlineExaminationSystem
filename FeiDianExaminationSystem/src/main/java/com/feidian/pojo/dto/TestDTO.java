package com.feidian.pojo.dto;

import lombok.Data;

@Data
public class TestDTO {

    //题干
    private String body;
    //答案
    private String answer;
    //所属课程id
    private int courseId;
}
