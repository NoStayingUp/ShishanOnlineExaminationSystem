package com.feidian.pojo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable{

    private static final long serialVersionUID = 1L;

    //课程id
    private int id;

    //课程名称
    private String name;
}
