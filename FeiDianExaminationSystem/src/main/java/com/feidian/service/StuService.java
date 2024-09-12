package com.feidian.service;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.dto.TestCacheDTO;
import com.feidian.pojo.dto.TestSubmitDTO;
import com.feidian.pojo.vo.TestCacheVO;
import com.feidian.pojo.vo.*;

import java.util.List;

public interface StuService {

    /**
     * 学生登录
     *
     * @param stuLoginDTO
     * @return
     */
    StuLoginVO stuLogin(StuLoginDTO stuLoginDTO);

    /**
     * 根据学生id获取学生信息
     * @param stuId
     * @return
     */
    StuInfoVO getInfoById(Integer stuId);

    /**
     * 修改学生手机号
     * @param stuId
     * @param phone
     */
    void updatePhoneById(Integer stuId, String phone);

    /**
     * 根据学生id获取考试列表
     * @param stuId
     * @return
     */
    List<StuExamVO> getStuExamListById(Integer stuId);

    /**
     * 通过课程id获取试题信息
     *
     * @param courseId
     * @return
     */
    List<StuTestVO> getTestListByCourseId(Integer courseId);

    /**
     * 提交答案，获取成绩
     * @param testSubmitDTOList
     * @return
     */
    ScoreVO getScore(TestSubmitDTO testSubmitDTOList);

    /**
     * 添加学生考试试题缓存
     * @param stuId
     * @param courseId
     * @param testCacheDTOs
     */
    void addTestCache(Integer stuId, Integer courseId, List<TestCacheDTO> testCacheDTOs);

    /**
     * 获取考生考试试题缓存
     * @param stuId
     * @param courseId
     * @return
     */
    List<TestCacheVO> getTestCache(Integer stuId, Integer courseId);
}
