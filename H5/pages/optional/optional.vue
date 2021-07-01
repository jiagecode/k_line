<template>
	<view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		},
		onShow() {
			document.title = '币安秒合约';
		},
		onLoad() {
				
			// 非法访问，请重新登录
			if(uni.getStorageSync('token') === null || uni.getStorageSync('token') === undefined || uni.getStorageSync('token') === ''){
				// 跳转页面
				uni.reLaunch({
					url: '../login/login'
				});
			}
		}
	}
</script>

<style>

</style>
