<template>
  <!-- 提现 -->
  <view class="bc" style="padding: 25rpx 4.6% 0 4.6%;">
		  
	<!-- 优先展示绑定的银行卡 -->
	<div>
		<block v-for="(item, index) in cardList" :key="item.bankCardId">
			<view class="d-flex a-center j-center" style="background-color: #159682; height: 240rpx; border-radius: 15rpx;">
				<view style="height: 80%; width: 90.6%;">
				  <view class="d-flex">
						<image style="background-color: #FFFFFF; width: 70rpx; height: 70rpx; border-radius: 50rpx;" src="/static/yh.png" @click="xy" />
						<view class="d-flex flex-column j-center a-cent" style="margin-left: 20rpx;">
							<view class="font-md" style="color: #FFFFFF;">{{ item.bankName }}</view>
							<view class="font-sm" style="color: #eddcd6;">储蓄卡</view>
					  </view>
				  </view>
				  <view class="font-big" style="margin: 0 0 0 14%; color: #FFFFFF;">{{ item.cardNo }}</view>
				</view>
			</view>
			<!-- 银行卡间隙 -->
			<view style="margin-top: 10rpx;"></view>
		</block>
	</div>
	<!-- 添加银行卡 -->
    <view class="d-flex a-center j-center" style="background-color: #eeeeee; border: dashed #ececec 5rpx; border-radius: 15rpx; height: 150rpx;">
      <navigator url="../bank-info/bank-info" hover-class="none">
        <image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png"></image>
        <view style="color: #888888;">添加银行卡</view>
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
