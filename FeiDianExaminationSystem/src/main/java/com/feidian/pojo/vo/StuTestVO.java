package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StuTestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //试题id
    private int id;
    //试题题干
    private String body;
}
