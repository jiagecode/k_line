import request from '@/utils/request'

//手续费设置
export function sxMoney(data) {
  return request({
    url: '/systemConfig/list',
    method: 'post',
    data:data
  })
}

//修改各种费
export function changeAllMoney(data) {
  return request({
    url: '/systemConfig/save',
    method: 'post',
    data:data
  })
}
