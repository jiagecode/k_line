<template>
	<view class="uni-indexed">
		<view v-if="touchmoveIndex>0" class='index-static'>{{scrollViewId}}</view>
		<scroll-view :scroll-into-view="scrollViewId" :style="{ height: winHeight + 'px' }" class="uni-indexed__list"
		 scroll-y @scroll="scrollEvent">
			<block v-for="(list, idx) in dataList" :key="idx">
				<view class="index-list-item">
					<view v-if="list.data && list.data.length" :id="list.letter" class="uni-indexed__list-title">{{ list.letter }}</view>
					<view v-if="list.data && list.data.length" class="uni-list">
						<view v-for="(item, index) in list.data" :key="index" class="uni-list-item" hover-class="uni-list-item--hover">
							<view class="uni-list-item__container" @click="onClick(idx, index)">
								<view class="uni-list-item__content">{{ item.area }}</view>
								<view class="uni-list-item__content" style="margin-left: 400rpx;">{{ item.area_code }}</view>
							</view>
						</view>
					</view>
				</view>
			</block>
		</scroll-view>
		<view :class="touchmove ? 'active' : ''"
		       class="uni-indexed__menu"
			   @touchstart.stop="touchStart"
			   @touchmove.stop="touchMove"
			   @touchend.stop="touchEnd">
			<view v-for="(list, key) in dataList" :key="key" :class="touchmoveIndex == key ? 'active' : ''" class="uni-indexed__menu-item">{{ list.letter }}</view>
		</view>
		<view v-if="touchmove" class="uni-indexed--alert">{{dataList[touchmoveIndex].letter }}</view>
	</view>
</template>
<script>
	export default {
		name: 'indexList',
		props: {
			dataList: {
				type: Array,
				default () {
					return [];
				}
			}
		},
		data() {
			return {
				lists: [],
				touchmove: false,
				touchmoveIndex: 0,
				itemHeight: 0,
				winHeight: 0,
				letterList: [],
				baseH: 18,
				letterH: 0,
				itemH: 0,
				scrollIndex: []
			};
		},
		computed: {
			scrollViewId() {
				if(this.touchmoveIndex === -1){
					this.touchmoveIndex = this.dataList.length - 1;
				} else {
					this.touchmoveIndex = this.touchmoveIndex - 1;
				}
				let d = this.dataList[this.touchmoveIndex];
				if(d === undefined){
					return;
				} else{
					return d.letter;
				}
			}
		},
		created() {
			let winHeight = uni.getSystemInfoSync().windowHeight;
			this.winHeight = winHeight;
		},
		// #ifndef H5
		onReady() {
			this.calcScrollH()
		},
		// #endif
		// #ifdef H5
		mounted() {
			this.calcScrollH()
		},
		// #endif
		methods: {
			async calcScrollH() {
				var domH = 0;
				var query = uni.createSelectorQuery().in(this)
				query.select('.uni-indexed__list-title').boundingClientRect()
				query.select('.uni-list-item').boundingClientRect()
				query.exec(res => {
					this.letterH = res[0].height
					this.itemH = res[1].height
					this.dataList.forEach((item, index) => {
						var indexObj = {}
						indexObj[item.letter] = item.data.length
						domH += this.letterH + this.itemH * (item.data.length)
						indexObj.domH = domH
						this.scrollIndex.push(indexObj)
					})
				})
			},
			touchStart(e) {
				this.touchmove = true;
				this.touchmoveIndex = -1;
				let pageY = e.touches[0].pageY;
				let eleH = this.baseH * this.dataList.length
				let top = (this.winHeight - eleH) / 2
				let index = Math.floor((pageY - top) / this.baseH) + 1;
				let item = this.dataList[index];
				if (item) {
					this.touchmoveIndex = index;
				}
			},
			touchMove(e) {
				this.touchmove = true;
				let pageY = e.touches[0].pageY;
				let eleH = this.baseH * this.dataList.length
				let top = (this.winHeight - eleH) / 2
				let index = Math.floor((pageY - top) / this.baseH);
				let item = this.dataList[index];
				if (item) {
					this.touchmoveIndex = index;
				}
			},
			touchEnd() {
				this.touchmove = false;
			},
			onClick(idx, index) {
				let letter = this.dataList[idx].letter
				let data = this.dataList[idx].data[index]
				uni.showToast({
					title: data.area + ' ' + data.area_code,
					duration: 600,
					icon: 'none'
				})
				// $emit $emit 触发父组件的自定义事件
				this.$emit('click', data.area_code);
			},
			scrollEvent(e) {
				if (!this.touchmove) {
					if(this.scrollIndex.length) {
						var scrollTop = e.detail.scrollTop
						if (scrollTop == 0 || scrollTop < this.scrollIndex[0].domH) {
							this.touchmoveIndex = 0
						} else {
							this.scrollIndex.forEach((item, index) => {
								if (index > 0) {
									if (this.scrollIndex[index - 1].domH <= scrollTop && scrollTop <= this.scrollIndex[index].domH ) {
										this.touchmoveIndex = index
									}
								}
							})
						}
					}
				}
			}
		}
	};
</script>
<style>
	@charset "UTF-8";
	.uni-list {
		background-color: #fff;
		position: relative;
		width: 100%;
		display: flex;
		flex-direction: column
	}
	.uni-list::after {
		position: absolute;
		z-index: 10;
		right: 0;
		bottom: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc
	}
	.uni-list::before {
		position: absolute;
		z-index: 10;
		right: 0;
		top: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc
	}
	.uni-list-item {
		font-size: 32upx;
		position: relative;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		align-data: center
	}
	.uni-list-item__container {
		padding: 24upx 30upx;
		width: 100%;
		box-sizing: border-box;
		flex: 1;
		position: relative;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-data: center
	}
	.uni-list-item__container:after {
		position: absolute;
		z-index: 3;
		right: 0;
		bottom: 0;
		left: 30upx;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc
	}
	.uni-list-item__content {
		width: 60%;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.uni-indexed {
		display: flex;
		flex-direction: row
	}
	.uni-indexed__list {
		flex: 1;
		height: 100vh
	}
	.uni-indexed__list-title,
	.index-static {
		padding: 10upx 24upx;
		line-height: 1.5;
		background-color: #f7f7f7;
		font-size: 24upx
	}
	.uni-list-item--hover {
		background: #eeeeee;
	}
	.index-static {
		position: fixed;
		top: 0;
		width: 100%;
		top: var(--window-top);
		z-index: 99999;
	}
	.uni-indexed__menu {
		overflow: hidden;
		align-items: center;
		position: fixed;
		right: 5px;
		top: 50%;
		transform: translateY(-50%);
		z-index: 1000;
	}
	.uni-indexed__menu.active {
		background-color: #eeeeee
	}
	.uni-indexed__menu.active .uni-indexed__menu-item {
		color: #000000;
	}
	.uni-indexed__menu.active .uni-indexed__menu-item.active {
		color: #ffffff;
		background: #009D37;
		border-radius: 50%;
	}
	.uni-indexed__menu-item {
		color: #000000;
		font-size: 10px;
		font-weight: bold;
		text-align: center;
		display: block;
		height: 18px;
		width: 18px;
		line-height: 18px;
	}
	.uni-indexed__menu-item.active {
		color: #ffffff;
		background: #009D37;
		border-radius: 50%;
	}
	.uni-indexed--alert {
		position: absolute;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		z-index: 20;
		width: 150upx;
		height: 150upx;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		border-radius: 50%;
		text-align: center;
		font-size: 60upx;
		color: #ffffff;
		font-weight: normal;
		background-color: rgba(0, 0, 0, .4);
	}
</style>
