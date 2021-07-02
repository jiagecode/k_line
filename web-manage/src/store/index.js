import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'

// 启用Vuex
Vue.use(Vuex)

// Store的代码结构一般由State、Getters、Mutation、Actions这四种组成，
// 也可以理解Store是一个容器，Store里面的状态与单纯的全局变量是不一样的，
// 无法直接改变store中的状态。想要改变store中的状态，只有一个办法，显示地提交mutation。
const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user
  },
  getters
})

export default store
