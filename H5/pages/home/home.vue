<template>
	<view>
		<view>
			<block v-for="(item,index) in marketsList" :key="idnex">
				<view class="flex-1" style="display: flex; flex-direction: row;">
					<!-- 序号 -->
					<view>{{index + 1}}</view>
					<!-- 币种/市值 -->
					<view style="background-color: #eeeeee;">
						<image style="width: 36rpx; height: 35rpx;" :src="item.image" >{{item.symbol}}</image>
						<view>￥{{item.market_cap}}美元</view>
					</view>
					<!-- 价格 -->
					<view style="margin-left: 10upx; background-color: #BEBEBE;">
						<view>{{item.current_price}}</view>
						<view>≈￥{{item.current_price}}</view>
					</view>
					<!-- 24h涨幅 -->
					<view style="margin-left: 10upx; width: 120upx; height: 100%; align-items: stretch; background-color: #28A745;">{{item.current_price / 32688}}%</view>
				</view>
				<view style="margin-top: 60upx;"></view>
			</block>
		</view>
<!-- 		<view class="d-flex">
			<view class="flex-1"></view>
			<view class="flex-1 d-flex j-center a-center flex-column line-h">
				<view class="iconf">BTC</view>
				￥40,959.56亿
			</view>
			<view class="flex-1 d-flex j-center a-center flex-column line-h">
				<view class="iconf">BTC</view>
				￥40,959.56亿
				</view>
			<view class="flex: 2.5"></view>
		</view> -->
		<!-- marketsList -->
<!-- 		<view v-if="marketsList.length > 0" class="bg-color-white info-list">
			<view class="info-item" v-for="(item, index) of marketsList" :key="index">
                <view class="image-content">
					<view class="number">{{index + 1}}</view>
					<view class="number">{{item.symbol}}</view>
					<view class="number">￥{{item.market_cap}}美元</view>
					<view class="number">≈￥{{item.current_price}}</view>
					<view class="number">{{item.current_price / 32688}}%</view>
					涨跌幅 = 涨跌值 / 昨收盘 * 100%
                    <image style="width: 36rpx; height: 35rpx; background-color: #eeeeee;" :src="item.image" >{{item.name}}</image>
                </view>
			</view>
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				marketsList:[]
			}
		},
		methods: {
			
		},
		onLoad() {
			console.log('页面加载完成');
			uni.request({
			    url: 'https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=2&page=1&sparkline=false',
				method: 'get',
			    success: (res) => {
			        console.log(res.data);
			        this.marketsList = res.data;
			    }
			});
		}
	}
</script>

<style>

</style>
