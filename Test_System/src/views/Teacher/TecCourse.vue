<template>
    教师考试情况页面
    <div class="container">
      <el-card 
       v-for="course in TecCourseData" 
       :key="course.id" 
       shadow="hover"
       @click="toTest(course.id)"
      >
        <div class="card">          
          <span>{{course.name}}</span>         
        </div>
      </el-card>
    </div>
  </template>
  
  <script setup>

    import { onMounted,reactive, ref } from 'vue'

    import { useRouter, useRoute } from 'vue-router'


    const router = useRouter()
    const route = useRoute()


    import {tecCourseService} from '@/api/tec'

    var TecCourseData=ref([
        // {
        //   id:'1',
        //   name:'数学'
        // },
        // {
        //   id:'2',
        //   name:'英语'
        // },
      ])

    const toTest=function(id){     
        router.push(
          {
            path:'/course/TecCourse/Exam',
            query:{
              courseId:id
            }
          })    
    }

    
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

    

  </script>
  
<style>
      .container{
    display: flex;
    flex-wrap: wrap;
  }

  .el-card{
    margin: 10px;
    width: 45%;  
    height: 150px; 
    background-color: pink;
   }

   .card{
    display: flex;
    justify-content: space-between;
    height: 150px;
    line-height: 150px;
   }

</style>