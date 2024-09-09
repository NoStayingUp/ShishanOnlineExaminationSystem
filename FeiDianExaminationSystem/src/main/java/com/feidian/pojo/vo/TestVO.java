package com.feidian.pojo.vo;

import com.feidian.pojo.entity.Test;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestVO {

    private List<Test> tests;
}
