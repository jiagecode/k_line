<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
    /* 官方UI库 */
    @import './common/uni.css';
    /* 第三方动画库 */
    @import './common/animate.css';
    /* 自定义图标库 */
    @import './common/icon.css';
	/* UI基础库 */
	@import './common/zy-main.css';
    /* 公共样式 */
    @import './common/common.css';
	
	/* 全局背景色 */
	page{
		background-color: #fafafa;
	}
	.go-to-realtime-button {
		width: 27px;
		height: 27px;
		position: absolute;
		display: none;
		padding: 7px;
		box-sizing: border-box;
		font-size: 10px;
		border-radius: 50%;
		text-align: center;
		z-index: 1000;
		color: #B2B5BE;
		background: rgba(250, 250, 250, 0.95);
		box-shadow: 0 2px 5px 0 rgba(117, 134, 150, 0.45);
	}
	.floating-tooltip-2 {
		width: 96px;
		height: 80px;
		position: absolute;
		display: none;
		padding: 8px;
		box-sizing: border-box;
		font-size: 12px;
		color: #131722;
		background-color: rgba(255, 255, 255, 1);
		text-align: left;
		z-index: 1000;
		top: 12px;
		left: 12px;
		pointer-events: none;
		border: 1px solid rgba(0, 150, 136, 1);
		border-radius: 2px;
	}
</style>
