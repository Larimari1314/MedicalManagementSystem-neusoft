import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import './assets/theme/theme-darkblue/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import routes from './routes'
// import Mock from './mock'
// Mock.bootstrap();
import axios from 'axios'
Vue.prototype.$ajax = axios;
const xhr = new XMLHttpRequest();
xhr.withCredentials = true;
axios.defaults.baseURL ='http://localhost:8000/';
axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true;
import 'font-awesome/css/font-awesome.min.css'
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})
axios.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      if (error.response.status == 500) {
        sessionStorage.removeItem('user');
      } else {
        message.error("出错了");
        return Promise.reject(error);
      }
    }
);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

