<template>
  <el-form ref="form" :model="ruleForm" :rules="rules">
    <el-form-item>
      <h1>教师端登录</h1>
    </el-form-item>
    <el-form-item prop="workNum">
      <el-input v-model="ruleForm.workNum" :prefix-icon="User" placeholder="请输入工号"> </el-input>
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
import { ElButton,ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'

import { useRouter} from 'vue-router'

const router = useRouter()

import { tecLoginService } from '@/api/tec'



const form = ref()
const ruleForm = reactive({
  workNum: '',
  password: ''
})

//配置表单校验规则
//1.非空校验required：true
const rules = reactive({
  workNum: [
    { required: true, message: '请输入工号', trigger: 'blur' },
    { min: 4, max: 4, message: '工号长度必须是4位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 13, message: '密码长度必须是5-13位', trigger: 'blur' }
  ]
})

const login = async () => {
  await form.value.validate()
  const res=await tecLoginService(ruleForm)
  localStorage.setItem("TOKEN_KEY",res.data.data.token);
  ElMessage.success('登陆成功')
  localStorage.setItem("Tec_ID",res.data.data.id);
  router.push({name:'teacherLayout'})
}  

</script>

<style scoped>
.button {
  width: 100%;
}
</style>
