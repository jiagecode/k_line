<template>
  <!-- 交易记录 -->
  <view style="padding: 0 4% 0 4%;">		
    <block v-for="(item, index) in positionList" :key="item.positionId">
      <view class="d-flex a-center" style="height: 200rpx; border-bottom: solid #ececec 2rpx;">
        <view style="width: 300rpx;">
          <view class="font-mdd">{{ item.skuName }}</view>
          <view class="font-md">{{ item.beaginPrice }}-{{ item.endPrice }}</view>
          <view class="font-sm" style="color: #999999;">{{ item.addDate }}</view>
        </view>
        <view class="font-md" style="width: 285rpx;">
          {{ showUpOrDown(item.investType, item.investAmount) }}
        </view>
        <view style="width: 300rpx;">
          <view class="font-md" v-show="item.investType == '1'" style="color: red;">+ {{ item.incomeAmount }}</view>
          <view class="font-md" v-show="item.investType == '2'" style="color: #09BB07;">- {{ item.incomeAmount }}</view>
          <view class="font-sm" style="color: #999999;">{{ item.addDate }}</view>
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
      positionStatus: 2 //持有状态为已卖出
    }
  },
  onLoad() {
    var token = uni.getStorageSync('token');
    // 非法访问，请重新登录
    if (token === null || token === undefined || token === '') {
      // 跳转页面
      uni.reLaunch({
        url: '../login/login'
      });
    }
  },
  methods: {
    showUpOrDown(investType, money) {
      let desc = investType === 1 ? "买涨" : "买跌";
      return desc + " (￥" + money + ")";
    },
	loadData(){
		var data = {"positionStatus": this.positionStatus};
		//发起查询数据
		https.myPosition(data).then((res) => {
		  if (res != null) {
			this.positionList = res.list;
			// 数据请求完成之后停止下拉刷新
			uni.stopPullDownRefresh();
			console.log("交易历史数据:" + res.list);
		  }
		})
	}
  },
  onShow() {
    document.title = '币安秒合约';
	this.loadData();
  },
	// 监听下拉刷新动作的执行方法，每次手动下拉刷新都会执行一次
	onPullDownRefresh() {
		this.loadData();
		uni.showToast({
			title: '刷新成功！',
			duration: 500,
		})
	}
}
</script>

<style>

</style>
