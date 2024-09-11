package com.feidian.service;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.vo.StuExamVO;
import com.feidian.pojo.vo.StuInfoVO;
import com.feidian.pojo.vo.StuLoginVO;

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
}
