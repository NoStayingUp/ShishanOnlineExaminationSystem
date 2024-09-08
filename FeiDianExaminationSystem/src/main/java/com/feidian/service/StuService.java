package com.feidian.service;

import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.vo.StuLoginVO;

public interface StuService {

    /**
     * 学生登录
     *
     * @param stuLoginDTO
     * @return
     */
    StuLoginVO stuLogin(StuLoginDTO stuLoginDTO);
}
