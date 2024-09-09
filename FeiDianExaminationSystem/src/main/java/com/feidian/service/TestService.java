package com.feidian.service;

import com.feidian.pojo.dto.DelTestDTO;
import com.feidian.pojo.dto.TestDTO;
import com.feidian.pojo.entity.Test;

import java.util.List;

public interface TestService {

    /**
     * 通过课程id得到试题列表
     * @param courseId
     * @return
     */
    List<Test> getListByCourseId(Integer courseId);

    /**
     * 新增试题
     * @param testDTO
     */
    void add(TestDTO testDTO);

    /**
     * 修改试题
     * @param test
     */
    void update(Test test);

    /**
     * 通过试题id列表批量删除试题
     * @param delTestDTO
     */
    void deleteByIds(DelTestDTO delTestDTO);


}
