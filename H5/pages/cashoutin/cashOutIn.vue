<template>
  <!-- 交易记录 -->
  <view style="margin-left: 20rpx;">
    <block v-for="(item, index) in cashDetailList" :key="item.cashId">
      <view class="d-flex a-center" style="height: 200rpx; border-bottom: solid #ececec 2rpx;">
        <view style="width: 285rpx;">
          {{ showOutInDesc(item.cashType, item.cashMoney) }}
          <view style="width: 285rpx;">
            状态:{{ showOutInStatus(item.checkStatus) }}
          </view>
          <view style="width: 400rpx;">时间:{{ item.addDate }}</view>
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
      cashDetailList: []
    }
  },
  onLoad() {
  },
  methods: {
    showOutInDesc(cashType, money) {
      let desc = cashType === 1 ? "提现" : "充值";
      return desc + "金额：" + money;
    },
    showOutInStatus(cashStatus) {
      //审核状态 0-无需审核 1-待审核 2-审核中 3-审核通过 4-审核拒绝
      if (cashStatus === 1) {
        return "待审核";
      } else if (cashStatus === 2) {
        return "审核中";
      } else if (cashStatus === 4) {
        return "审核拒绝";
      }
      return "已通过";
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
    var data = {};
    //发起查询数据
    https.myOutIn(data).then((res) => {
      if (res != null) {
        this.cashDetailList = res.list;
        console.log("提现充值数据:" + res.list);
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
