<template>
  <!-- 提现 -->
  <view class="bc" style="padding: 25rpx 4.6% 0 4.6%;">
		  
	<!-- 优先展示绑定的银行卡 -->
	<div>
		<block v-for="(item, index) in cardList" :key="item.bankCardId">
			<view class="d-flex a-center j-center" style="background-color: #159682; height: 220rpx; border-radius: 15rpx;">
				<view style="height: 80%; width: 90.6%;">
				  <view class="d-flex">
						<image style="background-color: #FFFFFF; width: 70rpx; height: 70rpx; border-radius: 50rpx;" src="/static/yh.png" @click="xy" />
						<view class="d-flex flex-column j-center a-cent" style="margin-left: 20rpx;">
							<view class="font-md" style="color: #FFFFFF;">{{ item.bankName }}</view>
							<view class="font-sm" style="color: #eddcd6;">储蓄卡</view>
					  </view>
				  </view>
				  <view class="font-lgg" style="margin-left: 60rpx; color: #FFFFFF;">{{ item.cardNo }}</view>
				</view>
			</view>
			<!-- 银行卡间隙 -->
			<view style="margin-top: 10rpx;"></view>
		</block>
	</div>
	<!-- 添加银行卡 -->
    <view class="d-flex a-center j-center" v-if="cardList.length === 0" style="background-color: #eeeeee; border: dashed #ececec 5rpx; border-radius: 15rpx; height: 150rpx;">
      <navigator url="../bank-info/bank-info" hover-class="none">
        <image style="margin-left: 40rpx; width: 50rpx; height: 50rpx;" src="/static/tj.png"></image>
        <view style="color: #888888;">添加银行卡</view>
      </navigator>
    </view>
      <!-- 用户提现   -->
    <view v-if="cardList.length > 0" style="padding: 10px; margin-top: 10rpx; border: solid #ececec 5rpx; border-radius: 15rpx;">
        <view class="d-flex a-center j-center">
			<text class="font-md" style="font-weight: bold;">提现金额：</text>
			<uni-easyinput type="number" style="color: #5586d3;" placeholder="金额" v-model="cashMoney" @blur="changeMoney"/>
		</view>
		<view style="margin-top: 20rpx; color: #e45a71;">
			<text>单次提现金额至少￥ 100</text>
		</view>
		<view class="d-flex"style="margin-top: 15rpx;">
			<text class="font-sm" style="color: rgb(178, 178, 178);">账户余额:<text style="margin-left: 12rpx; color: #5586d3;">{{accMoney}}</text></text><br/>
			<text class="font-sm" style="margin-left: 30rpx; color: rgb(178, 178, 178);">手续费:<span style="margin-left: 12rpx; color: #5586d3">1%</span></text><br/>
			<text class="font-sm" style="margin-left: 30rpx; color: rgb(178, 178, 178);">实际到账:<span style="margin-left: 12rpx; color: #5586d3">{{arriveMoney}}</span></text>
		</view>
		<view style="margin-top: 15rpx;">
			<button style="background-color: #5586d3; color: #FFFFFF; " @click="addCashOut()">立即提现</button>
		</view>
     </view>

  </view>
</template>

<script>
import https from "../../api/api";
import MathUtil from '@/utils/MathUtil.js'
export default {
	data() {
		return {
		  cashMoney: undefined,
      accMoney:undefined,
      arriveMoney:undefined,
			cardList: []
		}
	},
	methods: {
    changeMoney(){
      if(this.cashMoney != undefined && this.cashMoney != null){
        if(this.cashMoney > this.accMoney){
          this.cashMoney = this.accMoney;
        }
        this.arriveMoney = MathUtil.mul(this.cashMoney, 0.99) ;
      }
    },
    addCashOut(){
      if(this.cashMoney != undefined && this.cashMoney != null){
        if(this.cashMoney > this.accMoney){
          this.cashMoney = this.accMoney;
        }
        if(this.cashMoney < 100){
          uni.showToast({
            title: '提现金额不能低于100！',
            duration: 1000,
          })
          return;
        }
        this.accMoney = MathUtil.sub(this.accMoney,this.cashMoney);
          var data = {
            cashType : 1,
            cashMoney : this.cashMoney
          }
          https.addCash(data).then((res)=>{
            if(res != null ){
              if(res.code == 10000){
                uni.showToast({
                  title: '提现成功！',
                  duration: 1000,
                })
              }else {
                uni.showToast({
                  title: res.message,
                  duration: 1000,
                })
              }
            }
          })
      }
    },

		loadData(){
			https.myCardList({}).then((res) => {
				if (res != null) {
					// 数据请求完成之后停止下拉刷新
					uni.stopPullDownRefresh();
					this.cardList = res.list;
					console.log("银行卡数据:" + res.list);
				}
			})
      // 请求用户信息
      https.getUserInfo().then((res)=> {
        this.accMoney = res.userMoney;
      });

		}
	},
	onShow() {
		document.title = '币安秒合约';
		this.loadData();
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
