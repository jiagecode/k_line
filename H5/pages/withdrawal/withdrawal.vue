<template>
	<!-- 提现 -->
	<view style="margin: 25rpx 15rpx 0 15rpx;">
		<view class="d-flex a-center j-center" style="border: dashed #ececec 5rpx; border-radius: 25rpx; height: 200rpx;">
			<navigator url="../bank-info/bank-info" hover-class="none">
				<image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png" ></image>
				<view style="color: #888888;">添加银行卡</view>
			</navigator>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
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
