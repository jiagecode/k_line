<template>
	<view style="height: 100%; padding: 0 3%;">
		<view style="height: 92%;">
			<view style="height: 2%;"></view>
			<text class="d-flex a-center j-center font-lg " style="color: #FFFFFF; height: 90rpx; background-color: #5586d3;">
				欢迎访问币安秒合约在线客服
			</text>
			<!-- 客服 -->
			<view class="d-flex a-center" style="margin-top: 3%;">
				<image style="width: 14%; height: 113rpx;" src="/static/zxkf.png" />
				<text style="width: 83.4%; margin-left: 2.6%; font-weight: bold;" space="emsp">特别提示：平台将不定期更换卡号，请仔细核对充值的银行卡号，如因客户转账银行错误导致无法充值平台概不负责。</text>
			</view>
			<scroll-view v-for="(item,index) in msgList" :key="index">
				<view class="d-flex j-end font-mdd" v-show="item.type === 'user'" style="margin-top: 20rpx;">
					<view class="d-flex j-end" style="width: 80%;">{{item.msg}}</view>
					<view>：我</view>
				</view>
				<view class="d-flex font-mdd" v-show="item.type === 'server'" style="margin-top: 20rpx;">
					<view>客服：</view>
					<view style="width: 80%; color: #0078f8;">{{item.msg}}</view>
				</view>
			</scroll-view>
		</view>
		<view class="d-flex a-center" style="height: 8%; margin-top: -3%;">
			<uni-easyinput type="text" :inputBorder="false" placeholder="请写下你的问题" v-model="msg" />
			<button class="d-flex j-center a-center font-md" style="width: 18%; background-color: #5586d3; color: #FFFFFF;" @tap="send">发送</button>
		</view>
	</view>
</template>

<script>
	var app = getApp()
	export default {
		data() {
			return {
				msg:"",
				socketOpen: false,
				socketMsgQueue:[],
				msgList:[],
				socketMsg:{}
			}
		},
		async mounted() {
			console.log(this.socketOpen);
			this.socketOpen = true;
			this.wsInit();
		},
		methods: {
			// 连接websocket服务器
			wsInit(){
				
				var _this = this;
				if (!_this.socketOpen) return
				// 销毁ws
				// this.wsDestroy()
			
				// 根据用户ID创建websocket连接
				var user = uni.getStorageSync('userInfo');
				console.log(user.userId)
				uni.connectSocket({
					url: 'ws://192.168.1.9:1686/study/websocket/' + user.userId +'/user',
					success:((res)=>{
						console.log(res);
					})
				});
			
				uni.onSocketOpen((res)=> {
				  console.log('WebSocket连接已打开！');
				  _this.socketOpen = true;
				});
				
				uni.onSocketError(function (res) {
				  console.log('WebSocket连接打开失败，请检查！');
				});
				
				uni.onSocketMessage(function (res) {
				  var str = res.data; 
				  console.log('收到服务器内容：' + str);
				  // 字符串转json对象
				  var jsonStr = str.replace(" ", ""); 
				  if (typeof jsonStr != 'object') { 
					  jsonStr = jsonStr.replace(/\ufeff/g, "");//重点 
					  var jj = JSON.parse(jsonStr); 
					  res.data = jj; 
				  }
				  let a = {"userType": "user", "sid": null, "content": "当前在线客服繁忙，请稍后再试"};
				  console.log(a.content)
				  _this.socketMsg = '{"msdToUserType":"sys","msdToSid":"' +  res.data.msdToSid + '","content":"' + res.data.content + '"}';
				  _this.msgList.push({"type" : "server", "msg" : res.data.content});
				  
				  // 处理消息盒子
				  if(_this.msgList.length > 6){
					  _this.msgList.shift()
				  }
				});
					
			},
			
			send(){
			  if (this.socketOpen) {
				  
				let sid = this.socketMsg.msdToSid !== undefined ? this.socketMsg.msdToSid : null;
				let socketMsg = '{"msdToUserType":"sys","msdToSid":' + sid + ',"content":"' + this.msg + '"}';
				console.log(socketMsg)
				uni.sendSocketMessage({
				  data: socketMsg
				});
			  } else {
				this.socketMsgQueue.push(this.msg);
			  }
			  this.msgList.push({"type" : "user", "msg" : this.msg});
			  this.msg = '';
			  
			  // 处理消息盒子
			  if(this.msgList.length > 6){
				  this.msgList.shift()
			  }
			},
			
			colse(){
				uni.onSocketClose(function (res) {
				  console.log('WebSocket 已关闭！');
				});
			}
		},
		onLoad() {
			
			var token = uni.getStorageSync('token');
			console.log(token)
			// 非法访问，请重新登录
			if (token === null || token === undefined || token === '') {
			  // 跳转页面
			  uni.reLaunch({
				url: '../login/login'
			  });
			}
		},
		onShow() {
			var _this = this;
			document.title = '币安秒合约';
		}
	}
</script>

<style>

</style>
