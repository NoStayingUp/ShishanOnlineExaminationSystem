<template>
  <el-table :data="AllExamStuMsg" style="width: 100%">
        <el-table-column fixed prop="stuName" label="学生姓名" width="300" />
        <el-table-column prop="courseName" label="考试课程" width="400" />
        <el-table-column prop="examTime" label="考试时间" width="400" />  
        <el-table-column prop="score" label="考试成绩 " min-width="120"/>
    </el-table>
</template>

<script setup>

    import { useRouter, useRoute } from 'vue-router'
    import { onMounted,ref } from 'vue'
    import {tecAllMsgService} from '@/api/tec'


    const router = useRouter()
    const route = useRoute()

    const courseID=route.query.courseID
    const stuId=route.query.stuId
    var AllExamStuMsg=ref([])

    async function GetAllExamMSg (){
      try{ 
        let res=await tecAllMsgService(courseID,stuId)
        AllExamStuMsg.value=res.data.data.stuExamDetails
      }catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

    onMounted(()=>{
        GetAllExamMSg ()
    })
</script>

<style>

</style>