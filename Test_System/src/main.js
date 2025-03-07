import { createApp } from 'vue'

import App from './App.vue'
import router from './router'

import pinia from './stores/index'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'




const app = createApp(App)
app.use(ElementPlus)
app.use(pinia)
app.use(router).mount('#app')

//使用element-plus
// app.use(Icon)