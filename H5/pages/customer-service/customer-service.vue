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
				<view class="font-mdd" v-if="item.type === 'serviceList' && isShow" style="margin-top: 20rpx;">
					<view>请选择在线客服：</view>
					<block v-for="(i, index) in item.msg">
						<view class="d-flex a-center" style="width: 80%; color: #0078f8;" @tap="selectService(i)">（小姐姐）{{i.name}}</view>
					</block>
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
	export default {
		data(){
			return{
				msg:"",
				msgList:[],
				serviceList:[],
				isShow:true,
				socketTask: null,
				// 确保websocket是打开状态
				is_open_socket: false
			}
		},
    onLoad() {
        // 进入这个页面的时候创建websocket连接【整个页面随时使用】
        this.connectSocketInit();
    },
    onShow() {
      document.title = '币安秒合约';
    },
		methods:{
            // 进入这个页面的时候创建websocket连接【整个页面随时使用】
            connectSocketInit() {
				var _this = this;
				let userInfo = uni.getStorageSync('userInfo');
                // 创建一个this.socketTask对象【发送、接收、关闭socket都由这个对象操作】
                this.socketTask = uni.connectSocket({
                    // 【非常重要】必须确保你的服务器是成功的,如果是手机测试千万别使用ws://127.0.0.1:9099【特别容易犯的错误】
					url: 'ws://192.168.1.9:1686/study/websocket/' + userInfo.userId + ',' + userInfo.userRealName +'/user',
                    success(data) {
                        console.log("websocket连接成功");
                    },
                });
 
                // 消息的发送和接收必须在正常连接打开中,才能发送或接收【否则会失败】
                this.socketTask.onOpen((res) => {
                    console.log("WebSocket连接正常打开中...！");
                    this.is_open_socket = true;
                    // 注：只有连接正常打开中 ，才能正常成功发送消息
                    // this.socketTask.send({
                    //     data: "uni-app发送一条消息",
                    //     async success() {
                    //         console.log("消息发送成功");
                    //     },
                    // });
                })
				
				// 注：只有连接正常打开中 ，才能正常收到消息
				this.socketTask.onMessage((res) => {
					console.log("收到服务器内容：" + res.data);
					var str = res.data; 
					// 字符串转json对象
					var jsonStr = str.replace(" ", ""); 
					if (typeof jsonStr != 'object') {
						jsonStr = jsonStr.replace(/\ufeff/g, "");//重点 
						var jj = JSON.parse(jsonStr); 
						res.data = jj; 
					}
					let a = {"userType": "user", "sid": null, "content": "当前在线客服繁忙，请稍后再试"};
					// 发送给客户端
					_this.socketMsg = '{"msdToUserType":"sys","msdToSid":"' +  res.data.msdToSid + '","content":"' + res.data.content + '"}';
					// H5消息对象
					if(res.data.msdToUserType === 'serviceList'){
						// serviceList赋值
						for(let sn of res.data.content.split("|")){
							if(sn !== '' && sn.includes(",")){
								let serviceInfo = sn.split(",");
								_this.serviceList.push({id:serviceInfo[0], name:serviceInfo[1]});
							}
						}
						_this.msgList.push({"type" : "serviceList", "msg" : _this.serviceList});
					
					} else {
						_this.msgList.push({"type" : "server", "msg" : res.data.content});
					}

					// 处理消息盒子
					if(this.msgList.length > 6){
						this.msgList.shift()
					}
				})
				
                // 这里仅是事件监听【如果socket关闭了会执行】
                this.socketTask.onClose(() => {
                    console.log("已经被关闭了")
                })
            },
            // 关闭websocket【离开这个页面的时候执行关闭】
            closeSocket() {
                this.socketTask.close({
                    success(res) {
                        this.is_open_socket = false;
                        console.log("关闭成功", res)
                    },
                    fail(err) {
                        console.log("关闭失败", err)
                    }
                })
            },
            send() {
                if (this.is_open_socket) {
					if(this.serviceId === undefined){
						uni.showToast({
							title: '请先选择客服',
							duration: 1000,
							icon:'error'
						})
						return;
					}
					let socketMsg = '{"msdToUserType":"sys","msdToSid":"' + this.serviceId + '","content":"' + this.msg + '"}';
                    // websocket的服务器的原理是:发送一次消息,同时返回一组数据【否则服务器会进去死循环崩溃】
                    this.socketTask.send({
                        data: socketMsg,
                        async success() {
                            console.log("消息发送成功");
                        },
                    })
				
					this.msgList.push({"type" : "user", "msg" : this.msg});

					// 处理消息盒子
					if(this.msgList.length > 6){
						this.msgList.shift()
					}
					this.msg = '';
                }
            },
			/**
			 * 选择客服
			 */
			selectService(e){
				console.log(e);
				uni.showToast({
					title: '选择客服：' + e.name,
					duration: 1000,
				})
				this.serviceId = e.id + ',' + e.name
				this.isShow = !this.isShow;
				uni.setStorageSync('serviceId', this.serviceId);
				this.msgList.push({"type" : "server", "msg" : e.name + '很高兴为您服务！'});
			}
		}
	}
</script>

<style>

</style>
