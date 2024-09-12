package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ExamSituVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //考试情况列表
    private List<ExamVO> examSitus;


}
