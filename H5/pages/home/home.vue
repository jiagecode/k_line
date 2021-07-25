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
				<block v-for="(item, index) of marketsList" :key="index">
					<view class="d-flex" style="height: 115rpx;" @tap="toKline(item)">
						<view style="margin-top: -4rpx; margin-left: 30rpx; width: 60rpx;">{{index + 1}}</view>
						<!-- 市值 -->
						<view style="width: 200rpx; ">
							<view class="d-flex" style="height: 40rpx;">
								<image style="width: 40rpx; height: 40rpx;" :src="item.image" ></image>
								<view style="margin: -12rpx 0 0 10rpx; font-size: 35rpx;  font-weight: bold;">{{item.symbol}}</view>
							</view>
							<view class="num" style="margin-top: 5rpx; height: 27rpx; font-size: 25rpx; color: #999999;">￥{{item.market_cap}}</view>
						</view>
						<!-- 实时价格 -->
						<view style="width: 290rpx;">
							<view class="d-flex" style="height: 40rpx; font-size: 33rpx; font-weight: bold;">
								<view style="height: 40rpx; margin-top: -8rpx;">${{item.current_price}}</view>
							</view>
							<view class="num" style="font-size: 25rpx; color: #999999; margin-top: 5rpx; height: 27rpx;">≈￥{{item.current_price_cny}}</view>
							<view style="height: 38rpx;"></view>
						</view>
						<!-- 涨幅榜 -->
						<view style="width: 170rpx;">
							<!-- align-items: center; 垂直居中  justify-content: center; 水平居中 -->
							<view class="d-flex a-center j-center" v-if="item.price_change_percentage_24h > 0" 
							style="width: 143rpx; height: 62rpx; background-color: #00c68d; color: #FFFFFF;">+{{item.price_change_percentage_24h !== null ? item.price_change_percentage_24h.toFixed(2) : 0}}%</view>
							<view class="d-flex a-center j-center" v-else 
							style="width: 143rpx; height: 62rpx; background-color: #fd6d48; color: #FFFFFF; ">{{item.price_change_percentage_24h !== null ? item.price_change_percentage_24h.toFixed(2) : 0}}%</view>
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
				marketsList:[]
			}
		},
		methods: {
		   loadData() {
				uni.request({
					url: 'https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1&sparkline=false',
					method: 'get',
					success: (res) => {
						// 数据请求完成之后停止下拉刷新
						uni.stopPullDownRefresh();
						this.marketsList = this.processingdData(res.data);
					}
				})
            },
			toKline(item){
				uni.$emit("ChangeSymbol",item);
				uni.switchTab({
				    url: '../kline/TradingView'
				});
			},
			// 处理数据
			processingdData(data){
				for (const d of data) {
					// 美元转换人名币
					let market_cap = d.market_cap * 6.47;
					let current_price = d.current_price * 6.47;
					
					// 转换亿元
					d.market_cap = (this.numFormat(d.market_cap / 100000000)) + "亿";
					// 实时人民币价格
					d.current_price_cny = (this.numFormat(d.current_price));
				}
				return data;
			},
			// js 金额用，隔开
			numFormat(num) {
				return (num.toString().indexOf('.') !== -1) ? num.toLocaleString('CNY', {maximumFractionDigits : 2}) : num.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
			},
			async getOldPrice(id){
				return await new Promise((resolve, reject) => {
					uni.request({
					    // url: 'https://api.coingecko.com/api/v3/coins/' + id + '/market_chart?vs_currency=usd&days=1&interval=daily',
					    url: 'https://api.coingecko.com/api/v3/coins/' + id + '/ohlc?vs_currency=usd&days=1',
						method: 'get',
					    success: (res) => {
							console.log(res);
							let c = res.data[0][3];
							resolve(c);
					    }
					});
				})
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
