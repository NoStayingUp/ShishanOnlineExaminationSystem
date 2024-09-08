package com.feidian.mapper;

import com.feidian.pojo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {

    /**
     * 根据工号查询教师信息
     * @param workNum
     * @return
     */
    @Select("select * from teacher where work_num = #{workNum}")
    Teacher getTeacherByNum(String workNum);
}
