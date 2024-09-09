package com.feidian.mapper;

import com.feidian.pojo.dto.TestDTO;
import com.feidian.pojo.entity.Test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {


    /**
     * 通过课程id获得对应试题列表
     * @param courseId
     * @return
     */
    @Select("select * from test where course_id = #{courseId}")
    List<Test> getListByCourseId(Integer courseId);

    /**
     * 新增试题
     * @param testDTO
     */
    @Insert("insert into test(body,answer,course_id) values (#{body},#{answer},#{courseId})")
    void add(TestDTO testDTO);

    /**
     * 修改试题
     * @param test
     */
    void update(Test test);
}
