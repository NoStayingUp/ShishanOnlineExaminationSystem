package com.feidian.pojo.vo;

import lombok.Data;

@Data
public class StuLoginVO {

    //学生id
    private int id;

    //jwt令牌
    private String token;

}
