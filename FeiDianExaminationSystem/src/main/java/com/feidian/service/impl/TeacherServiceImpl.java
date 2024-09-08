package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.mapper.TeacherMapper;
import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.entity.Teacher;
import com.feidian.pojo.vo.TeacherLoginVO;
import com.feidian.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 教师登录
     * @param teacherLoginDTO
     * @return
     */
    public TeacherLoginVO teacherLogin(TeacherLoginDTO teacherLoginDTO) {
        String workNum = teacherLoginDTO.getWorkNum();
        String password = teacherLoginDTO.getPassword();

        //通过学号获取学生信息
        Teacher teacher = teacherMapper.getTeacherByNum(workNum);
        //判断该教师是否存在
        if (teacher == null) {
            //账号不存在
            throw new AccountNotFoundException("账号不存在");
        }
        //加密

        //存在则进行下一步判断
        if(!password.equals(teacher.getPassword())){
            //密码错误
            throw new PasswordErrorException("密码错误");
        }

        //如果都正确，则返回数据
        TeacherLoginVO ttuLoginVO = new TeacherLoginVO();
        ttuLoginVO.setId(teacher.getId());

        return ttuLoginVO;
    }
}
