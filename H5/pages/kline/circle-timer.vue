<template>
	<svg class="ring-container" :style="`width: ${size}; height: ${size}; display:block; margin:0 auto;`">
	<!-- <svg class="ring-container" :style="{width:size+'rpx',height:size+'rpx'}"> -->
	
		<circle :cx="cx" :cy="cx" :r="r" :stroke="backgroundColor" :stroke-width="width" stroke-linecap="round"
			fill="none"></circle>
		<circle :cx="cx" :cy="cx" :r="r" class="ring" :stroke-width="width" :stroke="color"
			:stroke-dasharray="`${circum}`" stroke-linecap="round" fill="none">
			<animate attributeName="stroke-dashoffset" :from="circum" :to="endCircum" :dur="originCountDown" />
		</circle>
		<text :x="cx+5" :y="cx+10" text-anchor="end" :font-size="fontSize + 5" :fill="fontColor" style="color: #5586d3;">
			{{ countDown }}
		</text>
		<text :x="cx+5" :y="cx+10" text-anchor="start" :font-size="fontSize - 5" :fill="fontColor">
			s
		</text>
	</svg>
</template>
 
<script>
	export default {
		name: "CircleLoad",
		data() {
			return {
				interval: null,
				endCircum: 0,
				countDown: this.originCountDown,
			}
		},
		computed: {
			cx() { // 中心点位置
				return this.size / 2
			},
			r() { // 半径
				return this.size / 2 - this.width
			},
			circum() {
				return parseInt(this.r * Math.PI * 2)
			}
		},
		props: {
			fontSize: {
				type: Number,
				default: 26
			},
			size: {
				type: Number,
				default: 120
			},
			width: {
				type: Number,
				default: 4
			},
			backgroundColor: {
				type: String,
				default: '#5586d3'
			},
			color: {
				type: String,
				default: '#434347'
			},
			fontColor: {
				type: String,
				default: '#5586d3'
			},
			originCountDown: {
				type: Number,
				default: 15
			}
		},
		mounted() {
			this.interval = setInterval(() => {
				let diff = '1';
				this.countDown -= diff;
				if (this.countDown <= 0) {
					clearInterval(this.interval)
				}
			}, 1000)
		}
	}
</script>

<style>
	
	.ring-container {
		display: inline-block;
		position: relative;
	}
	
	.ring-container .ring {
		transform: rotate(-90deg);
		transform-origin: 50% 50%;
	}
	
	span {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translatex(-50%) translatey(-50%);
			font-size: 80px;
			color: #ee9ca7;
			/* fallback for old browsers */
			:-webkit-linear-gradient(to right, #ffdde1, #ee9ca7);
			/* Chrome 10-25, Safari 5.1-6 */
			:linear-gradient(to right, #ffdde1, #ee9ca7);
			/* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	
		}
	
</style>
