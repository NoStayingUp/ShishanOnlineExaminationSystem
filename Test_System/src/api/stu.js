import instance from '@/utils/request'


export function stuLoginService(data){
    return instance.post('/exam/student/login',data)
}//登录的请求 传数据过去 post请求


//获取学生信息的请求 
export function stuMessageService(){
    return instance.get(`/exam/student/info/${localStorage.getItem("Stu_ID")}`)
}

//修改学生信息 修改学生手机号的操作
export  function stuChangePhoneService(num){
    const data={
        stuId:localStorage.getItem("Stu_ID"),
        phone:num
    }
    return instance.put('/exam/student/info/update',data)
}

//获取考试信息的请求
export function stuCourseService(){
    return instance.get(`/exam/student/examInfo/${localStorage.getItem("Stu_ID")}`)
}

//获取考试课程的考题的请求
export function stuExamQueService(courseId){
    return instance.get(`/exam/student/examInfo/test/${courseId}`)
}



//提交学生答题情况并返回成绩的请求 post请求
export function stuScoreQueService(answers){
    const data={
        stuId: localStorage.getItem("Stu_ID"),
        courseId: localStorage.getItem("Stu_courseId"),
        testSubmitList:answers
    }
    return instance.post('/exam/student/examInfo/test/submit',data)
}

//提交试题缓存的请求 post ??有错误
export function stuPostAnswerService(answer,courseId){
    return instance.post(`/exam/student/examInfo/testCache/sava/${localStorage.getItem("Stu_ID")}/${courseId}`,answer)
}

//获取考试缓存的请求 
export function stuGetAnswers(courseId){
    return instance.get(`/exam/student/examInfo/testCache/get/${localStorage.getItem("Stu_ID")}/${courseId}`)
}
