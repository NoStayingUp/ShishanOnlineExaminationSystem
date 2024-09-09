package com.feidian.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamSituVO {

    //考试情况列表
    private List<ExamVO> examSitus;


}
