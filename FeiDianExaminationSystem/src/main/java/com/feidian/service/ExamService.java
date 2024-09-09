package com.feidian.service;

import com.feidian.pojo.vo.ExamSituVO;

public interface ExamService {

    /**
     * 获取考试情况
     *
     * @param courseId
     * @return
     */
    ExamSituVO getSitusByCourseId(Integer courseId);
}
