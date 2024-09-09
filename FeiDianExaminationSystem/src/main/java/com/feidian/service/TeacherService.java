package com.feidian.service;

import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.vo.TeacherInfoVO;
import com.feidian.pojo.vo.TeacherLoginVO;

public interface TeacherService {

    /**
     * 教师登录
     * @param teacherLoginDTO
     * @return
     */
    TeacherLoginVO teacherLogin(TeacherLoginDTO teacherLoginDTO);

    /**
     * 通过id获取教师信息
     * @param teacherId
     * @return
     */
    TeacherInfoVO getInfoById(Integer teacherId);
}
