<template>      
    <div class="AddButton">
      <div class="add" v-show="isShow">
        <el-input v-model="addQues.body" style="width: 240px" placeholder="请输入题干" />
        <el-input v-model="addQues.answer" style="width: 240px" placeholder="请输入答案" />
        <el-button 
          @click="confirmBtn(addQues)"
          type="info">
          确认添加
        </el-button>
      </div>
      <el-button 
          id="AddBtn"
          type="success"
          v-show="!isShow"
          @click="addBtn">
          添加试题
        </el-button>      
    </div>
    <div class="operateButton">
      <el-input
      v-model="InputQuestions"
      style="width: 200px"
      placeholder="在此搜索试题"
      :prefix-icon="Search"
      />
      <el-button 
        type="primary"
        @click="CheckQues(InputQuestions)">
        查询试题
      </el-button>
    </div>
  <el-table :data="QuesData" style="width: 100%">
    <el-table-column prop="body" label="题干信息" width="400" >      
      <template v-slot="scope">
        <div>
          <input 
          class="EditInput"
          v-model="scope.row.body" />
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="answer" label="答案" width="450">
      <template v-slot="scope">
        <div>
          <input 
          class="EditInput"
          v-model="scope.row.answer"/>
        </div>
      </template>
    </el-table-column>
    
    <el-table-column fixed="right" label="操作" min-width="120">
      <template v-slot="scope">
        <el-button 
          link 
          type="primary" 
          size="large" 
          @click="EditBtn(scope.row.id,scope.row.body,scope.row.answer)">
          修改
        </el-button>
        <el-button link type="primary" size="large" @click="handleDelete(scope.row.id)">     
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>

    import { onMounted, ref } from 'vue'
    import {Search } from '@element-plus/icons-vue'

    import {tecExamQueService,tecDeleteQuesService,tecAddQueService,tecEditQueService} from '@/api/tec'

    import { useRouter, useRoute } from 'vue-router'
    const router = useRouter()
    const route = useRoute()

    const courseId=route.query.courseId

    var isShow=ref(false)

    var InputQuestions=ref('')
    var addQues=ref({
      body:'',
      answer:'',
      courseId:courseId
    })


    var ExamId=ref([])

    function addBtn(){
      isShow.value=true
    }

    //查找试题的操作 利用正则表达式和过滤器
    function CheckQues() {
      var reg =new RegExp(InputQuestions.value)
      QuesData.value=QuesData.value.filter(item=>reg.test(item.body))
    }

    //确认添加试题的按钮 一按就发请求
    async function confirmBtn(data){
      isShow.value=false
      let res =await tecAddQueService(data)
      window.location.reload()
    }

    var QuesData =ref( [])

    //获取考试试题的操作
    async function GetExamQues (id){
      try{ 
        let res=await tecExamQueService(id) 
        QuesData.value=res.data.data.tests  
      }
      catch(error){
        console.log("服务异常")
        console.log(error)
      }
    }
    
    onMounted(()=>{
      GetExamQues (courseId)
    })

    //删除试题的操作 需要提供删除数据的id
    async function handleDelete(id){
      ExamId.value.push(id)//这里是单次删除 但传的数据是数组 利用数组方法将单个id字符转换成数组
      try{
        let res =await tecDeleteQuesService(ExamId.value)
        window.location.reload()
        // console.log(res.data.data)
      }
      catch (error){
        console.log("服务异常")
        console.log(error)
    }
  }

  //修改试题的操作
  async function EditBtn(id,body,answer){
      var EditQues=ref({
        id:id,
        body:body,
        answer:answer,
        courseId:courseId
      })
      try{
        let res =await tecEditQueService(EditQues.value)
      }
      catch (error){
        console.log("服务异常")
        console.log(error)
    }
  }

</script>

<style>
    .AddButton{
      float: right;
    }

    #AddBtn{
      float: right;
    }

    .EditInput {
    border: 0;
    }

    
  
</style>