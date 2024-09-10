package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.mapper.CourseMapper;
import com.feidian.mapper.StudentMapper;
import com.feidian.pojo.dto.StuLoginDTO;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Student;
import com.feidian.pojo.entity.Teacher;
import com.feidian.pojo.vo.StuInfoVO;
import com.feidian.pojo.vo.StuLoginVO;
import com.feidian.pojo.vo.TeacherInfoVO;
import com.feidian.service.StuService;
import com.feidian.utils.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StuServiceImpl implements StuService {


    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;

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

    /**
     * 根据学生id获取学生信息
     * @param stuId
     * @return
     */
    public StuInfoVO getInfoById(Integer stuId) {

        //首先获得学生对象
        Student student = studentMapper.getById(stuId);


        //然后将其json字符串转为对应数据类型
        List<Integer> list = JSONUtil.jsonStrToList(student.getChooseCourse());
        //然后获取对应课程列表
        List<Course> chooseCourses = courseMapper.getCoursesByIds(list);
        //创建VO对象，并对其进行赋值
        StuInfoVO stuInfoVO = new StuInfoVO();
        //对象属性拷贝
        BeanUtils.copyProperties(student,stuInfoVO);
        stuInfoVO.setChooseCourses(chooseCourses);

        return stuInfoVO;
    }

    /**
     * 修改学生手机号
     * @param stuId
     * @param phone
     */
    public void updatePhoneById(Integer stuId, String phone) {

        studentMapper.updatePhoneById(stuId,phone);

    }
}
