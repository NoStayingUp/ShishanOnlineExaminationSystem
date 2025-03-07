import { defineStore } from 'pinia'
import { ref,reactive } from 'vue'
import {stuMessageService} from '@/api/stu'
export const useUserStore = defineStore('big-user', () => {
    const token = ref('')
    const setToken = (newToken) => {
        token.value = newToken
    }

    const removeToken = () => {
        token.value = ''
    }

    // const stuMsg=reactive({})
    // const getStuMessage=async()=>{
    //   const res =await stuMessageService()
    //   stuMsg.value=res.data.data
    // }

    

    return {
        token,
        setToken,
        removeToken,
        // getStuMessage
    }
  },
  {
    persist:true
  }
)

