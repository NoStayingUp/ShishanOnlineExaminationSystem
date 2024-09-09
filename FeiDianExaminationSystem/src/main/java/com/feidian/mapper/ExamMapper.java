package com.feidian.mapper;

import com.feidian.pojo.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamMapper {


    /**
     * 根据课程id获取考试列表
     * @param courseId
     * @return
     */
    @Select("select * from exam where course_id = #{courseId}")
    List<Exam> getListByCourseId(Integer courseId);
}
