package com.feidian.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DelTestDTO {

    //待删除的试题id
    private List<Integer> testIds;
}
