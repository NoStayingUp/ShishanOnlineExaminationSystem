package com.feidian.mapper;

import com.feidian.pojo.entity.Exam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据课程id和学生id获取某个学生某门课程的考试记录
     * @param stuId
     * @param courseId
     * @return
     */
    @Select("select * from exam where course_id = #{courseId} and stu_id = #{stuId}")
    List<Exam> getListByStuIdAndCourseId(@Param("stuId") Integer stuId,@Param("courseId") Integer courseId);

    /**
     * 添加考试记录信息
     * @param exam
     */
    @Insert("insert into exam(stu_id, course_id, score, exam_time) values (#{stuId},#{courseId},#{score},#{examTime})")
    void add(Exam exam);
}
