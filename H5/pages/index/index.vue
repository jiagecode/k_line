<template>
  <view class="sv">
    <view style="height: 290rpx;">
      <view class="d-flex a-center j-center" style="height: 205rpx;">
        <image style="width: 125rpx; height: 125rpx; border-radius: 62.5rpx;" :src=showUserHeadImg(userData.userHeadImg)></image>
      </view>
      <view class="d-flex j-center" style="margin-top: -10rpx;">{{ userData.userRealName }}</view>
      <view class="d-flex j-center" style="color: #5586d3;">￥ 账户金额：{{ userData.userMoney }}</view>
    </view>
    <view class="d-flex"
          style="margin-top: 45rpx; height: 160rpx; border-bottom: solid #d8d8d8 15rpx; border-top: solid #d8d8d8 15rpx;">
      <!-- 充值 -->
      <view class="d-flex a-center j-center" style="width: 25%;" @click="cz()">
        <view>
          <image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/cz.png">充值</image>
        </view>
      </view>
      <!-- 提现 -->
      <view class="d-flex a-center j-center" style="width: 25%;" @click="tx()">
        <view>
          <image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/tx.png">提现</image>
        </view>
      </view>
      <!-- 资金流水 -->
      <view class="d-flex a-center j-center" style="width: 25%;" @click="ls()">
        <view>
          <image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/ls.png">流水</image>
        </view>
      </view>
      <!-- 持仓记录 -->
      <view class="d-flex a-center j-center" style="width: 25%;" @click="cc()">
        <view>
          <image class="d-flex a-center j-center" style="width: 50rpx; height: 50rpx;" src="/static/cc.png">持仓</image>
        </view>
      </view>
    </view>
	
	<!-- 交易记录 -->
	<navigator url="../transaction-records/transaction-records" hover-class="none" style="border-bottom: solid #d8d8d8 1rpx;">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
			<image style="width: 50rpx; height: 50rpx;" src="/static/jy.png"/>
			<text style="margin-left: 20rpx;">交易记录</text>
		</view>
	</navigator>
	
	<!-- 提现记录 -->
	<navigator url="../cashoutin/cashOutIn" hover-class="none" style="border-bottom: solid #d8d8d8 1rpx;">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
			<image style="width: 50rpx; height: 50rpx;" src="/static/tx.png"/>
			<text style="margin-left: 20rpx;">提现记录</text>
		</view>
	</navigator>
	
	<!-- 客服 -->
	<navigator url="../customer-service/customer-service" hover-class="none" style="border-bottom: solid #d8d8d8 1rpx;">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
			<image style="width: 50rpx; height: 50rpx;" src="/static/kf.png"/>
			<text style="margin-left: 20rpx;">客服</text>
		</view>
	</navigator>
	
	<!-- 商务合作 -->
	<navigator url="../cooperation/cooperation" hover-class="none" style="border-bottom: solid #d8d8d8 1rpx;">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
			<image style="width: 50rpx; height: 50rpx;" src="/static/hz.png"/>
			<text style="margin-left: 20rpx;">商务合作</text>
		</view>
	</navigator>
      <!-- 下载App 
    <view style="border-bottom: solid #d8d8d8 1rpx;" @click="kf()">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
        <image style="width: 50rpx; height: 50rpx;" src="/static/down.png"/>
        <text style="margin-left: 20rpx;">下载App</text>
      </view>
    </view>-->
	<!-- 关于币安秒合约 
    <view style="border-bottom: solid #d8d8d8 1rpx;" @click="kf()">
      
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
        <image style="width: 50rpx; height: 50rpx;" src="/static/gy.png"/>
        <text style="margin-left: 20rpx;">关于币安秒合约</text>
      </view>
    </view>-->
      <!-- 客服
    <view style="border-bottom: solid #d8d8d8 1rpx;" @click="kf()">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
        <image style="width: 50rpx; height: 50rpx;" src="/static/yj.png"/>
        <text style="margin-left: 20rpx;">意见反馈</text>
      </view>
    </view> -->
      <!-- 联系我们
    <view style="border-bottom: solid #d8d8d8 1rpx;" @click="kf()">
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
        <image style="width: 50rpx; height: 50rpx;" src="/static/wm.png"/>
        <text style="margin-left: 20rpx;">联系我们</text>
      </view>
    </view> -->
    <view style="border-bottom: solid #d8d8d8 2rpx;" @click="out()">
      <!-- 退出 -->
      <view class="d-flex a-center" style="margin-left: 40rpx; height: 100rpx;">
        <image style="width: 50rpx; height: 50rpx;" src="/static/tc.png"/>
        <text style="margin-left: 20rpx;">退出</text>
      </view>
    </view>
  </view>
</template>

<script>
//import https from "../../api/api";

export default {
  data() {
    return {
      title: 'Hello',
      userData: {},
      defaultHedImg: "/static/photo.png"
    }
  },
  onLoad() {
    var token = uni.getStorageSync('token');
    var uData = uni.getStorageSync('userInfo');
    // 非法访问，请重新登录
    if (token === null || token === undefined || token === '' || uData === null) {
      // 跳转页面
      uni.reLaunch({
        url: '../login/login'
      });
    }
    this.userData = uData;
    //console.log("缓存的用户信息:"+uData);
  },
  methods: {
    showUserHeadImg(headImg) {
      if (headImg === null || headImg === '' || headImg === undefined) {
        return this.defaultHedImg;
      }
      return headImg;
    },
    cz() {
      console.log("跳转充值页面");
      uni.navigateTo({
        url: '../recharge/recharge'
      });
    },
    tx() {
      console.log("跳转提现页面");
      uni.navigateTo({
        url: '../withdrawal/withdrawal'
      });
    },
    ls() {
      console.log("跳转流水页面");
      uni.navigateTo({
        url: '../flowing-water/flowing-water'
      });
    },
    cc() {
      console.log("跳转持仓记录页面");
      uni.navigateTo({
        url: '../transaction-records/transaction-now'
      });
    },
    out() {

      // 销毁token
      uni.setStorageSync('token', null)
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
  height: 200 rpx;
  width: 200 rpx;
  margin-top: 200 rpx;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50 rpx;
}

.text-area {
  display: flex;
  justify-content: center;
}

.title {
  font-size: 36 rpx;
  color: #8f8f94;
}
</style>
