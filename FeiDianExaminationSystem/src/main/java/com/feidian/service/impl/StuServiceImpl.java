package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.mapper.StudentMapper;
import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.entity.Student;
import com.feidian.pojo.vo.StuLoginVO;
import com.feidian.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class StuServiceImpl implements StuService {


    @Autowired
    private StudentMapper studentMapper;

    /**
     * 学生登录
     *
     * @param stuLoginDTO
     * @return
     */
    public StuLoginVO stuLogin(StuLoginDTO stuLoginDTO){

        String stuNum = stuLoginDTO.getStuNum();
        String password = stuLoginDTO.getPassword();

        //通过学号获取学生信息
        Student stu = studentMapper.getStuByNum(stuNum);
        //判断该学生是否存在
        if (stu == null) {
            //账号不存在
            throw new AccountNotFoundException("账号不存在");
        }
        //加密

        //存在则进行下一步判断
        if(!password.equals(stu.getPassword())){
            //密码错误
            throw new PasswordErrorException("密码错误");
        }

        //如果都正确，则返回数据
        StuLoginVO stuLoginVO = new StuLoginVO();
        stuLoginVO.setId(stu.getId());

        return stuLoginVO;
    }
}
