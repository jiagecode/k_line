import request from '@/utils/request'

//获取白名单列表
export function getsafeList(data) {
  return request({
    url: '/whitelist/list',
    method: 'post',
    data
  })
}

//新增Ip地址
export function addIp(data) {
  return request({
    url: '/whitelist/save',
    method: 'post',
    data
  })
}
//删除IP地址
export function removeIp(params) {
  return request({
    url: '/whitelist/delete',
    method: 'get',
    params
  })
}

