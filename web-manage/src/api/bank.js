import request from '@/utils/request'

//获取银行卡列表
export function getBankList(data) {
  return request({
    url: '/bank/list',
    method: 'post',
    data: data
  })
}

//新增银行卡
export function addBank(data) {
  return request({
    url: '/bank/save',
    method: 'post',
    data: data
  })
}

//删除银行卡
export function removeBank(bankId) {
  return request({
    url: '/bank/delete',
    method: 'get',
    params: { bankId }
  })
}

