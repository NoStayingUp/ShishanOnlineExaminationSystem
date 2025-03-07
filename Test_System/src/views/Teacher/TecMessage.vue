<template>
    教师个人信息页面
    <el-descriptions
    class="margin-top"
    :column="1"    
    border
  >
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><User /></el-icon>
          姓名
        </div>
      </template>
      {{tecData.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon v-show="tecData.gender==0"><Female /></el-icon>
          <el-icon v-show="tecData.gender==1"><Male /></el-icon>
          性别
        </div>
      </template>
      {{tecData.gender===0?'女':'男'}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Phone /></el-icon>
          手机号
        </div>
      </template>
      {{tecData.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Wallet /></el-icon>
          工号
        </div>
      </template>
      {{tecData.workNum}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><School /></el-icon>
          所在学院
        </div>
      </template>
      {{tecData.college}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Notebook /></el-icon>
          教授课程
        </div>
      </template>
      <span 
        v-for="tecCourse in tecData.taughtCourses" 
        :key="tecCourse.id">
        {{ tecCourse.name }}
      </span>
    </el-descriptions-item>
  </el-descriptions>

  </template>
  
  <script setup>
    import { ElIcon,ElDescriptionsItem } from 'element-plus'
    import {User,Notebook,School,Phone,Female,Male,Wallet} from '@element-plus/icons-vue'
    import { onMounted,ref } from 'vue'

    import {tecMessageService} from '@/api/tec'

    var tecData=ref({})


    onMounted(()=>{
      Get ()
    })

    //获取老师的个人信息
    async function Get (){
      try{ 
        let res=await tecMessageService()
        tecData.value=res.data.data  
      }catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }

  </script>
  
<style>
  .cell-item{
      height: 70px;
      line-height: 70px;
      font-size: 20px;
    }

    span{
      margin-right: 20px;
    }
</style>