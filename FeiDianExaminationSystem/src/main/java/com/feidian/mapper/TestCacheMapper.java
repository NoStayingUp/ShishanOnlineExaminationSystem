package com.feidian.mapper;

import com.feidian.pojo.entity.TestCache;
import com.feidian.pojo.vo.TestCacheVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestCacheMapper {

    /**
     * 添加试题缓存列表
     * @param testCaches
     */
    void addList(@Param("caches") List<TestCache> testCaches);

    /**
     * 获取试题缓存列表
     * @param stuId
     * @param courseId
     * @return
     */
    @Select("select test_id, answer from test_cache where course_id = #{courseId} and stu_id = #{stuId}")
    List<TestCacheVO> getList(@Param("stuId") Integer stuId, @Param("courseId") Integer courseId);

    @Delete("delete from test_cache where course_id = #{courseId} and stu_id = #{stuId}")
    void delByStuIdAndCourseId(@Param("stuId") int stuId,@Param("courseId") int courseId);
}
