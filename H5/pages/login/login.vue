<template>
	<view>
		<button class="agree" @click="toLogin">注册/登录</button>
	</view>
</template>

<script>
	import https from '../../api/api.js'
	
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			// 登录
			toLogin(){
				
				// 发送 res.code 到后台换取 openId, sessionKey, unionId
				var data = {
					"userName": 'zjs',
					"password": '123456'
				};
				
				// 登录
				https.userLogin(data).then(res=> {
					console.log(res)
				   uni.setStorageSync('token', res.token)
				})
			}
		}
	}
</script>

<style>

</style>
