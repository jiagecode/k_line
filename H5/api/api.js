import doRequest from '../utils/httpRequest.js'

const urls = {
  userLogin: "/userInfo/login", //登陆授权
  getUserInfo: "/userInfo/getUserInfo" ,//获取用户信息
  flowingWater: "/accountRecord/querylist" ,//获取交易记录
  myOrderList: "/orderInfo/queryMyOrder" ,//获取我的订单
  myCard: "/bankCardInfo/queryMyBankCard" ,//查询我的银行卡
  cashOutIn: "/cashOutIn/myCashOutList" ,//查询我的提现、充值记录
  myPosition: "/positionInfo/mySkuForList" ,//查询我的持仓
  myCusSku: "/skuCusInfo/queryMySku" ,//查询我的自选SKUCODE
}

module.exports = {
  userLogin(data) {
    return doRequest('POST', urls.userLogin, data)
  },
  queryFlowingWater(data) {
    return doRequest('POST', urls.flowingWater, data)
  },
  getUserInfo(data) {
    return doRequest('GET', urls.getUserInfo, data)
  },
  myOrderList(data){
    return doRequest('POST', urls.myOrderList, data)
  },
  myCardList(data){
    return doRequest('POST', urls.myCard, data)
  },
  myOutIn(data){
    return doRequest('POST', urls.cashOutIn, data)
  },
  myPosition(data){
    return doRequest('POST', urls.myPosition, data)
  },
  myCusSkuCode(data){
    return doRequest('POST', urls.myCusSku, data)
  }
}