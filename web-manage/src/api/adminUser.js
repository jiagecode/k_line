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
//封禁或解封用户账户
export function openOrForbid(data) {
  return request({
    url: '/sysUserInfo/openOrForbidAccount',
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
//资金报表查询
export function moneyTableData(data) {
  return request({
    url: '/sysUserInfo/moneyTableData',
    method: 'post',
    data:data
  })
}
//更新用户状态
export function queryHomeData(params) {
  return request({
    url: '/sysUserInfo/queryHomeData',
    method: 'get',
    params
  })
}
//统计提现金额
export function sumMoneyForCash(params) {
  return request({
    url: '/sysDiy/sumMoneyForCash',
    method: 'get',
    params
  })
}
//查看订单详情
export function orderDetail(params) {
  return request({
    url: '/sysDiy/orderDetail',
    method: 'get',
    params
  })
}
//查询代理商下拉框
export function queryOptData1() {
  return request({
    url: '/sysDiy/optionList1',
    method: 'get',
  })
}
export function queryAgentData1(params) {
  return request({
    url: '/sysUserInfo/queryAgentData',
    method: 'get',
    params
  })
}
//资金报表汇总
export function moneyTableAll(data) {
  return request({
    url: '/sysUserInfo/moneyTableTotal',
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
//更新用户类型
export function changeUserType(data) {
  return request({
    url: '/sysUserInfo/updateUserType',
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

//修改用户户余额
export function changeUserMoney(data) {
  return request({
    url: '/sysDiy/editAccount',
    method: 'post',
    data:data
  })
}

//审核提现、充值
export function checkCash(data) {
  return request({
    url: '/sysDiy/checkForCash',
    method: 'post',
    data:data
  })
}
//银行卡管理
export function editBankCard(data) {
  return request({
    url: '/sysDiy/editBankCard',
    method: 'post',
    data:data
  })
}


