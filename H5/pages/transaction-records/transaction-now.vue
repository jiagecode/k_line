<template>
  <!-- 持仓明细 -->
  <view style="margin-left: 20rpx;">
    <block v-for="(item, index) in positionList" :key="item.positionId">
      <view class="d-flex a-center" style="height: 180rpx; border-bottom: solid #ececec 2rpx;">
        <view style="width: 300rpx;">
          <view>{{ item.skuName }}</view>
          <view>{{ item.beaginPrice }}-{{ item.endPrice }}</view>
          <view style="font-size: 25rpx; color: #999999;">{{ item.addDate }}</view>
        </view>
        <view style="width: 285rpx;">
          {{ showUpOrDown(item.investType, item.investAmount) }}
        </view>
        <view style="width: 300rpx;">
          <view v-show="item.investType == '1'" style="color: red;">+ {{ item.incomeAmount }}</view>
          <view v-show="item.investType == '2'" style="color: #09BB07;">- {{ item.incomeAmount }}</view>
          <view> 1</view>
		  <view style="font-size: 25rpx; color: #999999;">{{ item.addDate }}</view>
        </view>
      </view>
    </block>
  </view>
</template>

<script>
import https from "../../api/api";

export default {
  data() {
    return {
      positionList: [],
      positionStatus: 1 //持有状态为 持有中
    }
  },
  onLoad() {
  },
  methods: {
    showUpOrDown(investType, money) {
      let desc = investType === 1 ? "买涨" : "买跌";
      return desc + " (￥" + money + ")";
    },
  },
  mounted: async function () {
    var token = uni.getStorageSync('token');
    // 非法访问，请重新登录
    if (token === null || token === undefined || token === '') {
      // 跳转页面
      uni.reLaunch({
        url: '../login/login'
      });
    }
    var data = {"positionStatus": this.positionStatus};
    //发起查询数据
    https.myPosition(data).then((res) => {
      if (res != null) {
        this.positionList = res.list;
        console.log("交易历史数据:" + res.list);
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
