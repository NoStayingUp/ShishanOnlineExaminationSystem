package com.feidian.pojo.vo;

import com.feidian.pojo.entity.ExamDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StuExamDetailVO {

    private List<ExamDetail> stuExamDetails;
}
