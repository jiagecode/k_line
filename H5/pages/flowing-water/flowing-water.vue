<template>
  <view>
    <!--	<view class="d-flex" style="height: 160rpx; width: 94%; margin-top: 10rpx; border: #888888 solid 1rpx; border-radius: 40rpx;">
        <view class="d-flex flex-column j-center" style="width: 400rpx; margin-left: 5%;">
          <text>提现</text>
          <text>1.00</text>
          <text>2021-06-30 12:50:00</text>
        </view>
        <view class="d-flex a-center j-center" style="width: 400rpx;">
          13800
        </view>
      </view> -->
    <list>
      <!-- 注意事项: 不能使用 index 作为 key 的唯一标识 -->
      <cell v-for="(item, index) in dataList" :key="item.recordId">
        <view class="d-flex"
              style="height: 160rpx; width: 94%; margin-top: 10rpx; border: #888888 solid 1rpx; border-radius: 40rpx;">
          <view class="d-flex flex-column j-center" style="width: 400rpx; margin-left: 5%;">
            <text>{{ getCashTypeDesc(item.recordType) }}</text>
            <text>{{ item.changeMoney }}</text>
            <text>{{ item.addDate }}</text>
          </view>
          <view class="d-flex a-center j-center" style="width: 400rpx;">
            {{ item.afterMoney }}
          </view>
        </view>
      </cell>
    </list>
  </view>
</template>

<script>
import https from '../../api/api.js'

export default {
  data() {
    return {
      dataList: []
    }
  },
  methods: {
    getCashTypeDesc(cashType) {
      // 变动类型 1-充值 2-提现 3-买入 4-卖出
      return cashType < 2 ? "充值" : cashType == 2 ? "提现" : cashType == 3 ? "下单" : "结单";
    },

  },
  mounted: async function () {
    if (uni.getStorageSync('token') === null || uni.getStorageSync('token') === undefined || uni.getStorageSync('token') === '') {
      // 跳转页面
      uni.reLaunch({
        url: '../login/login'
      });
    }
    // uni.setStorageSync('token', "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjMiLCJleHAiOjE2MjUyODg2MjV9.yl68t0BE9wW-J4CZTj3h9VHDNLve_UpIX01OBsfmjSY");
    var data = {};
    //发起查询数据
    https.queryFlowingWater(data).then((res) => {
      if (res != null) {
        this.dataList = res.list;
        // console.log("流水数据:"+res.list);
      }
    })
  },
  onShow() {
    document.title = '币安秒合约';
  },
}
</script>

<style>
.d-flex {
  margin: 0 3% 0 3%;
}
</style>
