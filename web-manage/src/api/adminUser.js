import request from '@/utils/request'

//获取用户信息列表
export function listUser(data) {
  return request({
    url: 'sysUserInfo/queryInfoList',
    method: 'post',
    data:data
  })
}
//查询角色列表
export function listPeople(data) {
  return request({
    url: '/sysUserInfo/queryInfoList',
    method: 'post',
    data:data
  })
}
//查询角色列表
export function listCashVo(data) {
  return request({
    url: '/sysUserInfo/queryCashDetail',
    method: 'post',
    data:data
  })
}
//查询订单流水
export function orderVoList(data) {
  return request({
    url: '/sysUserInfo/queryOrderDataList',
    method: 'post',
    data:data
  })
}
//查询平仓日志
export function pingCangDataVo(data) {
  return request({
    url: '/sysUserInfo/queryManRecordVoForPage',
    method: 'post',
    data:data
  })
}
//查询银行卡
export function queryBankVoData(data) {
  return request({
    url: '/sysUserInfo/queryBankVoForPage',
    method: 'post',
    data:data
  })
}
//新增用户
export function addUser(data) {
  return request({
    url: '/sysUserInfo/addNewOne',
    method: 'post',
    data:data
  })
}

//删除用户
export function removeUser(data) {
  return request({
    url: '/adminUser/del',
    method: 'post',
    data:data
  })
}

//更新用户状态
export function changeUser(data) {
  return request({
    url: '/adminUser/update',
    method: 'post',
    data:data
  })
}

//账户管理
export function guanliUser(data) {
  return request({
    url: '/adminUser/managementAccount',
    method: 'post',
    data:data
  })
}

//修改商户余额
export function changeUserMoney(data) {
  return request({
    url: '/consumption/updateMerchantAmount',
    method: 'post',
    data:data
  })
}


