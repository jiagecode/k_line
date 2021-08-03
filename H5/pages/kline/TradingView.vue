<template>
	<div class='divchart' style='background-color:#ffffff;'>
<!-- 		<uni-row class="uni-row-top">
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
		</uni-row> -->
		
		<!-- top信息 -->
		<view class="uni-row-top d-flex" style="height: 130rpx;">
			<!-- 实时价格 -->
			<view id="t_price" class="d-flex flex-1 j-center a-center font-lgg">{{SocketMsg.PRICE}}</view>
			<!-- 当前行情 -->
			<view class="flex-1">
				<view class="d-flex flex-1" style="margin-top: 4rpx;">
					<view class="flex-1 d-block">
						<text class="d-flex a-center j-center font-sm" style="line-height: 22rpx;">最高</text>
						<text id="t_highday" class="d-flex a-center j-center font" style="line-height: none;">{{SocketMsg.LOWDAY}}</text>
					</view>
					<view class="flex-1 d-block">
						<text class="d-flex a-center j-center font-sm" style="line-height: 22rpx;">开盘</text>
						<text id="t_openday" class="d-flex a-center j-center font">{{SocketMsg.OPENDAY}}</text>
					</view>
				</view>
				<view class="d-flex flex-1">
					<view class="flex-1">
						<text class="d-flex a-center j-center font-sm" style="line-height: 22rpx;">最低</text>
						<view id="t_lowday" class="d-flex a-center j-center font">{{SocketMsg.LOWDAY}}</view>
					</view>
					<view class="flex-1 d-flex a-center j-center" @tap="addSelect">
						<!-- <text class="d-flex a-center j-center font-sm" style="line-height: 22rpx;">自选</text> -->
						<image style="width: 65rpx; height: 65rpx;" src="/static/zxuan.png"><view class="d-flex flex-column"><text class="font-sm" style="line-height: 22rpx; margin-bottom: 6rpx;">自</text><text class="j-center font-sm" style="line-height: 22rpx;">选</text></view></image>
					</view>
				</view>
			</view>
		</view>
		<uni-row>
			<uni-col :span="6">
				<button class="mini-btn btn-period period-Line" type="default" size="mini"
					style="background-color: #dedede;" @click="ChangeKLinePeriod('Line')">实时</button>
			</uni-col>
			<uni-col :span="6">
				<button class="mini-btn btn-period period-Min" type="default" size="mini"
					@click="ChangeKLinePeriod('Min')">分钟</button>
			</uni-col>
			<uni-col :span="6">
				<button class="mini-btn btn-period period-1H" type="default" size="mini"
					@click="ChangeKLinePeriod('1H')">小时</button>
			</uni-col>
			<uni-col :span="6">
				<button class="mini-btn btn-period period-1D" type="default" size="mini"
					@click="ChangeKLinePeriod('1D')">日线</button>
			</uni-col>
		</uni-row>
		
		<div id="lightweight"></div>

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
				// 商品代码
				coinCode:'BTC',
				// 商品名称
				coinName:'BTC',
				sysInfo: {},
				// 秒、分、小时、日线，对象
				areaSeries: null,
				// 交易量对象
				volumeSeries: null,
				chart: null,
				ChartWidth: 350,
				ChartHeight: 500,
				// 图表小数位数
				decimalDigits: 2,
				// 面积图数据 600
				lineData: [],
				// 交易量 600
				volumeData: [],
				// 交易量历史数据
				lastVolume: 0,
				// 分钟数据 60
				minData: [],
				// 小时数据 24
				hourData: [],
				// 天数据 30
				dayData: [],
				// 标记
				activityPeriod: '',
				currentBar: {
					open: null,
					high: null,
					low: null,
					close: null,
					time: null,
				},
				areaCurrentBar: {
					value: null,
					time: null,
				},
				// socket连接对象
				// ccStreamer: null,
				// socket连接状态
				SocketStatus: false,
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
		created() {},
		onLoad() {
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
			
			// 订阅币种切换事件
			uni.$on("ChangeSymbol", (rel) => {
				// console.log(rel);
				// 1、FIXME接收
				// 2、数据保存到
				if(rel.DISPLAY === undefined){
					this.SocketMsg = {
						PRICE : rel.info.PRICE.replace('$ ', '').replace(',', ''),
						LOWDAY : rel.info.HIGH24HOUR.replace('$ ', '').replace(',', ''),
						OPENDAY : rel.info.OPEN24HOUR.replace('$ ', '').replace(',', ''),
						LOWDAY : rel.info.LOW24HOUR.replace('$ ', '').replace(',', '')
					} 
					this.coinCode = new String(rel.name).toUpperCase();
					this.coinName = new String(rel.name);
				} else {
					this.SocketMsg = {
						PRICE : rel.DISPLAY.USD.PRICE.replace('$ ', '').replace(',', ''),
						LOWDAY : rel.DISPLAY.USD.HIGH24HOUR.replace('$ ', '').replace(',', ''),
						OPENDAY : rel.DISPLAY.USD.OPEN24HOUR.replace('$ ', '').replace(',', ''),
						LOWDAY : rel.DISPLAY.USD.LOW24HOUR.replace('$ ', '').replace(',', '')
					}
					this.coinCode = new String(rel.CoinInfo.Name).toUpperCase();
					this.coinName = new String(rel.CoinInfo.Name);
				}
				// 清空
				uni.closeSocket();
				this.dayData = [];
				this.hourData = [];
				this.minData = [];
				this.areaSeries = null;
				this.volumeSeries = null;
				this.chart = null;
				// 重新加载
				
			})

		},
		onShow() {
			uni.setNavigationBarTitle({
				title: this.coinName + '/USD'
			});
			document.title = '币安秒合约';
			
			uni.closeSocket();
			this.dayData = [];
			this.hourData = [];
			this.minData = [];
			this.areaSeries = null;
			this.volumeSeries = null;
			this.chart = null;
			
			// 查询日线数据
			https.klineDay({
				coinCode: this.coinCode
			}).then((res) => {
				for (var i = 0; i < res.length; i++) {
					var item = JSON.parse(res[i]);
					if(item === null)
						break;
					this.dayData.push({
						open: item.openPrice,
						high: item.maxPrice,
						low: item.minPrice,
						close: item.close,
						time: parseFloat(item.timeStamp),
					});
				}
			});
			// 查询小时线数据 
			https.klineHour({
				coinCode: this.coinCode
			}).then((res) => {
				// console.log(res);
				for (var i = 0; i < res.length; i++) {
					var item = JSON.parse(res[i]);
					if(item === null)
						break;
					this.hourData.push({
						open: item.openPrice,
						high: item.maxPrice,
						low: item.minPrice,
						close: item.close,
						time: parseFloat(item.timeStamp),
					});
				}
			});

			// 查询分钟线数据 1小时60条
			https.klineMin({
				coinCode: this.coinCode
			}).then((res) => {
				for (var i = 0; i < res.length; i++) {
					var item = JSON.parse(res[i]);
					if(item === null)
						break;
					this.minData.push({
						open: item.openPrice,
						high: item.maxPrice,
						low: item.minPrice,
						close: item.close,
						time: parseFloat(item.timeStamp),
					});
				}
			});

			// 重新计算页面元素高度
			var width = this.sysInfo.windowWidth;
			var height = this.sysInfo.windowHeight;
			this.ChartWidth = width;
			this.ChartHeight = height - 156;
			// this.ChartHeight = height - 160;

			this.$nextTick(() => {
				// 行情传值
				var container = document.getElementById("lightweight");
				container.style.position = 'relative';
				this.chart = createChart(
					container, {
						width: this.ChartWidth,
						height: this.ChartHeight,
						rightPriceScale: {
							scaleMargins: {
								top: 0.2,
								bottom: 0.2,
							},
							borderVisible: true,
						},
						timeScale: {
							rightOffset: 2,
							borderVisible: false,
						},
						crosshair: {
							mode: 0,
						},
						localization: {
							locale: 'zh-cn',
							dateFormat: 'yyyy-MM-dd',
						},
						layout: {
							backgroundColor: '#FFFFFF',
							lineColor: '#2B2B43',
							textColor: '#191919',
						},
						watermark: {
							color: 'rgba(0, 0, 0, 0)',
						},
						grid: {
							vertLines: {
								visible: false,
							},
							horzLines: {
								color: '#f0f3fa',
							},
						},
						priceScale: {
							position: "right",
							mode: 0,
							borderVisible: true,
						}
					}
				);

				// 获取数据然后初始化图表
				this.queryMilsData();
			});
		},
		onHide() {
			uni.closeSocket();
			this.SocketStatus = false;
			this.ClearChart();
		},

		onUnload() {
			uni.closeSocket();
			this.SocketStatus = false;
			// this.ClearChart();
		},
		methods: {
			// 切换图表的操作
			ChangeKLinePeriod(period) {
				// 标记
				this.activityPeriod = period;

				// 关闭socket连接
				// uni.closeSocket();
				// 按钮选中样式
				var periodList = document.getElementsByClassName("btn-period");
				for (var i = 0; i < periodList.length; i++) {
					periodList[i].style.backgroundColor = "#f8f8f8";
				}
				document.getElementsByClassName("period-" + period)[0].style.backgroundColor = "#dedede";

				// 删除当前图表
				if (this.areaSeries) {
					this.chart.removeSeries(this.areaSeries);
					this.areaSeries = null;
				}
				// 删除当前图表
				if (this.volumeSeries) {
					this.chart.removeSeries(this.volumeSeries);
					this.volumeSeries = null;
				}
				// 新增选中图表
				if (period === "Line") {
					// 面积图
					this.areaSeries = this.chart.addAreaSeries();
					// 交易量图
					this.volumeSeries = this.chart.addHistogramSeries({
						color: '#26a69a',
						priceFormat: {
							type: 'volume',
						},
						priceScaleId: '',
						scaleMargins: {
							top: 0.8,
							bottom: 0,
						},
					});
				} else {
					this.areaSeries = this.chart.addCandlestickSeries();

				}
				// 设置样式
				this.areaSeries.applyOptions({
					topColor: 'rgba(33, 150, 243, 0.56)',
					bottomColor: 'rgba(33, 150, 243, 0.04)',
					lineColor: 'rgba(33, 150, 243, 1)',
					lineWidth: 1,
					priceFormat: {
						type: "custom",
						precision: 4,
						minMove: 0.2,
						formatter: function(value) {
							return MathUtil.getFloat(value, 2);
						}
					}
				});

				if (period === "Line") {
					// 自定义时间刻度
					this.chart.applyOptions({
						timeScale: {
							borderVisible: true,
							visible: true,
							timeVisible: true,
							secondsVisible: true,
							tickMarkFormatter: (time, tickMarkType, locale) => {
								return moment(new Date(time * 1000)).format("HH:mm:ss");
								// return '';
							},
						}
					});
					this.areaSeries.setData(this.lineData);
					this.volumeSeries.setData(this.volumeData);
					if(!this.SocketStatus){
						// 开启socket获取数据
						this.initSocketData();
						this.SocketStatus = true;
					}
				}
				if (period === "Min") {
					// 自定义时间刻度
					this.chart.applyOptions({
						timeScale: {
							borderVisible: true,
							visible: true,
							timeVisible: true,
							secondsVisible: true,
							tickMarkFormatter: (time, tickMarkType, locale) => {
								return moment(new Date(time * 1000)).format("HH:mm");
							},
						}
					});
					this.areaSeries.setData(this.minData);
				}
				if (period === "1H") {
					// 自定义时间刻度
					this.chart.applyOptions({
						timeScale: {
							borderVisible: true,
							visible: true,
							timeVisible: true,
							secondsVisible: true,
							tickMarkFormatter: (time, tickMarkType, locale) => {
								return moment(new Date(time * 1000)).format("HH");
							},
						}
					});
					this.areaSeries.setData(this.hourData);
				}
				if (period === "1D") {
					// 自定义时间刻度
					this.chart.applyOptions({
						timeScale: {
							borderVisible: true,
							visible: true,
							timeVisible: true,
							secondsVisible: true,
							tickMarkFormatter: (time, tickMarkType, locale) => {
								return moment(new Date(time * 1000)).format("MM-DD");
							},
						}
					});
					
					this.areaSeries.setData(this.dayData);
				}

				// 触发事件 显示回到最新按钮
				this.chart.timeScale().scrollToPosition(-20, false);
				// 回到最新按钮
				this.createRealTimeButton();
				// 回到最新数据
				this.chart.timeScale().scrollToRealTime();
			},
			ClearChart() {
				var divKLine = document.getElementById('lightweight');
				if (null == divKLine || typeof divKLine == "undefined") {
					return;
				}
				while (divKLine.hasChildNodes()) {
					divKLine.removeChild(divKLine.lastChild);
				}
			},
			// 创建回到最新按钮
			createRealTimeButton() {
				var button = document.createElement('div');
				button.className = 'go-to-realtime-button';
				button.style.left = (this.ChartWidth - 27 - 60) + 'px';
				button.style.top = (this.ChartHeight - 27 - 30) + 'px';
				button.style.color = '#4c525e';
				button.innerHTML =
					'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 14" width="14" height="14"><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-width="2" d="M6.5 1.5l5 5.5-5 5.5M3 4l2.5 3L3 10"></path></svg>';
				document.getElementById("lightweight").appendChild(button);

				var timeScale = this.chart.timeScale();
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
			},
			// 查询实时数据 秒
			queryMilsData() {

				// 查询历史秒线数据 1小时3600条
				https.klineMils({
					coinCode: this.coinCode
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
						if(item === null)
							break;
						this.lineData.push({
							value: item.nowPrice,
							time: parseFloat(item.timeStamp),
						});
						// 判断当次交易量涨跌
						var nowVolume = MathUtil.getFloat(item.volumeFrom, 4);
						this.volumeData.push({
							time: parseFloat(item.timeStamp),
							value: nowVolume,
							color: (nowVolume - this.lastVolume) >= 0 ? 'rgba(255,82,82, 0.8)':'rgba(0, 150, 136, 0.8)',
						});
						this.lastVolume = nowVolume;
					}

					// 获取完数据再初始化图表
					this.ChangeKLinePeriod('Line');
				});
			},
			// socket
			initSocketData() {
				var that = this;
				uni.connectSocket({
					url: https.getBaseSocketUrl()+'/websocket/current/s/'+this.coinCode+'/' + that.UserData.userId
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
						PRICE: MathUtil.getFloat(msg.nowPrice, 4),
						OPENDAY: MathUtil.getFloat(msg.openPrice, 2),
						LOWDAY: MathUtil.getFloat(msg.minPrice, 2),
						HIGHDAY: MathUtil.getFloat(msg.maxPrice, 2),
					};
					that.SocketMsg = msgData;

					// 更新数据
					// var currentBar = {
					// 	open: msg.openPrice,
					// 	high: msg.maxPrice,
					// 	low: msg.minPrice,
					// 	close: msg.close,
					// 	time: parseInt(msg.timeStamp),
					// 	// time: msg.timeStamp,
					// 	// time: moment.unix(parseInt(msg.timeStamp)).format('YYYY-MM-DD'),
					// };
					if (that.areaSeries && that.activityPeriod === 'Line') {
						var areaCurrentBar = {
							time: parseFloat(msg.timeStamp),
							value: MathUtil.getFloat(msg.nowPrice, 4),
						};
						// 判断当次交易量涨跌
						var nowVolume = MathUtil.getFloat(msg.volumeFrom, 4);
						var volumeItem = {
							time: parseFloat(msg.timeStamp),
							value: nowVolume,
							color: (nowVolume - that.lastVolume) >= 0 ? 'rgba(255,82,82, 0.8)':'rgba(0, 150, 136, 0.8)',
						};
						that.lastVolume = nowVolume;
						// 更新面积图
						that.areaSeries.update(areaCurrentBar);
						// 更新交易量
						that.volumeSeries.update(volumeItem);
					}
					// 现价
					that.OrderCoinPrice = MathUtil.getFloat(msg.nowPrice, 4);

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
			addSelect() {
				let data = {
					"skuCode": this.coinCode
				}
				https.saveSkuCusInfo(data).then((res) => {
					console.log(res);
					let msg = res === 1 ? '添加成功!' : res === -1 ? '添加失败!' : '取消成功!';
					// 提示用户
					this.vusui.msg(
						msg, {
							icon: 0,
							shade: 0.6,
						}, () => {
							// 关闭当前弹窗
							this.vusui.close('page');
							// 重新获取用户信息 余额
							// todo
							// 跳转页面
							// uni.navigateTo({
							// 	url: '../transaction-records/transaction-now'
							// })
					});
				});
			},
			// 跳转持仓
			ClickCc() {
				uni.redirectTo({
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
					"skuCode": this.coinCode,
					"skuName": this.coinName,
					"skuQty": 1,
					"skuPrice": this.OrderCoinPrice,
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
					if (res != null) {
						if(res.resultCode === "-1" || res.resultCode === "-2" || res.resultCode === "-3" || res.resultCode === "-4"){
							this.vusui.msg(res.resultDesc, { icon: 2, shade: 0.6, }, () => { });
						}else if(res.resultCode === "1"){
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
						}else{
							this.vusui.msg("下单失败,未知错误!", { icon: 2, shade: 0.6, }, () => { });
						}
						
						
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
