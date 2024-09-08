package com.feidian.pojo.vo;

import lombok.Data;

@Data
public class TeacherLoginVO {

    //教师id
    private int id;

    //jwt令牌
    private String token;

}
