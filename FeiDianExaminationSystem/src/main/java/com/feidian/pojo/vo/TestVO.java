package com.feidian.pojo.vo;

import com.feidian.pojo.entity.Test;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Test> tests;
}
