<template>
	<view>
		<view style="height: 290rpx;">
			<view class="d-flex a-center j-center" style="height: 205rpx;">
				<image style="width: 125rpx; height: 125rpx;" src="/static/photo.png" ></image>
			</view>
			<view class="d-flex j-center" style="margin-top: -10rpx;">用户名</view>
			<view class="d-flex j-center" style="color: #5586d3;">￥ 账户金额：10000</view>
		</view>
		<view class="d-flex" style="margin-top: 45rpx; height: 160rpx; border-bottom: solid #ececec 1rpx; border-top: solid #ececec 1rpx;">
			<!-- 充值 -->
			<view class="d-flex a-center j-center" style="width: 25%; border-right: solid #ececec 1rpx;" @click="cz()">
				<view>
					<image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/cz.png" >充值</image>
				</view>
			</view>
			<!-- 提现 -->
			<view class="d-flex a-center j-center" style="width: 25%; border-right: solid #ececec 1rpx;" @click="tx()">
				<view>
					<image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/tx.png" >提现</image>
				</view>
			</view>
			<!-- 资金流水 -->
			<view class="d-flex a-center j-center" style="width: 25%; border-right: solid #ececec 1rpx;" @click="ls()">
				<view>
					<image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/ls.png" >流水</image>
				</view>
			</view>
			<!-- 交易记录 -->
			<view class="d-flex a-center j-center" style="width: 25%;" @click="jy()">
				<view>
					<image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/jy.png" >交易</image>
				</view>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/kf.png" />
				<text style="margin-left: 20rpx;">客服</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/hz.png" />
				<text style="margin-left: 20rpx;">商务合作</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/down.png" />
				<text style="margin-left: 20rpx;">下载App</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/yq.png" />
				<text style="margin-left: 20rpx;">邀请好友</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/gy.png" />
				<text style="margin-left: 20rpx;">关于币安秒合约</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/yj.png" />
				<text style="margin-left: 20rpx;">意见反馈</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 1rpx;" @click="kf()">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/wm.png" />
				<text style="margin-left: 20rpx;">联系我们</text>
			</view>
		</view>
		<view style="border-bottom: solid #ececec 2rpx;" @click="out()">
			<!-- 退出 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 50rpx; height: 50rpx;" src="/static/tc.png" />
				<text style="margin-left: 20rpx;">退出</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello'
			}
		},
		onLoad() {

		},
		methods: {
			cz(){
				console.log("跳转充值页面");
				uni.navigateTo({
				    url: '../recharge/recharge'
				});
			},
			tx(){
				console.log("跳转提现页面");
				uni.navigateTo({
				    url: '../withdrawal/withdrawal'
				});
			},
			ls(){
				console.log("跳转流水页面");
				uni.navigateTo({
				    url: '../flowing-water/flowing-water'
				});
			},
			jy(){
				console.log("跳转交易页面");
				uni.navigateTo({
				    url: '../transaction-records/transaction-records'
				});
			},
			kf(){
				console.log("跳转客服页面");
				uni.navigateTo({
				    url: '../customer-service/customer-service'
				});
			},
			out(){
				console.log("跳转退出页面");
				// 销毁token
				uni.navigateTo({
				    url: '../login/login'
				});
			},
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
