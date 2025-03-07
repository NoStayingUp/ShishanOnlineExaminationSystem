<template>
    <div class="container">
      <el-card 
       v-for="course in TecCourseData" 
       :key="course.id" 
       shadow="hover"
       @click="CheckMsg(course.id)"
      >
        <div class="card">          
          <span>{{course.name}}</span>         
        </div>
      </el-card>
    </div>
</template>
  
<script setup>
  import {ref,onMounted} from 'vue'

  import {tecCourseService} from '@/api/tec'

  import { useRouter} from 'vue-router'

  const router = useRouter()

  var TecCourseData=ref([])

  //这里的请求与获取老师教授课程的请求一样
  async function GetTecCourse (){
      try{ 
        let res=await tecCourseService()
        TecCourseData.value=res.data.data.courses
      }catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

    onMounted(()=>{
      GetTecCourse ()
    })

    const CheckMsg=function(id){     
        router.push(
          {
            path:`/examInfo/tecTest/TestMsg`,
            query:{
              courseID:id
            }
            //这里query传参数 传的不同的参数会进入到不同科目的考试情况页面        
          })    
        
    }
</script>
  
<style>
  
</style>