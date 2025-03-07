<template>
    <el-card>
      <div class="card-header">
        <span>{{stuData.name}},欢迎登录沸点在线考试系统</span>
      </div>
    </el-card>
    
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
      {{stuData.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon v-show="stuData.gender==0"><Female /></el-icon>
          <el-icon v-show="stuData.gender==1"><Male /></el-icon>
          性别
        </div>
      </template>    
        {{stuData.gender===0?'女':'男'}}     
      
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Phone /></el-icon>
          手机号
        </div>
      </template>
      <span>{{stuData.phone}}</span>
        <el-input
          v-show="isShow"
          v-model="phoneNum"
          style="width: 240px"
          placeholder="请输入修改后的手机号"
        />
        <el-button 
        v-show="isShow"
        type="success" 
        :icon="Check" 
        circle 
        @click="submitChangePhone(phoneNum)"/>
        <el-button 
        v-show="!isShow"
        type="primary" 
        :icon="Edit" 
        circle 
        @click="change()"/>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Wallet /></el-icon>
          学号
        </div>
      </template>
      {{stuData.stuNum}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><School /></el-icon>
          所在学院
        </div>
      </template>
      {{stuData.college}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          <el-icon><Notebook /></el-icon>
          所选课程
        </div>
      </template>
        <span v-for="course in stuData.chooseCourses"
        :key="course.id">{{ course.name }}</span>
    </el-descriptions-item>
  </el-descriptions>
</template>
  
<script setup>
    import { ElIcon,ElDescriptionsItem } from 'element-plus'
    import {User,Notebook,School,Phone,Female,Male,Wallet,Edit,Check} from '@element-plus/icons-vue'
    import {  onMounted, ref } from 'vue'
    import {stuMessageService,stuChangePhoneService} from '@/api/stu'


    let stuData=ref({})
    
    let phoneNum=ref('')//这里存放修改后的手机号

    let isShow=ref(false)

    function change(){
      isShow.value=true
    }

    //修改学生手机号的操作
    async function submitChangePhone(num){
      isShow.value=false
      let res=await stuChangePhoneService(num)
      window.location.reload()
    }
      

    onMounted(()=>{
      GetMsg ()
    })

    //获取学生信息的操作
    async function GetMsg (){
      try{ 
        let res=await stuMessageService()
        stuData.value=res.data.data
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
        width: 200px;
      }

      span{
        margin-right: 20px;
      }
  </style>