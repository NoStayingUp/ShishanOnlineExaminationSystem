package com.feidian.pojo.dto;

import lombok.Data;

@Data
public class StuUpdateDTO {

    //学生id
    private int stuId;

    //新的电话号码
    String phone;
}
