package com.feidian.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestSubmitDTO {

    //课程id
    private int courseId;

    //学生id
    private int stuId;

    //提交的试题列表
    private List<TestSubmit> testSubmitList;
}
