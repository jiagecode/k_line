<template>
  <!-- 银行卡 -->
  <view class="d-flex flex-column j-center a-center" style="padding-bottom: 50rpx;">
	  <!-- 文本说明 -->
	  <view class="font-lg" style="height: 80rpx; padding-top: 80rpx; font-weight: bold;">添加银行卡</view>
	  <view class="font-mdd" style="height: 130rpx;">请绑定持卡人本人的银行卡</view>
	  <!-- 添加信息 -->
	<view style="width: 84%;">
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>卡号：</text>
			<uni-easyinput type="number" :inputBorder="false" placeholder="卡号" v-model="bankInfo.cardNo" @input="bindPickerChange"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>持卡人：</text>
			<uni-easyinput type="number" :inputBorder="false" placeholder="持卡人" v-model="bankInfo.cardOwnerName"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>卡类型：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="持卡人" v-model="bankInfo.bankName"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>手机号：</text>
			<uni-easyinput type="number" :inputBorder="false" placeholder="手机号" v-model="bankInfo.cardPhone"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>身份证：</text>
			<uni-easyinput type="idcard" :inputBorder="false" placeholder="身份证号" v-model="bankInfo.cardOwnerNo"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>省份：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="省份" v-model="bankInfo.province"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>市名：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="市名" v-model="bankInfo.city"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx; border-bottom: #c9c7bf solid 1rpx;">
			<text>支行：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="支行址" v-model="bankInfo.subBranch"/>
		</view>
		<button style="margin-top: 50rpx; width: 45%; background-color: #5586d3; color: #FFFFFF;" @click="createBankInfo()">添加</button>
	</view>
  </view>
</template>
<script>
	import BIN from 'bankcardinfo'
	import https from '../../api/api.js'
	export default{
		data(){
			return{
				bankName:'',
				bankInfo: {
					bankName: "",
					province: "",
					city: "",
					subBranch: "",
					cardOwnerName: "",
					cardNo: "",
					cardOwnerNo: "",
					cardPhone: ""
				}
			}
		},
		methods:{
			// 6212812318000075076 
			bindPickerChange: function (e) {
				if(e.length > 14){
					var _this = this;
					BIN.getBankBin(e).then(function(data) {
						_this.bankInfo.bankName = data.bankName;
					}).catch(function(err) {
						console.log(err)
					})
				}
			},
			createBankInfo(){
				console.log("添加银行卡")
				console.log(this.bankInfo)
				
				// 发起登录请求
				https.bankCardInfoSave(this.bankInfo).then((res)=> {
					if(res != null){
						// 提示用户
						uni.showToast({
						    title: '添加成功！',
						    duration: 1000,
						})
						setTimeout(() => {
							// 跳转页面
							uni.redirectTo({
								url:'../withdrawal/withdrawal'
							})}, 
						900)
					}
				})
			}
		}
	}
</script>

<style>
text {
  margin-left: 25 rpx;
  width: 19%;
}

.a-center {
  height: 105 rpx;
  border-bottom: solid #ececec 5 rpx;
}
</style>