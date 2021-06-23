import doRequest from '../utils/httpRequest.js'

const urls = {
  userLogin: "/login" //登陆授权
}

module.exports = {
  userLogin(data) {
    return doRequest('POST', urls.userLogin, data)
  }
}