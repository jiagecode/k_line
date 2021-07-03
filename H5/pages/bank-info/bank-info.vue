<template>
  <!-- 银行卡 -->
  <view>
    <view class="d-flex a-center">
      <text>银行卡号：</text>
      <uni-easyinput type="number" :inputBorder="false" placeholder="银行卡号" :v-model="bankInfo.cardNo"/>
    </view>
    <view class="d-flex a-center">
      <text @tap="nextStep">银行名称：</text>
      <!-- <uni-easyinput type="text" :inputBorder="false" placeholder="银行名称" :v-model="bankInfo.bankName" /> -->
      <picker @change="bindPickerChange" :value="index" :range="array">
        <view class="uni-input">{{ array[index] }}</view>
      </picker>
    </view>
    <view class="d-flex a-center">
      <text>开户支行：</text>
      <uni-easyinput type="text" :inputBorder="false" placeholder="支行地址" :v-model="bankInfo.subBranch"/>
    </view>
    <view class="d-flex a-center">
      <text>开户名：</text>
      <uni-easyinput type="text" :inputBorder="false" placeholder="持卡人姓名" :v-model="bankInfo.cardOwnerName"/>
    </view>
    <view class="d-flex a-center">
      <text>身份证：</text>
      <uni-easyinput type="idcard" :inputBorder="false" placeholder="身份证号" :v-model="bankInfo.cardOwnerNo"/>
    </view>
    <view class="d-flex a-center">
      <text>预留手机：</text>
      <uni-easyinput type="number" :inputBorder="false" placeholder="预留手机号" :v-model="bankInfo.cardPhone"/>
    </view>
    <view class="d-flex a-center">
      <text>省份：</text>
      <uni-easyinput type="text" :inputBorder="false" placeholder="省份" :v-model="bankInfo.province"/>
    </view>
    <view class="d-flex a-center">
      <text>市名：</text>
      <uni-easyinput type="text" :inputBorder="false" placeholder="市名" :v-model="bankInfo.city"/>
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
