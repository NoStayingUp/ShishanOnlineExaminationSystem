import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      redirect:'exam/student/login',//路由重定向 默认页面为学生端登录
      component:()=>import('../views/login/LoginContainer.vue'),
      children:[
        {
          path:'exam/teacher/login',
          component:()=>import('../views/login/TecLogin.vue')
        },
        {
          path:'exam/student/login',
          component:()=>import('../views/login/StuLogin.vue')
        },
      ]
    },
    

    //架子
    {
      name:'teacherLayout',
      path:'/exam/teacher',
      component:()=>import('../views/layout/TecContainer.vue'),
      redirect:'/info/tecMsg',
      children:[
        {
          name:'teacherMsg',
          path:'/info/tecMsg',
          component:()=>import('../views/Teacher/TecMessage.vue')
        },
        {
          name:'teacherCourse',
          path:'/course/TecCourse',
          component:()=>import('../views/Teacher/TecCourse.vue')
        },

        //查看试题的路由
        {
          path:'/course/TecCourse/Exam',
          component:()=>import('../views/Teacher/tecExamQues.vue'),
        },
        //查看考试课程的路由
        {
          name:'teacherTest',
          path:'/examInfo/tecTest',
          component:()=>import('../views/Teacher/tecTestCourseMsg.vue')
        },

        //查看考试情况的路由
        {
          name:'teacherTestMsg',
          path:'/examInfo/tecTest/TestMsg',
          component:()=>import('../views/Teacher/TestMsg.vue'),
        },

        //查看某个考生的全部考试情况的信息
        {
          name:'teacherAllTestMsg',
          path:'/examInfo/tecTest/TestMsg/All',
          component:()=>import('../views/Teacher/AllTestMsg.vue'),
        },

        
      ]
    },
    //架子
    {
      name:'studentLayout',
      path:'/exam/student',
      component:()=>import('../views/layout/StuContainer.vue'),
      redirect:'/info/stuMsg',
      children:[
        {
          name:'studentMsg',
          path:'/info/stuMsg',
          component:()=>import('../views/Student/StuMessage.vue')
        },
        {
          name:'studentTest',
          path:'/examInfo/stuTest',
          component:()=>import('../views/Student/StuTest.vue'),
        },
        
        //考试板块 二级路由
        {
          name:'studentExam',
          path:'/examInfo/stuTest/exam',
          component:()=>import('../views/Student/stuToExam.vue'),
        },
      ]
    },
  ]
})

//登录访问拦截 默认放行
//返回值是false 拦截from的地址页面
// router.beforeEach(()=>{
//   const useStore=useUserStore()
//   if(!useStore.token)
//   {
//     return false
//   }

// })


export default router
