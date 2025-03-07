<template>
  <el-form ref="form" :model="ruleForm" :rules="rules">
    <el-form-item>
      <h1>学生端登录</h1>
    </el-form-item>
    <el-form-item prop="stuNum">
      <el-input v-model="ruleForm.stuNum" :prefix-icon="User" placeholder="请输入学号"> </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        v-model="ruleForm.password"
        :prefix-icon="Lock"
        name="password"
        type="password"
        placeholder="请输入密码"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="login" type="primary" plain class="button">登录</el-button>
    </el-form-item>
  </el-form>
  
</template>

<script setup>
import { ElButton, ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'

import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

import { useUserStore } from '@/stores'
const userStore=useUserStore()

import { stuLoginService } from '@/api/stu'

const form = ref()
const ruleForm = reactive({
  stuNum: '',
  password: ''
})



//配置表单校验规则
//1.非空校验required：true
const rules = reactive({
  stuNum: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 6, max: 6, message: '学号长度必须是6位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 13, message: '密码长度必须是5-13位', trigger: 'blur' }
  ]
})

// 完成登录时的预校验
const login = async () => {
  await form.value.validate()
  //完成校验后发送登录请求 同时跳转到主页面
  const res=await stuLoginService(ruleForm)
  //携带token 如果没有携带token后面的页面会出错
  localStorage.setItem("TOKEN_KEY",res.data.data.token);
  ElMessage.success('登陆成功')
  localStorage.setItem("Stu_ID",res.data.data.id);
  router.push({
    name:'studentLayout',
  })
}  

  

</script>

<style scoped>
.button {
  width: 100%;
}
</style>
