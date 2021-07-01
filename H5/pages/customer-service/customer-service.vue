<template>
	<scroll-view>
<!-- 		<view class="d-flex flex-column j-sb" style="height: 1000rpx; background-color: #0A98D5;">
			<view>1</view>
			<view class="">2</view>
		</view> -->
		<view class="d-flex flex-column j-sb" :style="'height:' + this.height + 'rpx;'">
			<view class="d-flex flex-column" style="margin: 2.5%;">
				<text class="d-flex a-center j-center font-lg " style="color: #FFFFFF; height: 90rpx; background-color: #5586d3;">
					欢迎访问币安秒合约在线客服
				</text>
				<!-- 客服 -->
				<view class="d-flex a-center" style="margin-top: 2.6%; border-bottom: solid #ececec 1rpx; margin-left: 2.6%;">
					<image style="width: 14%; height: 113rpx;" src="/static/zxkf.png" />
					<text style="width: 83.4%; margin-left: 2.6%; font-weight: bold;" space="emsp">特别提示：平台将不定期更换卡号，请仔细核对充值的银行卡号，如因客户转账银行错误导致无法充值平台概不负责。</text>
				</view>
			</view>
<!-- 		</view>
		<view> -->
			<view class="d-flex j-end a-center j-center"  style="margin: 0 2.5% 0 2.5%; height:90rpx; border: solid #ececec 1rpx;">
				<!-- <view class="d-flex"> -->
					<uni-easyinput type="text" :inputBorder="false" placeholder="请写下你的问题" :v-model="msg" />
					<button class="d-flex j-center a-center font-md" style="width: 20%; height: 100%; background-color: #09BB07; color: #FFFFFF;" @tap="send">发送</button>
				<!-- </view> -->
			</view>
		</view>
	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				msg:"",
				height:0
			}
		},
		methods: {
			send(){
				console.log("abc" + this.msg);
			}
		},
		onLoad() {
			this.height = uni.getSystemInfoSync().screenHeight;
			console.log("新幕高度：" + this.height);
			this.height += 400;
			console.log("旧幕高度：" + this.height);
			console.log("窗体高度：" + uni.getSystemInfoSync().windowHeight);
		},
		onShow() {
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
