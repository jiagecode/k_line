<template>
	<div class='divchart' style='background-color:#ffffff;'>
		<uni-row class="uni-row-top">
			<uni-col :span="9">
				<view class="uni-col-top dark topText">
					<div id="t_price">{{SocketMsg.PRICE}}</div>
				</view>
			</uni-col>
			<uni-col :span="5">
				<view class="uni-col-top light-top-t">开盘</view>
				<view class="uni-col-top light-top-d">
					<div id="t_openday">{{SocketMsg.OPENDAY}}</div>
				</view>
			</uni-col>
			<uni-col :span="5">
				<view class="uni-col-top light-top-t">最低</view>
				<view class="uni-col-top light-top-d">
					<div id="t_lowday">{{SocketMsg.LOWDAY}}</div>
				</view>
			</uni-col>
			<uni-col :span="5">
				<view class="uni-col-top light-top-t">最高</view>
				<view class="uni-col-top light-top-d">
					<div id="t_highday">{{SocketMsg.HIGHDAY}}</div>
				</view>
			</uni-col>
		</uni-row>

		<div id="lightweight">
			<!-- <view id="go-to-realtime-button" class="go-to-realtime-button"></view> -->
		</div>

		<uni-row>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-1m" type="default" size="mini" style="background-color: #dedede;"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_MINUTE_ID)">1M</button>
			</uni-col>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-15m" type="default" size="mini"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_15MINUTE_ID)">15M</button>
			</uni-col>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-30m" type="default" size="mini"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_30MINUTE_ID)">30M</button>
			</uni-col>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-1h" type="default" size="mini"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_60MINUTE_ID)">1H</button>
			</uni-col>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-1d" type="default" size="mini"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_DAY_ID)">日线</button>
			</uni-col>
			<uni-col :span="4">
				<button class="mini-btn btn-period btn-1w" type="default" size="mini"
					@click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_WEEK_ID)">周线</button>
			</uni-col>
		</uni-row>

		<!-- 		<div class="button-sp-area">
				<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(0,'EMPTY')">主图空指标</button>
				<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(0,'BOLL')">BOLL</button>
				<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'MACD')">MACD</button>
				<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'VOL')">VOL</button>
				<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'KDJ')">KDJ</button>
			</div>
	-->
		<!-- <div style='color: #007AFF;'>{{TestData}}</div> -->
		<uni-row class="uni-row-bot">
			<uni-col :span="8">
				<div @click="ClickCc()">
					<!-- <view class="uni-col-bot bot-cc light-bot-t"></view> -->
					<view class="uni-col-bot bot-cc light-bot-d">
						<!-- <image style="width: 40rpx; height: 40rpx;" src="/static/checkbox.png" /> -->
						持仓
					</view>
				</div>
			</uni-col>
			<uni-col :span="8">
				<div @click="ClickBuy(1)">
					<!-- <view class="uni-col-bot bot-mz light-bot-t">xx</view> -->
					<view class="uni-col-bot bot-mz light-bot-d">买涨</view>
				</div>
			</uni-col>
			<uni-col :span="8">
				<div @click="ClickBuy(2)">
					<!-- <view class="uni-col-bot bot-md light-bot-t">xx</view> -->
					<view class="uni-col-bot bot-md light-bot-d">买跌</view>
				</div>
			</uni-col>
		</uni-row>

		<!-- 弹出层 -->
		<vus-layer>
			<!--slot(插槽)-->
			<view slot="page">
				<!--在这里写入页面层的html内容-->
				<view>
					<div class="page-top-line"></div>
				</view>
				<view>
					<p class="page-top-dqsj">到期时间</p>
				</view>
				<view>
					<uni-row class="uni-row-page">
						<uni-col :span="8" class="row-page-item">
							<div @click="SelectBuyItem(1)" class="page-item page-item-1 page-item-select">
								<view class="uni-col-page light-page-t">结算时间</view>
								<view class="uni-col-page light-page-c"><b style="font-size: 50upx;">30</b>秒</view>
								<view class="uni-col-page light-page-d">收益 88%</view>
							</div>
						</uni-col>
						<uni-col :span="8" class="row-page-item">
							<div @click="SelectBuyItem(2)" class="page-item page-item-2">
								<view class="uni-col-page light-page-t">结算时间</view>
								<view class="uni-col-page light-page-c"><b style="font-size: 50upx;">60</b>秒</view>
								<view class="uni-col-page light-page-d">收益 90%</view>
							</div>
						</uni-col>
						<uni-col :span="8" class="row-page-item">
							<div @click="SelectBuyItem(3)" class="page-item page-item-3">
								<view class="uni-col-page light-page-t">结算时间</view>
								<view class="uni-col-page light-page-c"><b style="font-size: 50upx;">180</b>秒</view>
								<view class="uni-col-page light-page-d">收益 92%</view>
							</div>
						</uni-col>
					</uni-row>
				</view>
				<view>
					<p class="page-top-dqsj">投资金额</p>
				</view>
				<view>
					<uni-row class="uni-row-page1">
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(10)" class="page-amount page-amount-1 page-amount-select">
								<view class="uni-col-page1 light-page1-t">¥10</view>
							</div>
						</uni-col>
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(50)" class="page-amount page-amount-2">
								<view class="uni-col-page1 light-page1-t">¥50</view>
							</div>
						</uni-col>
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(100)" class="page-amount page-amount-3">
								<view class="uni-col-page1 light-page1-t">¥100</view>
							</div>
						</uni-col>
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(500)" class="page-amount page-amount-4">
								<view class="uni-col-page1 light-page1-t">¥500</view>
							</div>
						</uni-col>
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(1000)" class="page-amount page-amount-5">
								<view class="uni-col-page1 light-page1-t">¥1000</view>
							</div>
						</uni-col>
						<uni-col :span="4" class="row-page1-item">
							<div @click="SelectBuyAccount(5000)" class="page-amount page-amount-6">
								<view class="uni-col-page1 page1-md light-page1-t">¥5000</view>
							</div>
						</uni-col>
					</uni-row>
				</view>
				<view>
					<p class="page-top-dqsj"></p>
				</view>
				<uni-row class="">
					<uni-col :span="12" class="row-page1-item">
						<view class="page-left-ye">余额: ¥{{UserData.userMoney}} </view>
					</uni-col>
					<uni-col :span="12" class="row-page1-item">
						<view class="page-left-sxf">手续费: 0% </view>
					</uni-col>
				</uni-row>
				<view>
					<div class="page-c-line"></div>
				</view>
				<view>
					<uni-row class="uni-row-page2">
						<uni-col :span="6" class="row-page2-item">
							<div>
								<view class="uni-col-page2 light-page2-t">名称</view>
								<view class="uni-col-page2 light-page2-d page-2-name">{{OrderCoin}}</view>
							</div>
						</uni-col>
						<uni-col :span="6" class="row-page2-item">
							<div>
								<view class="uni-col-page2 light-page2-t">方向</view>
								<view class="uni-col-page2 light-page2-d page-2-fx"
									:class="(OrderDirection == 1)?'page-2-fx-red':'page-2-fx-green'">
									{{OrderDirectionName}}
								</view>
							</div>
						</uni-col>
						<uni-col :span="6" class="row-page2-item">
							<div>
								<view class="uni-col-page2 light-page2-t">现价</view>
								<view class="uni-col-page2 light-page2-d page-2-xj"
									:class="(OrderDirection == 1)?'page-2-xj-red':'page-2-xj-green'">{{OrderCoinPrice}}
								</view>
							</div>
						</uni-col>
						<uni-col :span="6" class="row-page2-item">
							<div>
								<view class="uni-col-page2 light-page2-t">金额</view>
								<view class="uni-col-page2 light-page2-d page-2-je">¥{{OrderAmount}}</view>
							</div>
						</uni-col>
					</uni-row>
				</view>
				<view>
					<p class="page-top-dqsj"></p>
				</view>
				<view>
					<button class="page-btn-submit" type="default" size="default" @click="SubmitBuy()">确认下单</button>
				</view>
				<uni-row>
					<uni-col :span="24">
						<view class="page-bottom-text">
							<div class="page-left-yqsy">预期收益:¥{{OrderExpectEarnings}}</div>
							<div class="page-left-bdje">保底金额:¥{{OrderGuarantee}}</div>
						</view>
					</uni-col>
				</uni-row>
				<view>
					<p class="page-top-dqsj"></p>
				</view>
			</view>
		</vus-layer>
		<!-- 弹出层 -->
	</div>
</template>

<style>
	/* // 弹出层引入CSS文件 */
	@import "../../components/vusui-app-layer/theme/default/vusui-layer.css";

	/* // 页面css */
	@import "../../common/tradingview.css";
</style>


<script>
	import {
		createChart
	} from "lightweight-charts";
	import moment from 'moment'
	import MathUtil from '@/utils/MathUtil.js'
	import https from '../../api/api.js'

	// 图表对象
	var chart = null;
	// k线图型对象
	var candlestickSeries = null;

	export default {
		data() {
			return {
				sysInfo: {},
				ChartWidth: 350,
				ChartHeight: 500,
				lineData: [],
				currentBar: {
					open: null,
					high: null,
					low: null,
					close: null,
					time: {
						day: 29,
						month: 5,
						year: 2019
					},
				},
				// socket连接对象
				ccStreamer: null,
				// socket 的数据
				SocketMsg: {
					PRICE: null,
					OPENDAY: null,
					LOWDAY: null,
					HIGHDAY: null,
				},
				// 用户数据
				UserData: null,
				// 订单数据
				OrderDirection: 1,
				OrderDirectionName: "买涨",
				OrderItem: 1,
				OrderCycle: 30,
				OrderAmount: 10,
				OrderCoin: "Bitcoin",
				OrderCoinPrice: null,
				OrderExpectEarnings: 18.8,
				OrderGuarantee: 0.00,
			}
		},
		onLoad() {
			// 查询分钟线数据 1小时60条
			https.klineMin({
				coinCode: "BTC"
			}).then((res) => {
				// console.log(res);
				// candlestickSeries.setData(this.lineData);
			});
			// 查询历史秒线数据 1小时3600条
			https.klineMils({
				coinCode: "BTC"
			}).then((res) => {
				if (null == res || typeof res == "undefined" || res.length == 0) {
					// 提示用户
					uni.showToast({
						title: '查询历史数据出错.',
						duration: 1000,
					})
				}
				for (var i = 0; i < res.length; i++) {
					var item = JSON.parse(res[i]);
					this.lineData.push({
						open: item.openPrice,
						high: item.maxPrice,
						low: item.minPrice,
						close: item.close,
						time: parseInt(item.timeStamp),
					});
				}
				candlestickSeries.setData(this.lineData);
				// console.log(this.lineData);
			});


		},
		onShow() {
			// 用户信息
			this.UserData = uni.getStorageSync('userInfo');
			var token = uni.getStorageSync('token');
			// 非法访问，请重新登录
			if (token === null || token === undefined || token === '' || this.UserData === null) {
				// 跳转页面
				uni.reLaunch({
					url: '../login/login'
				});
			}
			// 系统信息
			uni.getSystemInfo({
				success: (res) => {
					// 保存系统信息到页面全局
					this.sysInfo = res;
				}
			});
			// 重新计算页面元素高度
			var width = this.sysInfo.windowWidth;
			var height = this.sysInfo.windowHeight;
			this.ChartWidth = width;
			this.ChartHeight = height - 200;

			this.$nextTick(() => {
				var container = document.getElementById("lightweight");
				container.style.position = 'relative';
				chart = createChart(
					container, {
						width: this.ChartWidth,
						height: this.ChartHeight,
						rightPriceScale: {
							scaleMargins: {
								top: 0.2,
								bottom: 0.2,
							},
							borderVisible: false,
						},
						timeScale: {
							rightOffset: 2,
							borderVisible: false,
						},
						crosshair: {
							mode: 0,
						},
						// localization: {
						// 	dateFormat: "yyyy/MM/dd",
						// },
					}
				);
				chart.timeScale().scrollToPosition(-20, false);

				// 回到最新按钮
				var button = document.createElement('div');
				button.className = 'go-to-realtime-button';
				button.style.left = (this.ChartWidth - 27 - 60) + 'px';
				button.style.top = (this.ChartHeight - 27 - 30) + 'px';
				button.style.color = '#4c525e';
				button.innerHTML =
					'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 14" width="14" height="14"><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-width="2" d="M6.5 1.5l5 5.5-5 5.5M3 4l2.5 3L3 10"></path></svg>';
				container.appendChild(button);

				var timeScale = chart.timeScale();
				timeScale.subscribeVisibleTimeRangeChange(function() {
					var buttonVisible = timeScale.scrollPosition() < 0;
					button.style.display = buttonVisible ? 'block' : 'none';
					button.className = 'go-to-realtime-button';
				});

				button.addEventListener('click', function() {
					timeScale.scrollToRealTime();
				});

				button.addEventListener('mouseover', function() {
					button.style.background = 'rgba(250, 250, 250, 1)';
					button.style.color = '#000';
				});

				button.addEventListener('mouseout', function() {
					button.style.background = 'rgba(250, 250, 250, 0.6)';
					button.style.color = '#4c525e';
				});

				// 自定义颜色
				candlestickSeries = chart.addCandlestickSeries({
					// upColor: "#6495ED",
					// downColor: "#FF6347",
					// borderVisible: false,
					// wickVisible: true,
					// borderColor: "#000000",
					// wickColor: "#000000",
					// borderUpColor: "#4682B4",
					// borderDownColor: "#A52A2A",
					// wickUpColor: "#4682B4",
					// wickDownColor: "#A52A2A",
				});

				// 价格刻度
				chart.applyOptions({
					priceScale: {
						position: "right",
						mode: 0,
					}
				});
				// 时间刻度
				chart.applyOptions({
					timeScale: {
						borderVisible: true,
						visible: true,
						timeVisible: true,
						secondsVisible: true
					}
				});
				// 十字线
				chart.applyOptions({
					crosshair: {
						vertLine: {
							style: 1,
							visible: true,
							labelVisible: true
						},
						horzLine: {
							style: 1,
							visible: true,
							labelVisible: true
						},
						mode: 1
					}
				});


				// 设置数据
				candlestickSeries.setData(this.lineData);

				// 开启socket获取数据
				this.initSocketData();

			});
		},
		onHide() {
			uni.closeSocket();
			this.ClearChart();
		},

		onUnload() {
			uni.closeSocket();
			this.ClearChart();
		},
		methods: {
			initSocketData() {
				var that = this;
				uni.connectSocket({
					url: 'ws://192.168.1.7:1902/study/websocket/current/s/BTC/' + that.UserData.userId
				});
				uni.onSocketOpen(function(res) {
					console.log('WebSocket连接已打开！');
				});
				uni.onSocketError(function(res) {
					console.log('WebSocket连接打开失败，请检查！');
				});
				uni.onSocketMessage(function(res) {
					if (null == res || typeof res == "undefined") {
						return;
					}
					var msg = JSON.parse(res.data);
					var oldPrice = that.SocketMsg.PRICE;
					// {"timeStamp":"1626749353","skuCode":"BTC","nowPrice":30322.4816,"minPrice":30314.33,"maxPrice":30336.53,
					// "openPrice":30320.46,"volumeFrom":34.9,"volumeTo":1058050.46,"close":30327.87,"orderNum":13}
					var msgData = {
						PRICE: msg.nowPrice,
						OPENDAY: msg.openPrice,
						LOWDAY: msg.minPrice,
						HIGHDAY: msg.maxPrice,
					};
					that.SocketMsg = msgData;

					// 更新数据
					var currentBar = {
						open: msg.openPrice,
						high: msg.maxPrice,
						low: msg.minPrice,
						close: msg.close,
						time: parseInt(msg.timeStamp),
						// time: msg.timeStamp,
						// time: moment.unix(parseInt(msg.timeStamp)).format('YYYY-MM-DD'),
					};
					candlestickSeries.update(currentBar);
					// 现价
					that.OrderCoinPrice = msg.nowPrice;

					// 判断涨跌
					var price = document.getElementById('t_price');
					if (parseFloat(oldPrice) < parseFloat(that.SocketMsg.PRICE)) {
						// 涨
						price.style.color = '#f96f47';
					}
					if (parseFloat(oldPrice) > parseFloat(that.SocketMsg.PRICE)) {
						// 跌
						price.style.color = '#00bf89';
					}

				});
				uni.onSocketClose(function(res) {
					console.log('WebSocket 已关闭！');
				});
			},
			ClearChart() {
				// console.log("清除图表");
				var divKLine = document.getElementById('lightweight');
				if (null == divKLine || typeof divKLine == "undefined") {
					return;
				}
				while (divKLine.hasChildNodes()) {
					divKLine.removeChild(divKLine.lastChild);
				}
			},
			mergeTickToBar(price) {
				if (currentBar.open === null) {
					currentBar.open = price;
					currentBar.high = price;
					currentBar.low = price;
					currentBar.close = price;
				} else {
					currentBar.close = price;
					currentBar.high = Math.max(currentBar.high, price);
					currentBar.low = Math.min(currentBar.low, price);
				}
				candleSeries.update(currentBar);
			},


			// 跳转持仓
			ClickCc() {
				uni.navigateTo({
					url: '../transaction-records/transaction-now'
				})
			},
			// 选择项目
			SelectBuyItem(item) {
				this.OrderItem = item;
				// 移除样式
				var itemList = document.getElementsByClassName("page-item");
				for (var i = 0; i < itemList.length; i++) {
					itemList[i].classList.remove("page-item-select");
				}
				// 修改样式 选中值
				switch (item) {
					case 1:
						this.OrderCycle = 30;
						document.getElementsByClassName("page-item-1")[0].classList.add("page-item-select");
						break;
					case 2:
						this.OrderCycle = 60;
						document.getElementsByClassName("page-item-2")[0].classList.add("page-item-select");
						break;
					case 3:
						this.OrderCycle = 180;
						document.getElementsByClassName("page-item-3")[0].classList.add("page-item-select");
						break;
					default:
						//没有选中
						break;
				}
				// 触发预期收益计算
				this.countYqsy();
			},
			// 选择金额
			SelectBuyAccount(account) {
				this.OrderAmount = account;
				// 移除样式
				var itemList = document.getElementsByClassName("page-amount");
				for (var i = 0; i < itemList.length; i++) {
					itemList[i].classList.remove("page-amount-select");
				}
				// 修改样式
				switch (account) {
					case 10:
						document.getElementsByClassName("page-amount-1")[0].classList.add("page-amount-select");
						break;
					case 50:
						document.getElementsByClassName("page-amount-2")[0].classList.add("page-amount-select");
						break;
					case 100:
						document.getElementsByClassName("page-amount-3")[0].classList.add("page-amount-select");
						break;
					case 500:
						document.getElementsByClassName("page-amount-4")[0].classList.add("page-amount-select");
						break;
					case 1000:
						document.getElementsByClassName("page-amount-5")[0].classList.add("page-amount-select");
						break;
					case 5000:
						document.getElementsByClassName("page-amount-6")[0].classList.add("page-amount-select");
						break;
					default:
						//没有选中
						break;
				}

				// 触发预期收益计算
				this.countYqsy();
			},
			countYqsy() {
				// 收益率
				var rat = 0.88;
				if (this.OrderItem == 1) {
					rat = 0.88;
				}
				if (this.OrderItem == 2) {
					rat = 0.9;
				}
				if (this.OrderItem == 3) {
					rat = 0.92;
				}
				// 收益金额 = 投资额 + (投资额 * 收益率);
				this.OrderExpectEarnings = MathUtil.add(this.OrderAmount, MathUtil.mul(this.OrderAmount, rat));
			},
			// 下单
			SubmitBuy() {
				// 校验
				if (this.UserData.userMoney < this.OrderAmount) {
					// 图标 0 成功, 1 警告, 2 错误, 3 信息, 4 疑问, 5 斜杠
					this.vusui.msg(
						'账户余额不足,请充值!', {
							icon: 2,
							shade: 0.6,
						}, () => {
							// console.log("回调");
						});
					return;
				};
				// 校验当前价
				console.log(this.OrderCoinPrice)
				if (typeof this.OrderCoinPrice == "undefined" || this.OrderCoinPrice == null || this.OrderCoinPrice == 0 ||
					this.OrderCoinPrice == "") {
					return;
				}
				// 提交
				var data = {
					"orderType": 1,
					"orderStatus": 0,
					"skuId": this.OrderItem,
					"skuCode": this.Name,
					"skuName": this.PairName,
					"skuQty": 1,
					"skuPrice": this.OrderAmount,
					"orderAmount": this.OrderAmount,
					"orderCharge": 0,
					"orderCycle": this.OrderCycle,
					"investAmount": this.OrderAmount,
					"investType": this.OrderDirection,
					"expectedReturn": this.OrderExpectEarnings,
					"guaranteedAmount": this.OrderGuarantee,
					"inPoint": this.OrderCoinPrice,
				};

				https.submitOrder(data).then((res) => {
					// orderloading.close();
					console.clear();
					console.log(res);
					if (res != null) {
						// 提示用户
						this.vusui.msg(
							'下单成功!', {
								icon: 0,
								shade: 0.6,
							}, () => {
								// 关闭当前弹窗
								this.vusui.close('page');
								// 重新获取用户信息 余额
								// todo
								// 跳转页面
								uni.navigateTo({
									url: '../transaction-records/transaction-now'
								})
							});
					}
				});
			},
			// 购买弹窗
			ClickBuy(type) {
				//根据type确定方向
				if (type == 1) {
					this.OrderDirection = 1;
					this.OrderDirectionName = "买涨";
				}
				if (type == 2) {
					this.OrderDirection = 2;
					this.OrderDirectionName = "买跌";
				}
				// slot(插槽) 模式
				this.vusui.page({
					title: '订单确认'
				});
			},
		}
	}
</script>
