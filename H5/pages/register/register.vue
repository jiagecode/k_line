<template>
	<view class="body sv">
		<!-- 密码登录提示 -->
		<H1 style="padding-top: 75rpx;">注册账号</H1>
		<view style="padding-top: 75rpx;">
			<uni-forms>
				<!-- 姓名 -->
				<view class="d-flex a-center" style="height: 80rpx; border-bottom: solid #ececec 2rpx;">
					<uni-icons type="personadd" color="#5586d3" size="20"/>
					<uni-forms-item label="" name="" style="margin-top: 50rpx;">
						<uni-easyinput type="text" :inputBorder="false" placeholder="输入姓名" v-model="userName" />
					</uni-forms-item>
				</view>
				<!-- 手机号 -->
				<view class="d-flex a-center" style="margin-top: 55rpx; height: 80rpx; border-bottom: solid #ececec 2rpx;">
					<navigator hover-class="none" open-type="navigate" url="../address/address">
						<text style="color: #5586d3;">{{countryAndCode}}</text>
						<uni-icons type="arrowdown" color="#5586d3" style="margin-left: 10rpx;"/>
					</navigator>
					<uni-forms-item label="" name="" style="margin-top: 50rpx;">
						<uni-easyinput style="margin-left: 15rpx;" type="number" :inputBorder="false" placeholder="请输入手机号" v-model="phone" />
					</uni-forms-item>
				</view>
				<!-- 密码 -->
				<view class="d-flex a-center" style="margin-top: 55rpx; height: 80rpx; border-bottom: solid #ececec 2rpx;">
					<uni-icons type="locked" color="#5586d3" size="20"/>
					<uni-forms-item label="" name="" style="margin-top: 50rpx;">
						<uni-easyinput type="password" :inputBorder="false" placeholder="设置登录密码" v-model="passwordOne" />
					</uni-forms-item>
				</view>
				<!-- 确认密码 -->
				<view class="d-flex a-center" style="margin-top: 55rpx; height: 80rpx; border-bottom: solid #ececec 2rpx;">
					<uni-icons type="checkbox-filled" color="#5586d3" size="20"/>
					<uni-forms-item label="" name="" style="margin-top: 50rpx;">
						<uni-easyinput type="password" :inputBorder="false" placeholder="确认登录密码" v-model="passwordTwo" />
					</uni-forms-item>
				</view>
				<!-- 用户协议 -->
				<view class="d-flex a-center" style="margin-top: 55rpx;">
					<image v-show="isXy" style="width: 40rpx; height: 40rpx;" src="/static/checkbox.png" @click="xy" />
					<image v-show="!isXy" style="width: 40rpx; height: 40rpx;" src="/static/checkedbox.png" @click="xy" />
					<text style="color: #b2b2b2;">我已阅读并同意隐私政策以及用户协议</text>
				</view>
				<!-- 登录按钮 -->
				<view style="margin-top: 65rpx;">
					<button v-show="isXy" style="background-color: #dddddd; color: #FFFFFF;">注册</button>
					<button v-show="!isXy" style="background-color: #5586d3; color: #FFFFFF;" @click="toRegister()">注册</button>
				</view>
				<!-- 已有账户登录 -->
				<view class="d-flex a-center j-center" style="margin-top: 35rpx; color: #5586d3;">
					<text @click="toLoginPage()">已有账户登录</text>
				</view>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	import https from '../../api/api.js'
	
	export default {
		data() {
			return {
				phone: '',
				passwordOne: '',
				passwordTwo: '',
				userName: '',
				countryAndCode: '',
				isXy: true
			}
		},
		// option为object类型，会序列化上个页面传递的参数
		onLoad(options) {
			
			// 根据定位对象取出对应的城市			
			const country = (options.country && JSON.parse(decodeURIComponent(options.country))) || {"area": "中国","area_code": "+86", pinyin: 'Z'};
			// 拼接城市和区号
			// this.countryAndCode =  country.area + '(' + country.area_code + ')';
			this.countryAndCode = country.area_code;
		},
		methods: {
			// 注册
			toRegister(){
				
				if(this.phone.length !== 11){
					uni.showToast({
						icon: 'none',   
						duration: 1000,
						title: '手机号不正确'
					});
					return;
				}
				
				if(this.passwordOne !== this.passwordTwo){
					uni.showToast({
						icon: 'none',   
						duration: 1000,
						title: '密码不一致'
					});
					return;
				}
				
				var data = {
					'userPhone': this.phone,
					'userRealName': this.userName,
					'userPassword': this.passwordOne
				};
				
				https.userRegister(data).then((res)=> {
					if(res != null){
						// 缓存token
						uni.setStorageSync('token', res)
						// 请求用户信息
						https.getUserInfo().then((res)=> {
							// 缓存用户信息
							uni.setStorageSync('userInfo', res);
						});
						// 提示用户
						uni.showToast({
						    title: '注册成功！',
						    duration: 1000,
						})
						setTimeout(() => {
							// 跳转页面
							uni.switchTab({
								url:'../home/home'
							})}, 
						900)
					}
				})
			},
			// 到登录页
			toLoginPage(){
				
				console.log("toLogin");
				uni.redirectTo({
					url:'../login/login'
				})
			},
				
			// 同意协议
			xy() {
				this.isXy = !this.isXy;
			}
		}
	}
</script>

<style>
.body{
	margin: 0 50rpx 0 50rpx;
}
</style>
