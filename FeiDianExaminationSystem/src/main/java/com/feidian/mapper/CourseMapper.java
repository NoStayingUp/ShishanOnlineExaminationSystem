package com.feidian.mapper;

import com.feidian.pojo.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    /**
     * 获取课程列表
     * @param ids
     * @return
     */
    List<Course> getCoursesByIds(@Param("ids") List<Integer> ids);
}
