package com.feidian.service.impl;

import com.feidian.exception.NoDataException;
import com.feidian.mapper.TestMapper;
import com.feidian.pojo.dto.TestDTO;
import com.feidian.pojo.entity.Test;
import com.feidian.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private TestMapper testMapper;

    /**
     * 通过课程id得到试题列表
     * @param courseId
     * @return
     */
    public List<Test> getListByCourseId(Integer courseId) {

        List<Test> tests = testMapper.getListByCourseId(courseId);

        if(tests == null || tests.size() == 0){
            //则抛出数据为空异常
            throw new NoDataException("该课程暂无试题!");
        }

        return tests;
    }

    /**
     * 新增试题
     * @param testDTO
     */
    public void add(TestDTO testDTO) {

        testMapper.add(testDTO);

    }
}
