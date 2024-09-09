package com.feidian.service;

import com.feidian.pojo.vo.ExamSituVO;
import com.feidian.pojo.vo.StuExamDetailVO;

public interface ExamService {

    /**
     * 获取考试情况
     *
     * @param courseId
     * @return
     */
    ExamSituVO getSitusByCourseId(Integer courseId);

    /**
     * 通过课程id和学生id获取一个考生这门考试的详细信息
     * @param stuId
     * @param courseId
     * @return
     */
    StuExamDetailVO getStuDetailByStuIdAndCourseId(Integer stuId, Integer courseId);
}
