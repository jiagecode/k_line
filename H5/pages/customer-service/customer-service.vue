<template>
	<view class="sv" style="height: 100%; padding: 0 3%;">
		<view style="height: 92%;">
			<view style="height: 2%;"></view>
			<text class="d-flex a-center j-center font-lg " style="color: #FFFFFF; height: 90rpx; background-color: #5586d3;">
				欢迎访问币安秒合约在线客服
			</text>
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-top: 3%;">
				<image style="width: 14%; height: 113rpx;" src="/static/zxkf.png" />
				<text style="width: 83.4%; margin-left: 2.6%; font-weight: bold;" space="emsp">特别提示：平台将不定期更换卡号，请仔细核对充值的银行卡号，如因客户转账银行错误导致无法充值平台概不负责。</text>
			</view>
		</view>
		<view class="d-flex a-center" style="height: 8%; margin-top: -3%;">
			<uni-easyinput type="text" :inputBorder="false" placeholder="请写下你的问题" :v-model="msg" />
			<button class="d-flex j-center a-center font-md" style="width: 20%; height: 100%; background-color: #09BB07; color: #FFFFFF;" @tap="send">发送</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				msg:""
			}
		},
		methods: {
			send(){
				console.log("abc" + this.msg);
			}
		},
		onLoad() {
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
