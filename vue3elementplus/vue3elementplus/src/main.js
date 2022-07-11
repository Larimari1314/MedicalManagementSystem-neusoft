import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"

import element from "element-plus"
import "element-plus/theme-chalk/index.css"

import "./index.css"
import "./styles/index.scss"

import axios from "axios"
const vapp = createApp(App)
//全局注册，之后可在其他组件中通过 this.$axios 发送数据，跨域访问
axios.defaults.baseURL = 'http://localhost:8000/mystu'
//axios挂载到vue实例
vapp.config.globalProperties.$axios = axios

vapp.use(router).use(store).use(element).mount("#app");
