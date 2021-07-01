import doRequest from '../utils/httpRequest.js'

const urls = {
  userLogin: "/userInfo/login", //登陆授权
  getUserInfo: "/userInfo/getUserInfo" //获取用户信息
}

module.exports = {
  userLogin(data) {
    return doRequest('POST', urls.userLogin, data)
  },
  getUserInfo(data) {
    return doRequest('GET', urls.getUserInfo, data)
  }
}