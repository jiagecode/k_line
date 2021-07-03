<template>
  <!-- 提现 -->
  <view style="margin: 25rpx 15rpx 0 15rpx;">
    <view class="d-flex a-center j-center" style="border: dashed #ececec 5rpx; border-radius: 25rpx; height: 200rpx;">
      <block v-for="(item, index) in cardList" :key="item.bankCardId">
        <view class="d-flex flex-column j-center" style="width: 400rpx; margin-left: 5%;">
          <text>开户行：{{ item.bankName }}</text>
          <text>持卡人：{{ item.cardOwnerName }}</text>
          <text>卡号：{{ item.cardOwnerNo }}</text>
          <text>时间：{{ item.addDate }}</text>
        </view>
      </block>
    </view>

    <view class="d-flex a-center j-center" style="border: dashed #ececec 5rpx; border-radius: 25rpx; height: 200rpx;">
      <navigator url="../bank-info/bank-info" hover-class="none">
        <image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png"></image>
        <view style="color: #888888;">添加银行卡</view>
      </navigator>
    </view>
    <view class="d-flex a-center j-center" style="border: dashed #ececec 5rpx; border-radius: 25rpx; height: 200rpx;">
      <navigator url="../cashoutin/cashOutIn" hover-class="none">
        <image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png"></image>
        <view style="color: #888888;">我的提现记录</view>
      </navigator>
    </view>
    <view class="d-flex a-center j-center" style="border: dashed #ececec 5rpx; border-radius: 25rpx; height: 200rpx;">
      <navigator url="../transaction-records/transaction-now" hover-class="none">
        <image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png"></image>
        <view style="color: #888888;">持仓明细</view>
      </navigator>
    </view>
  </view>
</template>

<script>
import https from "../../api/api";

export default {
  data() {
    return {
      cardList: []
    }
  },
  methods: {},
  mounted: async function () {
    var token = uni.getStorageSync('token');
    // 非法访问，请重新登录
    if (token === null || token === undefined || token === '') {
      // 跳转页面
      uni.reLaunch({
        url: '../login/login'
      });
    }
    var data = {};
    //发起查询数据
    https.myCardList(data).then((res) => {
      if (res != null) {
        this.cardList = res.list;
        console.log("银行卡数据:" + res.list);
      }
    })
  },
  onShow() {
    document.title = '币安秒合约';
  }
}
</script>

<style>

</style>
