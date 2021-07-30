<template>
	<!-- 持仓明细 -->
	<view style="padding: 20rpx;">
		<block v-for="(item, index) in positionList" :key="item.positionId">
			<uni-row class="uni-row-bot" style="height: 120rpx;">
				<uni-col :span="10">
					<view style="height: 60rpx; line-height:60rpx;">{{ item.skuName }}</view>
					<view style="height: 60rpx; line-height:60rpx;">{{item.beaginPrice}}</view>
				</uni-col>
				<uni-col :span="14">
					<view style="height: 60rpx; text-align: right;">{{ showUpOrDown(item.investType, item.investAmount)}}</view>
					<view style="height: 60rpx; line-height:60rpx; font-size:25rpx; color: #999999; text-align: right;">{{ item.addDate }}</view>
				</uni-col>
			</uni-row>
			<uni-row class="uni-row-bot" style="height: 30rpx; border-bottom: solid #ececec 2rpx;">
				<uni-col :span="24">
					<ai-progress style="float: left;height: 30rpx; width: 100%;" :noData="true"
						:percentage="percentage" inBgColor="#5586d3" bgColor="#ff5252">
					</ai-progress>
				</uni-col>
			</uni-row>
		</block>
	</view>
</template>

<script>
	import https from "../../api/api";
	import moment from 'moment'
	import MathUtil from '@/utils/MathUtil.js'

	export default {
		data() {
			return {
				// 当前进度
				percentage: 0,
				positionList: [],
				positionStatus: 1 //持有状态为 持有中
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

			// 开始更新进度
			this.$nextTick(() => {
				this.autoPercentage();
			});
		},
		methods: {
			autoPercentage() {
				var that = this;
				var interval = setInterval(function() {
					// 列表没有数据就不处理
					if (that.positionList.length > 0) {
						// 只处理第一条 
						var positionData = that.positionList[0];
						// for (var i=0;i<that.positionList.length;i++) { 
						var startDate = positionData.beginDate;
						var endDate = positionData.endDate;
						if (startDate == null || startDate == "" || typeof startDate == "undefined") {
							return;
						}
						if (endDate == null || endDate == "" || typeof endDate == "undefined") {
							return;
						}
						var startTimeStamp = moment(startDate).format('X');
						var endTimeStamp = moment(endDate).format('X');
						var nowTimeStamp = moment().format('X');
						// 如果当前时间大于end 或者 当前时间小于start 不处理
						if (nowTimeStamp > endTimeStamp || nowTimeStamp < startTimeStamp) {
							return;
						}
						// 总时长
						var totalTime = MathUtil.sub(endTimeStamp, startTimeStamp);
						// 已经过时长
						var usedeTime = MathUtil.sub(nowTimeStamp, startTimeStamp);
						// 计算百分比
						var percent = MathUtil.mul(MathUtil.toFixed(MathUtil.div(usedeTime, totalTime), 2), 100);
						// 设置数据
						that.percentage = percent;
						// console.log(percent, that.percentage);

						// 100% 时 刷新
						if (percent >= 100) {
							that.positionList = [];
							uni.navigateBack();
							console.log("刷新");
						}
						// }
					}
				}, 1000);
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
				});
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
