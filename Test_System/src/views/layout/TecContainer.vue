<template>
    <div>
        <el-container class="common-layout">
            <el-header>
                <h1>沸点在线考试系统
                    <button @click="Leave">退出登录</button>
                </h1>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <h3 class="LayoutTop">
                        <el-icon><MessageBox /></el-icon>
                        教师端考试系统</h3>
                    <el-menu
                        active-text-color="#ffd04b"
                        background-color="#545c64"
                        class="el-menu-vertical-demo"
                        default-active="1"
                        text-color="#fff"
                    > 
                     <!-- default-active默认高亮的是index为1的menu-item -->
                        <el-menu-item index="1"  @click="toTecMsg">
                            <el-icon><UserFilled /></el-icon>
                            <span>个人信息</span>
                        </el-menu-item>
                        <el-menu-item index="2"  @click="toTecCourse">
                          <el-icon><Collection /></el-icon>
                            <span>教授课程</span>
                        </el-menu-item>
                        <el-menu-item index="3"  @click="toTecTest">
                            <el-icon><Checked /></el-icon>                            
                            <span>考试情况</span>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
import { ElIcon } from 'element-plus'
import {UserFilled,Checked,Collection,MessageBox } from '@element-plus/icons-vue'

import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

//为个人信息绑定上路由
const toTecMsg=function(){
    router.push({name:'teacherMsg'})
}
const toTecCourse=function(){
    router.push({name:'teacherCourse'})
}
const toTecTest=function(){
    router.push({name:'teacherTest'})
}

function Leave(){
        localStorage.removeItem("TOKEN_KEY")
        router.push('/exam/student/login')
    }

</script>

<style>
    .common-layout{
        height: 100vh;
        
    }
    .el-header{
        background-color:#b1b3b8;
        border-radius:5px ;
        height: 80px;
        text-align: center;
        }
    .el-aside{
        background-color:#73767a;
        text-align: center;  
    }

    .el-menu-item{
        height: 100px;
        font-size:18px;
    }
    h1,h3{
        font-weight: lighter;
    }
    h1{
        text-align: center;
        color: white;
        font-size: 30px;
    }
    h3{
        color: azure;
    }
    
    /* .el-main{
        background-color: purple;
    } */
</style>