import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/sysUserInfo/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/sysUserInfo/logout',
    method: 'get'
  })
}

export function getInfo() {
  return request({
    url: '/sysUserInfo/getUserInfo',
    method: 'get'
  })
}

//获取组织结构列表
export function getoffice(data) {
  return request({
    url: '/office/list',
    method: 'post',
    // data:JSON.stringify(data)
    data:data
  })
}
//获取菜单列表list
export function getmenu(data) {
  return request({
    url: '/sysMenuInfo/queryList',
    method: 'post',
    data:data
  })
}
//菜单新增/修改
export function addmenu(data) {
  return request({
    url: '/sysMenuInfo/addOrUpdateOne',
    method: 'post',
    data:data
  })
}
//菜单删除
export function removemenu(data) {
  return request({
    url: '/sysMenuInfo/delOne',
    method: 'post',
    data:data
  })
}



//首页
export function getHome(params) {
  return request({
    url: '/osIndex/count',
    method: 'get',
    params
  })
}

export function getIp(params) {
  return request({
    url: '/whitelist/verificationIp',
    method: 'get',
    params
  })
}
