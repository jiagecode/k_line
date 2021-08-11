<template>
	<!-- 交易记录 -->
	<view style="padding: 20rpx;">
		<block v-for="(item, index) in positionList" :key="item.positionId">
			<uni-row class="uni-row-bot" style="height: 120rpx;">
				<uni-col :span="8" style="text-align: left;">
					<view style="height: 40rpx; line-height:40rpx;">{{ item.skuName }}</view>
					<view style="height: 40rpx; line-height:40rpx;">{{ fixedPrice(item.beaginPrice)}}</view>
					<view style="height: 40rpx; line-height:40rpx; color: #999999;" class="font-sm">{{ item.addDate }} </view>
				</uni-col>
				<uni-col :span="8" style="text-align: center;">
					<view style="height: 40rpx; line-height:40rpx;">&nbsp;</view>
					<view style="height: 40rpx; line-height:40rpx;">
						{{ showUpOrDown(item.investType, item.investAmount)}}
					</view>
					<view style="height: 40rpx; line-height:40rpx;">&nbsp;</view>
				</uni-col>
				<uni-col :span="8" style="text-align: right;">
					<view class="font-md" v-show="item.winFlag == '1'" style="height: 40rpx; line-height:40rpx; color: red;">+ {{ item.incomeAmount }}</view>
					<view class="font-md" v-show="item.winFlag == '2'" style="height: 40rpx; line-height:40rpx; color: #09BB07;">- {{ item.investAmount }}</view>
					<view style="height: 40rpx; line-height:40rpx;">{{ fixedPrice(item.endPrice) }}</view>
					<view style="height: 40rpx; line-height:40rpx; color: #999999;" class="font-sm">{{ item.endDate }}</view>
				</uni-col>
			</uni-row>
			<view style="border-bottom: solid #ececec 2rpx; margin-bottom: 20rpx;"></view>
		</block>
	</view>

</template>

<script>
	import https from "../../api/api";
	import MathUtil from '@/utils/MathUtil.js'

	export default {
		data() {
			return {
				positionList: [],
				positionStatus: 2 //持有状态为已卖出
			}
		},
		onLoad() {
			var token = uni.getStorageSync('token');
			// 非法访问，请重新登录
			if (token === null || token === undefined || token === '') {
				// 跳转页面
				uni.reLaunch({
					url: '../login/login'
				});
			}
		},
		methods: {
			// 价格处理
			fixedPrice(price){
				return MathUtil.toFixed(price, 4);
			},
			showUpOrDown(investType, money) {
				let desc = investType === 1 ? "买涨" : "买跌";
				return desc + " (￥" + money + ")";
			},
			loadData() {
				var data = {
					"positionStatus": this.positionStatus
				};
				//发起查询数据
				https.myPosition(data).then((res) => {
					if (res != null) {
						this.positionList = res.list;
						// 数据请求完成之后停止下拉刷新
						uni.stopPullDownRefresh();
						console.log("交易历史数据:", res.list);
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
		}
	}
</script>

<style>

</style>
