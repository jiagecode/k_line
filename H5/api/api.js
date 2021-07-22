import doRequest from '../utils/httpRequest.js'

const urls = {
	userLogin: "/userInfo/login", //登陆授权
	getUserInfo: "/userInfo/getUserInfo", //获取用户信息
	flowingWater: "/accountRecord/querylist", //获取交易记录
	myOrderList: "/orderInfo/queryMyOrder", //获取我的订单
	myCard: "/bankCardInfo/queryMyBankCard", //查询我的银行卡
	cashOutIn: "/cashOutIn/myCashOutList", //查询我的提现、充值记录
	myPosition: "/positionInfo/mySkuForList", //查询我的持仓
	myCusSku: "/skuCusInfo/queryMySku", //查询我的自选SKUCODE
	userRegister: "/userInfo/createUserInfo", // H5用户注册
	bankCardInfoSave: "/bankCardInfo/save", // 新增银行卡
	submitOrder: "/orderInfo/save", //提交订单
	klineDay: "/kline/day/list", //8日线
	klineMin: "/kline/min/list", //当日分钟线
	klineMils: "/kline/mils/list", //历史秒线
	saveSkuCusInfo:"/skuCusInfo/save", //添加自选
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
	myOrderList(data) {
		return doRequest('POST', urls.myOrderList, data)
	},
	myCardList(data) {
		return doRequest('POST', urls.myCard, data)
	},
	myOutIn(data) {
		return doRequest('POST', urls.cashOutIn, data)
	},
	myPosition(data) {
		return doRequest('POST', urls.myPosition, data)
	},
	myCusSkuCode(data) {
		return doRequest('POST', urls.myCusSku, data)
	},
	userRegister(data) {
		return doRequest('POST', urls.userRegister, data)
	},
	bankCardInfoSave(data) {
		return doRequest('POST', urls.bankCardInfoSave, data)
	},
	submitOrder(data) {
		return doRequest('POST', urls.submitOrder, data)
	},
	klineDay(data) {
		return doRequest('POST', urls.klineDay, data)
	},
	klineMin(data) {
		return doRequest('POST', urls.klineMin, data)
	},
	klineMils(data) {
		return doRequest('POST', urls.klineMils, data)
	},
	saveSkuCusInfo(data) {
		return doRequest('POST', urls.saveSkuCusInfo, data)
	}
}
