import instance from '@/utils/request'

export function tecLoginService(data){
    return instance.post('/exam/teacher/login',data)
}//登录的请求


//获取教师个人信息的请求
export function tecMessageService(){
    return instance.get(`/exam/teacher/info/${localStorage.getItem("Tec_ID")}`)
}

//获取教授课程的请求
export function tecCourseService(){
    return instance.get(`/exam/teacher/course/${localStorage.getItem("Tec_ID")}`)
}

//获取考试试题的请求
export function tecExamQueService(courseid){
    return instance.get(`/exam/teacher/course/test/${courseid}`)
}

//添加考试试题的请求
export function tecAddQueService(data){
    return instance.post('/exam/teacher/course/test/add',data)
}

//修改考试试题的请求
export function tecEditQueService(data){
    return instance.put('/exam/teacher/course/test/update',data)
}


//删除考试试题的请求
export function tecDeleteQuesService(ExamId){
    return instance.delete(`/exam/teacher/course/test/delete?ids=${ExamId}`)

}

//获取学生考试情况的请求
export function tecExamStuService(courseid){
    return instance.get(`/exam/teacher/examSitu/${courseid}`)
}

//查看该考生的全部考试信息
export function tecAllMsgService(courseId,stuId){
    return instance.get(`/exam/teacher/examSitu/${courseId}/${stuId}`)
}
