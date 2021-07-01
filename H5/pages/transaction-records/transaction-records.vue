<template>
	<!-- 交易记录 -->
	<view style="margin-left: 20rpx;">
		<block v-for="(item, index) in OrderInfo" :key="index">
			<view class="d-flex a-center" style="height: 150rpx; border-bottom: solid #ececec 2rpx;">
				<view style="width: 195rpx;">
					<view>{{item.skuId}}</view>
					<view style="font-size: 25rpx; color: #999999;">{{item.addDate}}</view>
				</view>
				<view style="width: 285rpx;">
					{{item.investType}}
				</view>
				<view style="width: 250rpx;">
					<view v-show="item.investType == '买涨'" style="color: red;">+ {{item.skuPrice}}</view>
					<view v-show="item.investType == '买跌'" style="color: #09BB07;">- {{item.skuPrice}}</view>
					<view style="font-size: 25rpx; color: #999999;">{{item.editDate}}</view>
				</view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				OrderInfo:[{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30080",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买涨",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买涨",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				},
				{
					orderId : "订单ID",
					orderType : "订单类型 1-买入 2-卖出",
					userId : "用户ID",
					orderStatus : "订单状态 0-预生成 1-已成交 2-已完成",
					skuId : "比特币",
					skuCode : "商品代码",
					skuQty : "商品数量",
					skuPrice : "30088",
					orderAmount : "订单金额",
					orderCharge : "订单手续费",
					addDate : "2021-06-29 15:14:00",
					editDate : "2021-06-29 15:15:00",
					orderCycle : "订单周期(单位：秒)",
					investAmount : "投资金额",
					investType : "买跌",
					expectedReturn : "预期收益",
					guaranteedAmount : "保底金额",
					addUserId : "创建人",
					editUserId : "修改人",
					del : "数据状态"
				}]
			}
		},
		methods: {
			
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
