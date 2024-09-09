package com.feidian.mapper;

import com.feidian.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import javax.swing.*;

@Mapper
public interface StudentMapper {


    /**
     * 根据学号查询学生信息
     * @param stuNum
     * @return
     */
    @Select("select * from student where stu_num = #{stuNum}")
    Student getStuByNum(String stuNum);

    /**
     * 通过学生id获取学生对象
     * @param stuId
     * @return
     */
    @Select("select * from student where id = #{stuId}")
    Student getById(int stuId);
}
