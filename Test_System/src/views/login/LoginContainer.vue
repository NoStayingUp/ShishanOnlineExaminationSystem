<template>
  <el-row>
    <el-col :span="12">
      <img src="./login.jpg" class="bg" alt="" />
    </el-col>
    <el-col class="login" :span="8" :offset="3">
      <el-card>
        <div class="loginTop">
          <div @click="toStuLogin">学生端</div>
          <div @click="toTecLogin">教师端</div>
        </div>
           
      </el-card>
      
      <div class="form">
        <router-view></router-view>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

function toStuLogin() {
  router.push('/exam/student/login')
}

function toTecLogin() {
  router.push('/exam/teacher/login')
}
</script>

<style>
.bg {
  width: 700px;
  height: 100%;
}
.el-card{
  background-color: #909399;
}
.login{
  margin: auto;
}
.loginTop{
  display: flex;
  font-size: 18px;
  justify-content: space-around;
  height: 100%;
  
}
.loginTop div{
  text-align: center;
  width: 50%;
  height: 100%;
}
.form {
  margin: auto;
  border: 1px solid #909399;
  border-radius:5px ;
  padding: 50px;
}
</style>
