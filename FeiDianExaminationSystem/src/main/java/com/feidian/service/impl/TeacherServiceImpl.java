package com.feidian.service.impl;

import com.feidian.exception.AccountNotFoundException;
import com.feidian.exception.PasswordErrorException;
import com.feidian.mapper.CourseMapper;
import com.feidian.mapper.TeacherMapper;
import com.feidian.pojo.dto.TeacherLoginDTO;
import com.feidian.pojo.entity.Course;
import com.feidian.pojo.entity.Teacher;
import com.feidian.pojo.vo.TeacherInfoVO;
import com.feidian.pojo.vo.TeacherLoginVO;
import com.feidian.service.TeacherService;
import com.feidian.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

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

    /**
     * 根据id查询教师信息
     * @param teacherId
     * @return
     */
    public TeacherInfoVO getInfoById(Integer teacherId) {
        //首先获得教师对象
        Teacher teacher = teacherMapper.getById(teacherId);

        //判断教师是否存在
        if (teacher == null) {
            //抛出教师不存在异常
            throw new AccountNotFoundException("该教师id不存在");
        }

        //然后将其json字符串转为对应数据类型
        List<Integer> list = JSONUtil.jsonStrToList(teacher.getTaughtCourse());
        //然后获取对应课程列表
        List<Course> taughtCourses = courseMapper.getCoursesByIds(list);
        //创建VO对象，并对其进行赋值
        TeacherInfoVO teacherInfoVO = new TeacherInfoVO();
        //对象属性拷贝
        BeanUtils.copyProperties(teacher,teacherInfoVO);
        teacherInfoVO.setTaughtCourses(taughtCourses);

        return teacherInfoVO;
    }
}
