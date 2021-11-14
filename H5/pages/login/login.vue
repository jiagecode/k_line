<template>
	<view class="body sv">
		<!-- 密码登录提示 -->
		<H1 style="padding-top: 80rpx;">密码登录</H1>
		<view style="padding-top: 75rpx;">
			<uni-forms>
				<!-- 手机号 -->
				<view v-if="loginType" class="d-flex a-center" style=" border-bottom: solid #ececec 2rpx;">
					<navigator hover-class="none" open-type="navigate" url="../address/address">
						<text style="color: #5586d3;">{{countryAndCode}}</text>
						<uni-icons type="arrowdown" color="#5586d3" style="margin-left: 10rpx;" />
					</navigator>
					<uni-forms-item label="" name="name" style="margin-top: 50rpx;">
						<uni-easyinput style="margin-left: 15rpx;" type="number" :inputBorder="false"
							placeholder="请输入手机号" v-model="phone" @blur="blur(phone)" />
					</uni-forms-item>
				</view>
				<view v-if="!loginType" class="d-flex a-center" style=" border-bottom: solid #ececec 2rpx;">
					<uni-icons type="email" color="#5586d3" style="margin-left: 10rpx;" />
					<uni-forms-item label="" name="name" style="margin-top: 50rpx;">
						<uni-easyinput style="margin-left: 15rpx;" type="email" :inputBorder="false" placeholder="请输邮箱"
							v-model="email" @blur="checkEmail(email)" />
					</uni-forms-item>
				</view>
				<!-- 密码 -->
				<view class="d-flex a-center" style="margin-top: 55rpx; border-bottom: solid #ececec 2rpx;">
					<uni-icons type="locked" color="#5586d3" size="20" />
					<uni-forms-item label="" name="name" style="margin-top: 50rpx;">
						<uni-easyinput type="password" :inputBorder="false" placeholder="输入密码" v-model="password" />
					</uni-forms-item>
				</view>
				<!-- 用户协议 -->
				<view class="d-flex a-center" style="margin-top: 55rpx;">
					<image v-show="isXy" style="width: 40rpx; height: 40rpx;" src="/static/checkbox.png" @click="xy" />
					<image v-show="!isXy" style="width: 40rpx; height: 40rpx;" src="/static/checkedbox.png"
						@click="xy" />
					<text style="color: #b2b2b2;">我已阅读并同意隐私政策以及用户协议</text>
				</view>
				<!-- 登录按钮 -->
				<view style="margin-top: 75rpx;">
					<button v-show="isXy" style="background-color: #dddddd; color: #FFFFFF;">登录</button>
					<button v-show="!isXy" style="background-color: #5586d3; color: #FFFFFF;"
						@click="toLogin()">登录</button>
				</view>
				<!-- 已有账户登录 -->
				<view class="d-flex a-center j-center" style="margin-top: 35rpx; color: #5586d3;">
					<text v-show="loginType" @click="toEmailPage()">邮箱登录</text>
					<text v-show="!loginType" @click="toPhonePage()">手机登录</text>
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
				email: '',
				password: '',
				value: 1,
				countryAndCode: '',
				isXy: true,
				loginType: true,
				emails:['@qq.com', '@163.com', '@126.com', '@netease.com', 
				'@yeah.net', '@vip.163.com', '@vip.126.com', '@188.com', 
				'@163.net', '@china.com', '@vip.sina.com', '@eyou.com',
				'@tom.com', '@263.net', '@139.com', '@21cn.com', '@yahoo.cn',
				'@wo.cn', '@foxmail.com', '@aliyun.com', '@outlook.com', '@hotmail.com', '@yahoo.com.cn'],
				checkEmailResult: true,
				v: undefined
			}
		},
		// option为object类型，会序列化上个页面传递的参数
		onLoad(options) {

			// 根据定位对象取出对应的城市			
			const country = (options.country && JSON.parse(decodeURIComponent(options.country))) || {
				"area": "中国",
				"area_code": "+86",
				pinyin: 'Z'
			};
			// 拼接城市和区号
			// this.countryAndCode =  country.area + '(' + country.area_code + ')';
			this.countryAndCode = country.area_code;
		},
		methods: {
			// 登录
			toLogin() {

				// 封装请求数据
				var data = {
					"userPhone": this.phone !== '' ? this.phone : this.email,
					"userPassword": this.password
				};

				// 发起登录请求
				https.userLogin(data).then((res) => {
					if (res != null) {
						// 缓存token
						uni.setStorageSync('token', res)
						// 请求用户信息
						https.getUserInfo().then((res) => {
							// 缓存用户信息
							uni.setStorageSync('userInfo', res);
						});
						// 提示用户
						uni.showToast({
							title: '登陆成功！',
							duration: 1000,
						})
						setTimeout(() => {
								// 跳转页面
								uni.switchTab({
									url: '../home/home'
								})
							},
							900)
					}
				})
			},

			// 邮箱登录
			toEmailPage() {
				this.loginType = !this.loginType;
				this.phone = ''
			},

			// 手机登录
			toPhonePage() {
				this.loginType = !this.loginType;
				this.email = ''
			},

			// 同意协议
			xy() {
				if(this.loginType){
					
					if (this.phone === '') {
						// 提示用户
						uni.showToast({
							title: '请先输入手机号！',
							duration: 1000,
							icon: 'none'
						})
					} else if (this.countryAndCode === '+86') {
						this.showMsg('平台不持支大陆的手机号，请使用其它手机号！');
					} else {
						this.isXy = !this.isXy;
					}
				} else {
					
					if (this.email === '') {
						// 提示用户
						uni.showToast({
							title: '请先输入邮箱号！',
							duration: 1000,
							icon: 'none'
						})
					} else if (this.v) {
						this.showMsg('平台不支持中国大陆内的邮箱,请使用其他邮箱!');
					} else {
						this.isXy = !this.isXy;
					}
				}
				// console.log("按钮被点击了，且传入的参数是：" + this.isXy)
			},
			blur(phone) {

				if (phone === '') {
					callback1(new Error('手机号不可为空'));
				} else {
					var reg = /^1[3456789]\d{9}$/;
					if (reg.test(phone)) {
						// 判断是否86手机号
						this.isPhone(phone);
					} else {
						callback(new Error('请输入有效的手机号码'));
					}
				}
				
				function callback1(Error) {
					uni.showModal({
						title: Error.message,
					});
				}

				function callback(Error) {
					uni.showModal({
						title: Error.message,
					});
				}
			},
			isPhone(phone) {

				console.log('有效手机号为：', phone);
				if (this.countryAndCode === '+86') {
					this.showMsg('平台不持支大陆的手机号，请使用其它手机号！');
				}
			},
			showMsg(text) {
				
				uni.showModal({
					content: text,
					showCancel: false,
					confirmText: '我已知晓'
				})
			},
			checkEmail(email) {
				let _this = this;
				if(email === ''){
						callback1(new Error('邮箱地址不可为空'));
				} else {
					
					// 校验邮箱
					_this.checkEmailResult = RegExp(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/).test(email);
					if (_this.checkEmailResult) {
						checkDomesticEmail(_this, email);
					} else {
						callback1(new Error('请输入正确的邮箱地址'));
					}
					
					function callback1(Error) {
						uni.showModal({
							title: Error.message
						});
					}
					
					function checkDomesticEmail(_this, email){
						
						// 转小写
						email = email.toLowerCase();
						// 校验qq邮箱
						for(let ms of _this.emails){
							_this.v = email.indexOf(ms);
							console.log(_this.v);
							if(_this.v !== -1){
								
								_this.showMsg('平台不支持中国大陆内的邮箱,请使用其他邮箱!');
								_this.v = true;
								break;
							} else {
								
								_this.v = false;
							}
						}
						console.log(_this.v);
					}
				}
			}
		}
	}
</script>

<style>
	.body {
		margin: 0 50rpx 0 50rpx;
	}
</style>
