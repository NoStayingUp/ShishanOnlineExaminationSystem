package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StuTestVO {

    //试题id
    private int id;
    //试题题干
    private String body;
}
