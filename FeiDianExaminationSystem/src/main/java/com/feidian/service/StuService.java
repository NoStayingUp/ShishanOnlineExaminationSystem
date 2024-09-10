package com.feidian.service;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.vo.StuInfoVO;
import com.feidian.pojo.vo.StuLoginVO;

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
}
