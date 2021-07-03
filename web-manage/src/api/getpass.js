import request from '@/utils/request'

//获取验证码
export function getcode(data) {
  return request({
    url: '/login/getMsgCode',
    method: 'post',
    data:data
  })
}
//找回密码
export function findPass(data) {
  return request({
    url: '/login/findPassword',
    method: 'post',
    data:data
  })
}
