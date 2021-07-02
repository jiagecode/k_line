import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import echarts from 'echarts'

// main.js是项目的入口文件，
// 项目中所有的页面都会加载main.js,
// 所以main.js,主要有三个作用：
// 1.实例化Vue。
// 2.放置项目中经常会用到的插件和CSS样式。例如： 网络请求插件:axios和vue-resource、图片懒加载插件：vue-lazyload
// 3.存储全局变量



Vue.prototype.$echarts = echarts
// 启用echarts
Vue.use(echarts);

// v-has自定义命令
import btnControl from '../btnPermissions';
Vue.use(btnControl);

Vue.prototype.formatDate=function(now) {
  var year=now.getFullYear();
  var month=now.getMonth()+1;
  if(month<10){
    month = '0'+month
  }
  var date=now.getDate();
  if(date<10){
    date = '0'+date
  }
  return year+"-"+month+"-"+date;
}

import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  console.log(process.env.NODE_ENV)
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)

Vue.config.productionTip = false

//复制
Vue.prototype.copy=function (e) {
  let url = e;
  let oInput = document.createElement('input');
  oInput.value = url;
  document.body.appendChild(oInput);
  oInput.select(); // 选择对象;
  document.execCommand("Copy"); // 执行浏览器复制命令
  this.$message({
    message: '复制成功',
    type: 'success'
  });
  oInput.remove()
}
//
// import axios from 'axios'
// axios.interceptors.request.use(config => {
//   return config
// });
// axios.interceptors.response.use(response => {
//   return response
// });
// axios.defaults.withCredentials=true;
//
// Vue.prototype.axios = axios;
// //本地开发
// axios.defaults.baseURL = '/api';

// 页面入口
new Vue({
  el: '#app',
  router,
  store, //
  render: h => h(App)
})
