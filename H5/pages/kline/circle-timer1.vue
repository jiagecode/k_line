<template>
	<view style="width: 100%;display: block;margin: 0 auto;">
		<svg :width="size" :height="size" viewbox="0 0 120 120" style="display: block;margin: 0 auto;position: relative;">
			<circle :cx="cx" :cy="cx" :r="r" :stroke-width="width" stroke-linecap="round" stroke="#D1D3D7" fill="none"></circle>
			<circle :cx="cx" :cy="cx" :r="r" :stroke-width="width" stroke-linecap="round" stroke="#00A5E0" fill="none"
				transform="matrix(0,-1,1,0,0,120)" ></circle>
				<text x="0" y="15" id="text" fill="red">在平坦的路上曲折前行</text>
				<!-- <text style="fill:red;font-size:24pt;">123</text> -->
		</svg>
		<!-- <p>拖我：<input id="range" type="range" min="0" max="100" value="0" style="width:300px;"></p> -->
		<view style="position: absolute;">123</view>
	</view>
</template>

<script>
	export default {
		name: "CircleLoad1",
		data() {
			return {
				size: 120,
				width: 4,
				stroke: 10,
				// percentage: 100,
				timer: null,
				seconds: 30
			}
		},
		props: {
			// 进度值
			percentage: {
				type: [Number, String],
				default: 20,
			},
		},
		computed: {
			cx() { // 中心点位置
				return this.size / 2;
			},
			r() { // 半径
				return this.size / 2 - this.width;
			},
			circum() {
				console.log(parseInt(this.r * Math.PI * 2));
				return parseInt(this.r * Math.PI * 2);
			}
		},
		methods: {
			start() {
				var percent = parseInt(this.percentage) / 100;
				var perimeter = Math.PI * this.r * 2;
				var circle = document.querySelectorAll("circle")[1];
				circle.setAttribute('stroke-dasharray', perimeter * percent + " " + perimeter * (1 - percent));
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.start();
			});
		},
		created() {},
		filters: {},
		watch: {},
		directives: {}
	};
</script>

<style>
	circle {
		-webkit-transition: stroke-dasharray .25s;
		transition: stroke-dasharray .25s;
	}
</style>
