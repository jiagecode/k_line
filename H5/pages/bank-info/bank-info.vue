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
			<uni-easyinput type="number" :inputBorder="false" placeholder="卡号" :v-model="bankInfo.cardNo"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>持卡人：</text>
			<uni-easyinput type="number" :inputBorder="false" placeholder="持卡人" :v-model="bankInfo.cardOwnerName"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text @tap="nextStep">卡类型：</text>
			<picker @change="bindPickerChange" :value="index" :range="array">
				<view class="uni-input">{{array[index]}}</view>
			</picker>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>手机号：</text>
			<uni-easyinput type="number" :inputBorder="false" placeholder="手机号" :v-model="bankInfo.cardPhone"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>身份证：</text>
			<uni-easyinput type="idcard" :inputBorder="false" placeholder="身份证号" :v-model="bankInfo.cardOwnerNo"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>省份：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="省份" :v-model="bankInfo.province"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx;">
			<text>市名：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="市名" :v-model="bankInfo.city"/>
		</view>
		<view class="d-flex a-center" style="height: 110rpx; border-top: #c9c7bf solid 1rpx; border-bottom: #c9c7bf solid 1rpx;">
			<text>支行：</text>
			<uni-easyinput type="text" :inputBorder="false" placeholder="支行址" :v-model="bankInfo.subBranch"/>
		</view>
		<button style="margin-top: 80rpx; width: 45%; background-color: #5586d3; color: #FFFFFF;" @click="createBankInfo()">添加</button>
	</view>
  </view>
</template>

<script>
//import BIN from 'bankcardinfo'
export default {
  data() {
    return {
      index: 0,
      array: ['中国工商银行', '美国', '巴西', '日本'],
      cardNo: '',
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
  methods: {
    bindPickerChange: function (e) {
      console.log('picker发送选择改变，携带值为', e.target.value)
    },
    nextStep() {
      // promise 方式调用, 2.0.0 及以上版本支持 6212812318000075076
      console.log(this.cardNo);
      // console.log(this.bankInfo);
      // console.log(this.bankInfo.cardNo);
      // BIN.getBankBin(this.bankInfo.cardNo).then(function(data) {
      // 	console.log(data)
      // 	this.bankInfo.bankName = data.bankName;
      // }).catch(function(err) {
      // 	console.log(err)
      // })
    }
  },
  onShow() {
    document.title = '币安秒合约';
  },
  methods: {
	  createBankInfo(){
		  console.log("添加银行卡")
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
