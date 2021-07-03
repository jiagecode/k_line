import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { constantRoutes,dynamicRouter } from '@/router'

import request from '@/utils/request'

// 声明对象并初始化
const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    routers: ''
  }
}

// 声明state赋值
const state = getDefaultState()

// 声明
const mutations = {

  // 重新给state赋值
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  // 缓存账号
  SET_ACCOUNT: (state, account) => {
    state.account = account
  },
  // 缓存角色
  SET_ROUTERS: (state, routers) => {
    state.routers = routers
  }
}


// export function getAuthMenu(token) {
//   return request({
//     url: '/adminUser/getUserInfo',
//     method: 'POST',
//     params: { token }
//   })
// }

const actions = {

  // user/login = 路径文件名/actions下方法名
  login({ commit }, userInfo) {
    // 取出this.$store.dispatch中传的值
    const { username, password } = userInfo
    // 执行调用http请求
    return new Promise((resolve, reject) => {
      login({ sysUserCode: username.trim(), sysUserPassword: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data)
        setToken(data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit } ) {
    return new Promise((resolve, reject) => {

      getInfo().then(response => {

        sessionStorage.clear();
        const { data } = response

        if (!data) {
          reject('验证失败，请重新登录。')
        }

        const { name, avatar, menus, account, roles, perms } = data
        if(!perms){
          reject('该账号已被禁用，不能登录。')
        }

        let newmenus =  dynamicRouter(menus)
        let onwMenu = constantRoutes.concat(newmenus)
        console.log(roles )
        var btnPowerString = perms;
        // console.log(btnPowerString)
        if(btnPowerString){
          btnPowerString = btnPowerString.join(',');
          sessionStorage.setItem('btnPowerString',btnPowerString);
        }

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_ROUTERS', newmenus)
        commit('SET_ACCOUNT', account)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        sessionStorage.clear();
        // resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first  reset_state
      commit('RESET_STATE')
      resolve()
    })
  }
}

// 在store/modules文件夹里的user.js,声明 state, mutations, actions并释放出来。
export default {
  namespaced: true,
  state,
  mutations,
  actions
}

