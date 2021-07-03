import request from '@/utils/request'

//获取交易订单列表
export function getjyList(data) {
  return request({
    url: '/pay/list',
    method: 'post',
    data:data
  })
}

//删除交易订单
export function removejyList(data) {
  return request({
    url: '/pay/delete',
    method: 'post',
    data:data
  })
}

//修改状态
export function changeList(data) {
  return request({
    url: '/pay/save',
    method: 'post',
    data:data
  })
}

//操作
export function caozuo(data) {
  return request({
    url: '/pay/edit',
    method: 'post',
    data:data
  })
}



