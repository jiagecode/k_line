import request from '@/utils/request'

//获取充值订单列表
export function getczList(data) {
  return request({
    url: '/topUp/list',
    method: 'post',
    data:data
  })
}

//删除充值订单
export function removeList(data) {
  return request({
    url: '/topUp/delete',
    method: 'post',
    data:data
  })
}
//更新订单状态
export function changeStatus(data) {
  return request({
    url: '/topUp/save',
    method: 'post',
    data:data
  })
}

