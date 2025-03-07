<template>
  <form action="">
    <el-form>
      <el-form-item v-for="eq in ExamQuestions" :key="eq.id" :label="eq.id">
        <span>{{eq.body}}</span>
        <el-input v-model="eq.answer"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitExam(ExamQuestions)">提交</el-button>
      </el-form-item>
    </el-form>
  </form>
</template>

<script setup>
  import { onBeforeUnmount, onMounted,ref } from 'vue'

  import {stuExamQueService,stuScoreQueService,stuPostAnswerService,stuGetAnswers} from '@/api/stu'

  import { useRoute } from 'vue-router'
  const route = useRoute()

  const courseId=route.query.courseId

  var ExamQuestions =ref([])

    onMounted(()=>{
      GetAnswers ()//获取试题缓存
      GetExamQues () //获取考题数据
    })

    //获取考试试题的操作
  async function GetExamQues (){
      try{ 
        let res=await stuExamQueService(courseId)
        ExamQuestions.value=res.data.data 
        // console.log(ExamQuestions.value)
      }
      catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

  //获取考试缓存的请求
  async function GetAnswers (){
      try{ 
        let res=await stuGetAnswers(courseId)
        // console.log(ExamQuestions.value[0].id)
        // console.log(res.data.data)//拿到的缓存是一个数组 封装的是几个对象 页面渲染问题

        //这里用双重循环对获取到的缓存与当前试题进行匹配 如果缓存数据中有与当前试题id相同的则把答案赋值
        for(var i=0;i<ExamQuestions.value.length;i++){
          for(var j=0;j<res.data.data.length;j++)
          if(ExamQuestions.value[i].id===res.data.data[j].testId)
          {
            ExamQuestions.value[i].answer=res.data.data[j].answer
          }
        }
      }
      catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

    //提交试题的请求 获取成绩 成绩会更新
    async function submitExam(data){
      let res =await stuScoreQueService(data)
      alert("你的成绩为："+res.data.data.score)
    }
    
    //保存考题的缓存 在页面销毁前发送请求 原是定时发送请求 但请求次数太频繁
    async function PostAnswer(){
      let Arraydata = ExamQuestions.value.map(item =>({testId: item.id, answer: item.answer}))
      let res =await stuPostAnswerService(Arraydata,courseId)
      // console.log(Arraydata)
    }

    //在页面被销毁之前 生命周期钩子调用保存当前试题答案缓存的操作
    onBeforeUnmount(()=>{
      PostAnswer()
    })

    
</script>

<style>

</style>