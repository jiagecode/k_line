<template>
	<view>
		<!-- 行情列表 -->
		<view>
			<view class="d-flex" style="margin-top: 30rpx; height: 30rpx;">
				<view style="font-size: 23rpx; margin-left: 30rpx; width: 60rpx;"><image style="width: 30rpx; height: 30rpx;" src="/static/px.png"></image></view>
				<view style="font-size: 23rpx; width: 200rpx;">币种/市值</view>
				<view style="font-size: 23rpx; width: 290rpx;">价格</view>
				<view style="font-size: 23rpx; width: 170rpx;">24h涨幅榜</view>
			</view>
			<view style="margin-top: 45rpx;">
				<block v-for="(item, index) of marketsList" :key="index">
					<view class="d-flex" style="height: 115rpx;" @tap="toKline">
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
							<view class="num" style="font-size: 25rpx; color: #999999; margin-top: 5rpx; height: 27rpx;">≈￥{{item.current_price}}</view>
							<view style="height: 38rpx;"></view>
						</view>
						<!-- 涨幅榜 -->
						<view style="width: 170rpx;">
							<!-- align-items: center; 垂直居中  justify-content: center; 水平居中 -->
							<view class="d-flex a-center j-center" v-if="item.current_price / 32688 > 1" style="width: 143rpx; height: 62rpx; background-color: #00c68d; color: #FFFFFF;">+ 4.20%</view>
							<view class="d-flex a-center j-center" v-else style="width: 143rpx; height: 62rpx; background-color: #fd6d48; color: #FFFFFF; ">- 0.06%</view>
						</view>
					</view>
				</block>
						<p>{{60+30+200+290+170}}</p>
			</view>
		</view>
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
			toKline(){
				console.clear("toKline");
				uni.navigateTo({
					url: '../kline/kline'
				});
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
			
			uni.request({
			    url: 'https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false',
				method: 'get',
			    success: (res) => {
			        console.log(res.data);
			        this.marketsList = res.data;
			    }
			});
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
