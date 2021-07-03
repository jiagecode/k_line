import request from '@/utils/request'

//角色列表
export function listPeople(data) {
  return request({
    url: '/role/list',
    method: 'post',
    data:data
  })
}
//角色新增
export function addPeople(data) {
  return request({
    url: '/role/insert',
    method: 'post',
    data:data
  })
}
//角色删除
export function removePeople(data) {
  return request({
    url: '/role/del',
    method: 'post',
    data:data
  })
}
//获取角色授权
export function getpeopleSq(data) {
  return request({
    url: '/role/authorize',
    method: 'post',
    data
  })
}
//给角色授权
export function sqtoPeople(data) {
  return request({
    url: '/roleMenu/saveRoleMenu',
    method: 'post',
    data
  })
}



