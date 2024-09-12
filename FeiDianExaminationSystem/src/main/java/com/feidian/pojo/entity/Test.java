package com.feidian.pojo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    //试题id
    private int id;
    //试题题干
    private String body;
    //试题答案
    private String answer;
    //试题所属课程id
    private int courseId;
}
