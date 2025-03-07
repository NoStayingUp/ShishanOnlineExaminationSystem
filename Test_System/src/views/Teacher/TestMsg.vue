<template>
    <el-table :data="examStuMsg" style="width: 100%">
        <el-table-column fixed prop="stuId" label="学生学号" width="200" />
        <el-table-column fixed prop="stuName" label="学生姓名" width="200" />
        <el-table-column prop="courseName" label="考试课程" width="200" />
        <el-table-column prop="examTime" label="考试时间" width="300" />  
        <el-table-column prop="score" label="考试成绩 " min-width="120"/>
        <el-table-column fixed="right" label="操作" min-width="120">
            <template v-slot="scope">
                <el-button
                 link 
                 type="primary" 
                 size="large"
                 @click="CheckAll(scope.row.stuId)"
                 >
                查看详情
                </el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script setup>
    import {tecExamStuService} from '@/api/tec'

    import { onMounted,ref } from 'vue'

    import { useRouter, useRoute } from 'vue-router'

    const router = useRouter()
    const route = useRoute()

    var  courseID=route.query.courseID

    var examStuMsg=ref([])

    async function GetStuExamMSg (){
        try{ 
            let res=await tecExamStuService(courseID)
            examStuMsg.value=res.data.data.examSitus
        }catch(error){
            console.log("服务异常")
            console.log(error)
        }
    }

    onMounted(()=>{
      GetStuExamMSg()
    })

    //这里要查看某个考生的全部考试情况 要传的数据有考试课程id和学生id
    async function CheckAll(id){
        router.push({
            path:'/examInfo/tecTest/TestMsg/All',
            query:{
                courseID:courseID,
                stuId:id
            }
        })
    }
</script>

<style>

</style>