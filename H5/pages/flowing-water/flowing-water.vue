<template>
	<view style="padding: 0 4% 0 4%">
		<block v-for="(item, index) in dataList" :key="item.recordId">
			<!-- 根据月份拆分 
			<view style="height: 95rpx; background-color: red;"></view>-->
			<!-- 明细 -->
			<view class="d-flex a-center" style="height: 160rpx; border-top: #eeeeee solid 1rpx;">
				<!-- 消费图片 根据4种类型渲染不同的图片 -->
				<view>
				  <image style="background-color: #FFFFFF; width: 70rpx; height: 70rpx; border-radius: 50rpx;" src="/static/yh.png" />
				</view>
				<!-- 类别+时间 -->
				<view style="margin-left: 2.6%; width: 56%;">
				  <view class="font-mdd">{{ getCashTypeDesc(item.recordType) }}</view>
				  <view class="font-sm" style="color: #999999; ">{{ item.addDate }}</view>
				</view>
				<!-- 金额+状态 -->
				<view>
					<!-- 涨幅金额 
					<view class="font-mdd">{{ item.changeMoney }}</view>-->
					<!-- 账户金额 -->
					<view class="font-mdd">{{ item.afterMoney }}</view>
					<view class="font-sm" style="color: #999999; ">交易成功</view>
				</view>
			</view>
		</block>
  <!-- 注意事项: 不能使用 index 作为 key 的唯一标识
<list>
  <cell v-for="(item, index) in dataList" :key="item.recordId">
	<view class="d-flex"
		  style="height: 160rpx; width: 94%; margin-top: 10rpx; border: #888888 solid 1rpx; border-radius: 40rpx;">
	  <view class="d-flex flex-column j-center" style="width: 400rpx; margin-left: 5%;">
		<text>{{ getCashTypeDesc(item.recordType) }}</text>
		<text>{{ item.changeMoney }}</text>
		<text>{{ item.addDate }}</text>
	  </view>
	  <view class="d-flex a-center j-center" style="width: 400rpx;">
		{{ item.afterMoney }}
	  </view>
	</view>
  </cell>
</list> -->
  </view>
</template>

<script>
import https from '../../api/api.js'

export default {
	data() {
		return {
			dataList: []
		}
	},
	methods: {
		getCashTypeDesc(cashType) {
			// 变动类型 1-充值 2-提现 3-买入 4-卖出
			return cashType < 2 ? "充值" : cashType == 2 ? "提现" : cashType == 3 ? "下单" : "结单";
		},
		loadData(){
			//发起查询数据
			https.queryFlowingWater({}).then((res) => {
				if (res != null) {
					this.dataList = res.list;
					// 数据请求完成之后停止下拉刷新
					uni.stopPullDownRefresh();
				}
			})
		}
	},
	onShow() {
		document.title = '币安秒合约';
		this.loadData();
	},
	// 监听下拉刷新动作的执行方法，每次手动下拉刷新都会执行一次
	onPullDownRefresh() {
		this.loadData();
		uni.showToast({
			title: '刷新成功！',
			duration: 500,
		})
	},
	onLoad() {
		if (uni.getStorageSync('token') === null || uni.getStorageSync('token') === undefined || uni.getStorageSync('token') === '') {
			// 跳转页面
			uni.reLaunch({
				url: '../login/login'
			});
		}
	}
}
</script>

<style>
.d-flex {
  margin: 0 3% 0 3%;
}
</style>
