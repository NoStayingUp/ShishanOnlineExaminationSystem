package com.feidian.pojo.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCache {

    //试题缓存id
    private int id;

    //考生id
    private int stuId;

    //课程id
    private int courseId;

    //试题id
    private int testId;

    //试题答案
    private String answer;
}
