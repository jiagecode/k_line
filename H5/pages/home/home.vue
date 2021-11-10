<template>
	<view>
		<!-- 行情列表 -->
		<scroll-view class="sv">
			<view class="d-flex" style="margin-top: 30rpx; height: 30rpx;">
				<view style="font-size: 23rpx; margin-left: 30rpx; width: 60rpx;"><image style="width: 40rpx; height: 40rpx;" src="/static/px.png"></image></view>
				<view style="font-size: 23rpx; width: 200rpx;">币种/市值</view>
				<view style="font-size: 23rpx; width: 290rpx;">价格</view>
				<view style="font-size: 23rpx; width: 170rpx;">24h涨幅榜</view>
			</view>
			<view style="margin-top: 45rpx;">
				<block v-for="(item, index) of marketsListTwo" :key="index">
					<view v-if="item.DISPLAY !== undefined" class="d-flex" style="height: 115rpx;" @tap="toKline(item)">
						<view style="margin-top: -4rpx; margin-left: 30rpx; width: 60rpx;">{{index + 1}}</view>
						<!-- 市值 -->
						<view style="width: 200rpx; ">
							<view class="d-flex" style="height: 40rpx;">
								<image style="width: 40rpx; height: 40rpx;" :src="'https://www.cryptocompare.com/' + item.DISPLAY.USD.IMAGEURL"/>
								<view style="margin: -12rpx 0 0 10rpx; font-size: 35rpx;  font-weight: bold;">{{item.CoinInfo.Name}}</view>
							</view>
							<view class="num" style="margin-top: 5rpx; height: 27rpx; font-size: 25rpx; color: #999999;">{{item.DISPLAY.USD.MKTCAP}}</view>
						</view>
						<!-- 实时价格 -->
						<view style="width: 290rpx;">
							<view class="d-flex" style="height: 40rpx; font-size: 33rpx; font-weight: bold;">
								<view style="height: 40rpx; margin-top: -8rpx;">{{item.DISPLAY.USD.PRICE}}</view>
							</view>
							<!-- 现价人民币 -->
							<view class="num" style="font-size: 25rpx; color: #999999; margin-top: 5rpx; height: 27rpx;">≈￥{{processingdDatajTwo(item.DISPLAY.USD.PRICE)}}</view>
							<view style="height: 38rpx;"></view>
						</view>
						<!-- 涨幅榜 -->
						<view style="width: 170rpx;">
							<view class="d-flex a-center j-center" v-if="item.DISPLAY.USD.CHANGEPCT24HOUR > 0" 
							style="width: 143rpx; height: 62rpx; background-color: #00c68d; color: #FFFFFF;">+{{item.DISPLAY.USD.CHANGEPCT24HOUR}}%</view>
							<view class="d-flex a-center j-center" v-else 
							style="width: 143rpx; height: 62rpx; background-color: #fd6d48; color: #FFFFFF; ">{{item.DISPLAY.USD.CHANGEPCT24HOUR}}%</view>
						</view>
					</view>
				</block>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import https from '../../api/api.js'
	export default {
		data() {
			return {
				marketsListTwo:[]
			}
		},
		methods: {
		   loadData() {
			   uni.request({
			   	url: https.getCryptocompareApiUrl(),
			   	method: 'get',
			   	success: (res) => {
					if(res.data.Message === 'Success'){
						
						// 数据请求完成之后停止下拉刷新
						uni.stopPullDownRefresh();
						this.marketsListTwo = res.data.Data;
					}
			   	}
			   })
            },
			toKline(item){
				console.log(item);
				uni.$emit("ChangeSymbol", item);
				uni.switchTab({
				    url: '../kline/TradingView'
				});
			},
			// 处理单个实时美元数据转人名币
			processingdDatajTwo(data){
				let a = data.replace('$ ', '');
				a = a.replace(',', '');
				return this.numFormat(a * 6.47);
			},
			// js 金额用，隔开
			numFormat(num) {
				return (num.toString().indexOf('.') !== -1) ? num.toLocaleString('CNY', {maximumFractionDigits : 2}) : num.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
			}
		},
		onLoad() {
			// 非法访问，请重新登录
			if(uni.getStorageSync('token') === null || uni.getStorageSync('token') === undefined || uni.getStorageSync('token') === ''){
				// 跳转页面
				uni.reLaunch({
				    url: '../login/login'
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
