<template>
	<!-- 充值 -->
	<view>
		<!-- 通道一 -->
		<view style="border-bottom: solid #ececec 1rpx;">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 30rpx; height: 30rpx;" src="/static/tips.png" />
				<text style="margin-left: 20rpx;">通道一：联系<text style="color: #5586d3;" @click="kf()">在线客服</text>转账充值。</text>
			</view>
		</view>
		<!-- 通道二 -->
		<view style="border-bottom: solid #ececec 1rpx;">
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-left: 40rpx; height: 140rpx;">
				<image style="width: 30rpx; height: 30rpx;" src="/static/tips.png" />
				<text style="margin-left: 20rpx;">通道二：OTC充值送，转币后截图联系<text style="color: #5586d3;" @click="kf()">在线客服</text>。</text>
			</view>
		</view>
		<view class="d-flex j-center" style="margin-top: 30rpx;">
			<image style="width: 615rpx; height: 690rpx;" src="/static/sk.jpg" />
		</view>
		<text class="d-flex j-center">Ox8679a51b7b830241616ecb61td3557c3a4be</text>
		<button class="d-flex j-center" style="margin-top: 20rpx; width: 35%; background-color: #5586d3; color: #FFFFFF;" @click="copy()">点击复制</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			kf(){
				console.log("跳转客服页面");
			},
			copy(){
				uni.showToast({
				    title: '复制成功！',
				    duration: 500
				});
			}
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
