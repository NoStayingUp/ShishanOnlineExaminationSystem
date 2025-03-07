<template>
    学生考试页面（考试分数显示为最后一次考试分数）
    <div class="container">
      <el-card 
       v-for="course in courseData" 
       :key="course.courseId" 
       shadow="hover"
       @click="toTest(course.courseId)"
      >
        <div class="card">          
          <span>{{course.courseName}}</span>         
          <span v-show="course.status==0">该门课程未考试</span>
          <span v-show="course.status!=0">考试分数为：{{ course.score }}</span>
        </div>
      </el-card>
    </div>
</template>
  
<script setup>
  import { onMounted,  ref } from 'vue'
  import { useRouter} from 'vue-router'
  import {stuCourseService} from '@/api/stu'
  const router = useRouter()


  var courseData=ref([])

    onMounted(()=>{
      GetCourse ()
    })

    async function GetCourse (){
      try{ 
        let res=await stuCourseService()
        courseData.value=res.data.data
      }catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

    const toTest=function(id){                
        router.push({
          path:'/examInfo/stuTest/exam',
          query:{
            courseId:id
          }
          //去考试的时候要传一个课程id 去对应的页面考试
          //跳转完之后立马发请求 请求的数据存起来挂载到页面上
        })
             
    }
</script>
  
<style scoped>

  .container{
    display: flex;
    flex-wrap: wrap;
  }

  .el-card{
    margin: 10px;
    width: 45%;  
    height: 150px; 
    background-color: #fab6b6;
   }

   .card{
    display: flex;
    justify-content: space-between;
    height: 150px;
    line-height: 150px;
   }

   
</style>