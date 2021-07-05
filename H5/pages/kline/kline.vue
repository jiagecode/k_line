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

		<!--  #ifdef  H5  -->
		<div>
			<div class='kline' id="kline" ref='kline'></div>
		</div>
		<!--  #endif -->

		<!--  #ifndef  H5 -->
		<view>
			<canvas id="kline2" canvas-id='kline2' class='kline2'
				v-bind:style="{width: ChartWidth+'px', height: ChartHeight+'px'}" @touchstart="KLineTouchStart"
				@touchmove='KLineTouchMove' @touchend='KLineTouchEnd'></canvas>
		</view>
		<!--  #endif -->
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

	/* 弹出框内容css */
	.page-top-line {
		margin: 0 auto;
		margin-top: 20upx;
		width: 100%;
		height: 4upx;
		background-color: #000000;
	}

	.page-top-dqsj {
		height: 60upx;
		text-align: left;
		line-height: 60upx;
		color: #8c8b90;
	}

	.uni-row-page {
		margin-top: 20upx;
		margin-bottom: 20upx;
		height: 150upx;
	}

	.uni-col-page {
		height: 150upx;
	}

	.light-page-t {
		height: 40upx;
		line-height: 40upx;
		font-size: 30upx;
		text-align: center;
		color: #8c8b90;
		background-color: #424448;
	}

	.light-page-c {
		height: 70upx;
		line-height: 70upx;
		font-size: 26upx;
		text-align: center;
		color: #ffc71e;
		background-color: #424448;
	}

	.light-page-d {
		height: 40upx;
		line-height: 40upx;
		font-size: 30upx;
		font-weight: bold;
		text-align: center;
		color: #f6676c;
		background-color: #424448;
	}

	.row-page-item {
		padding-left: 10upx !important;
		padding-right: 10upx !important;
	}

	.page-item-select {
		border-radius: 4upx;
		border: 4upx solid #ffc71e;
	}

	.uni-row-page1 {
		height: 80upx;
	}

	.uni-col-page1 {
		height: 80upx;
	}

	.light-page1-t {
		height: 60upx;
		line-height: 60upx;
		font-size: 26upx;
		text-align: center;
		color: #ffc71e;
		background-color: #434549;
	}

	.row-page1-item {
		padding-top: 10upx !important;
		padding-bottom: 10upx !important;
		padding-left: 10upx !important;
		padding-right: 10upx !important;
	}

	.page-amount-select {
		border-radius: 4upx;
		border: 4upx solid #ffc71e;
	}

	.page-left-ye {
		display: contents;
		float: left;
		width: 50%;
		height: 50upx;
		line-height: 50upx;
		color: #8c8b90;
	}

	.page-left-sxf {
		float: right;
		width: 50%;
		height: 50upx;
		line-height: 50upx;
		color: #8c8b90;
	}

	.page-c-line {
		margin: 0 auto;
		width: 100%;
		height: 4upx;
		background-color: #434347;
	}

	.uni-row-page2 {
		height: 120upx;
	}

	.uni-col-page2 {
		height: 60upx;
	}

	.light-page2-t {
		height: 60upx;
		line-height: 60upx;
		font-weight: bold;
		font-size: 26upx;
		text-align: center;
		color: #8c8b90;
	}

	.light-page2-d {
		height: 60upx;
		line-height: 60upx;
		font-size: 26upx;
		text-align: center;
		color: #4e4d52;
	}

	.row-page2-item {
		padding-top: 10upx !important;
		padding-bottom: 10upx !important;
		padding-left: 10upx !important;
		padding-right: 10upx !important;
	}

	.page-2-name {
		font-size: 30upx;
		color: #FFFFFF;
	}

	.page-2-fx {
		font-size: 30upx;
		color: #8c8b90;
	}

	.page-2-fx-red {
		color: #f6676c;
	}

	.page-2-fx-green {
		color: #36d47e;
	}

	.page-2-xj {
		font-size: 30upx;
		color: #8c8b90;
	}

	.page-2-xj-red {
		color: #f6676c;
	}

	.page-2-xj-green {
		color: #36d47e;
	}


	.page-2-je {
		font-size: 30upx;
		color: #ffc71d;
	}

	.page-btn-submit {
		background-color: #ffc71d;
	}

	.page-bottom-text {
		display: block;
		text-align: center;
		height: 50upx;
		line-height: 50upx;
		font-size: 26upx;
		color: #ffc71d;
	}

	.page-left-yqsy {
		width: 50%;
		display: block;
		float: left;
		text-align: right;
		padding-right: 10upx;
	}

	.page-left-bdje {
		width: 50%;
		display: block;
		float: right;
		text-align: left;
		padding-left: 10upx;

	}

	/* 弹出框内容css */




	.uni-row-top {
		height: 60px;
	}

	.uni-col-top {
		height: 60px;
	}

	.light-top-t {
		height: 30px;
		line-height: 36px;
		font-size: 17px;
		text-align: center;
		color: #555555;
		background-color: #f8f8f8;
	}

	.light-top-d {
		height: 30px;
		line-height: 24px;
		font-size: 15px;
		text-align: center;
		color: rgb(255, 121, 117);
		background-color: #f8f8f8;
	}

	.topText {
		height: 60px;
		line-height: 60px;
		font-size: 20px;
		text-align: center;
		color: rgb(255, 121, 117);
		background-color: #f8f8f8;
	}

	.uni-row-bot {
		height: 70px;
	}

	.uni-col-bot {
		height: 70px;
	}

	.light-bot-t {
		height: 30px;
		line-height: 40px;
		font-size: 30upx;
		font-weight: bold;
		text-align: center;
		color: #4e4d52;
		background-color: #e5e9f2;
	}

	.light-bot-d {
		height: 70px;
		line-height: 70px;
		font-size: 35upx;
		font-weight: bold;
		text-align: center;
		color: #4e4d52;
		background-color: #e5e9f2;
	}

	.bot-cc {
		color: #e5e9f2;
		background-color: #4e4d52;
	}

	.bot-mz {
		background-color: #ff7975;
	}

	.bot-md {
		position: relative;
		background-color: #36d47e;
	}

	.mini-btn {
		width: 100%;
		height: 100%;
		top: 0;
		left: 0;
		border: 0px;
		border-right: 1px;
		border-radius: 0px;
		overflow: inherit;
	}

	.mini-btn:after {
		border-radius: 0px;
		border-right: 1px;
		border: 0px;
		overflow: inherit;
	}

	/* 弹出层 */
	.uni-banner {
		width: 70%;
		position: fixed;
		left: 15%;
		margin-top: -400upx;
		z-index: 99;
	}

	.uni-mask {
		z-index: 1;
		background: rgba(0, 0, 0, 0.6);
	}
</style>

<script>
	import moment from 'moment'
	import MathUtil from '@/utils/MathUtil.js'
	import https from '../../api/api.js'

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//	HQChart单画布模式,K线/分时共享使用一个画布
	//
	//
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////


	// #ifdef H5	
	import HQChart from '@/uni_modules/hqchart2/umychart.uniapp.h5.js'
	// HQChart.JSChart.SetDomain("https://api.coingecko.com/api/v3/coins/bitcoin");
	// HQChart.JSComplier.SetDomain("https://api.coingecko.com/api/v3/coins/bitcoin");
	//禁用日志
	// HQChart.Chart.JSConsole.Chart.Log=()=>{ }
	// HQChart.Chart.JSConsole.Complier.Log=()=>{ }
	// #endif


	// #ifndef H5
	import {
		JSCommon
	} from '@/uni_modules/hqchart2/umychart.wechat.3.0.js'
	import {
		JSCommonHQStyle
	} from '@/uni_modules/hqchart2/umychart.style.wechat.js'
	import {
		JSConsole
	} from '@/uni_modules/hqchart2/umychart.console.wechat.js'
	// 交易时间 0=闭市 1=盘前 2=盘中 3=盘后
	// 设置当前已开市
	// JSCommon.MARKET_SUFFIX_NAME.GetMarketStatus = function (symbol) { return 2; }

	// JSCommon.JSChart.SetDomain("https://api.coingecko.com/api/v3/coins/bitcoin");
	// JSCommonComplier.JSComplier.SetDomain("https://api.coingecko.com/api/v3/coins/bitcoin")

	//禁用日志
	// JSConsole.Complier.Log=()=>{ };
	// JSConsole.Chart.Log=()=>{ };

	// #endif

	var API_KEY = "2f5295de0ff822eb314074bbbed58c54154a0cf14a1cc9623b5b577046197d9f";

	// 系统信息
	var sysInfo = null;
	// socket连接对象
	var ccStreamer = null;

	function DefaultData() {}

	DefaultData.GetKLineOption = function() {
		let data = {
			Type: '历史K线图',

			Windows: //窗口指标
				[{
						Index: "MA",
						Modify: false,
						Change: false
					},
					{
						Index: "VOL",
						Modify: false,
						Change: false
					}
				],

			IsCorssOnlyDrawKLine: true,
			CorssCursorTouchEnd: true,
			IsClickShowCorssCursor: true,
			CorssCursorInfo: {
				Left: 2,
				Right: 2,
				Bottom: 1,
				IsShowCorss: true
			}, //十字光标刻度设置
			//IsFullDraw:true,

			IsAutoUpdate: true, //是自动更新数据
			AutoUpdateFrequency: 20000, //数据更新频率

			Border: //边框
			{
				Left: 1,
				Right: 1, //右边间距
				Top: 25,
				Bottom: 25,
			},

			KLine: {
				DragMode: 1,
				Right: 0, //复权 0 不复权 1 前复权 2 后复权 不是股票的品种设置0
				Period: 4, //周期: 0 日线 1 周线 2 月线 3 年线 
				PageSize: 30,
				IsShowTooltip: false,
				DrawType: 0,
				DataWidth: 10
			},

			ExtendChart: [{
					Name: 'KLineTooltip'
				}, //开启手机端tooltip
			],

			Frame: //子框架设置
				[{
						SplitCount: 3,
						Custom: [{
							Type: 0,
							Position: 'right'
						}]
					},
					{
						SplitCount: 2,
					},
					{
						SplitCount: 3
					},
				],

		};

		return data;
	}

	DefaultData.GetMinuteOption = function() {
		var option = {
			Type: '分钟走势图', //创建图形类型

			Windows: //窗口指标
				[

				],

			IsAutoUpdate: true, //是自动更新数据
			DayCount: 1, //1 最新交易日数据 >1 多日走势图
			IsShowRightMenu: false, //是否显示右键菜单
			CorssCursorTouchEnd: true,
			//IsFullDraw:true,

			MinuteLine: {
				//IsDrawAreaPrice:false,      //是否画价格面积图
			},

			Border: //边框
			{
				Left: 1, //左边间距
				Right: 1, //右边间距
				Top: 20,
				Bottom: 20
			},

			Frame: //子框架设置
				[{
						SplitCount: 3
					},
					{
						SplitCount: 2,
						Height: 0
					},
					{
						SplitCount: 3
					},
				],

			ExtendChart: //扩展图形
				[{
						Name: 'MinuteTooltip'
					} //手机端tooltip
				],
		};

		return option;
	}


	//周期枚举
	var KLINE_PERIOD_ID = {
		KLINE_DAY_ID: 0,
		KLINE_WEEK_ID: 1,
		KLINE_MONTH_ID: 2,
		KLINE_YEAR_ID: 3,

		KLINE_MINUTE_ID: 4,
		KLINE_5MINUTE_ID: 5,
		KLINE_15MINUTE_ID: 6,
		KLINE_30MINUTE_ID: 7,
		KLINE_60MINUTE_ID: 8
	}

	//周期枚举
	var MINUTE_PERIOD_ID = {
		MINUTE_ID: 1,
		MINUTE_2DAY_ID: 2,
		MINUTE_3DAY_ID: 3,
		MINUTE_4DAY_ID: 4,
		MINUTE_5DAY_ID: 5,
	}

	var g_JSChart = null;

	export default {
		data() {
			let data = {
				// PairName:Bitcoin,Symbol:btc.BIT,Name:btc
				Symbol: 'btc.BIT',
				Name: 'btc',
				PairName: 'Bitcoin', //货币代码
				FloatPrecision: 2, //品种的小数位数
				ChartWidth: 350,
				ChartHeight: 500,
				KLine: {
					// 周期
					// 0=日线 1=周线 2=月线 3=年线 9=季线 21=双周 [40001-50000) 自定义日线
					// 4=1分钟 5=5分钟 6=15分钟 7=30分钟 8=60分钟 11=120分钟 12=240分钟 [20001-30000) 自定义分钟
					// 10=分笔线 （小程序不支持)
					// 30001-32000 自定义秒
					Period: 4,
					Right: 0
				},
				Minute: {
					Days: 1
				},

				MINUTE_PERIOD_ID: MINUTE_PERIOD_ID,
				KLINE_PERIOD_ID: KLINE_PERIOD_ID,
				TestData: '',

				// socket 对象
				SocketTask: null,
				timeOutObject: null,
				// socket 打开状态
				is_open_socket: false,
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

			};

			return data;
		},

		onLoad(obj) {
			var that = this;

			// 订阅币种切换事件
			uni.$on("ChangeSymbol", (rel) => {
				this.ChangeSymbol(rel);
			})

		},

		onReady() {},

		onShow() {
			var that = this;
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
			this.ChartHeight = height - 156;
			this.$nextTick(() => {
				this.ChangeSize();
				this.CreateKLineChart();
			})

			// top
			var top = document.getElementById('top');


			// this.connectionSocket();
			ccStreamer = new WebSocket('wss://streamer.cryptocompare.com/v2?api_key=' + API_KEY);
			ccStreamer.onopen = function onStreamOpen() {
				var subRequest = {
					"action": "SubAdd",
					"subs": ["2~Coinbase~" + that.Name.toUpperCase() + "~USD"]
				};
				ccStreamer.send(JSON.stringify(subRequest));
			}

			ccStreamer.onmessage = function onStreamMessage(message) {
				var message = event.data;
				var msgData = JSON.parse(message);
				var oldPrice = that.SocketMsg.PRICE;
				if (msgData.TYPE == '2') {
					if (typeof msgData.PRICE != 'undefined') {
						that.SocketMsg.PRICE = msgData.PRICE;
						that.OrderCoinPrice = msgData.PRICE;
						document.getElementById("t_price").innerText = msgData.PRICE;
						var xjItems = document.getElementsByClassName("page-2-xj");
						if (xjItems.length > 0) {
							xjItems[0].innerText = msgData.PRICE;
						}
					}
					if (typeof msgData.OPENHOUR != 'undefined') {
						that.SocketMsg.OPENHOUR = msgData.OPENHOUR;
					}
					if (typeof msgData.OPENDAY != 'undefined' && msgData.OPENDAY > 0) {
						that.SocketMsg.OPENDAY = msgData.OPENDAY;
						document.getElementById("t_openday").innerText = msgData.OPENDAY;
					}
					if (typeof msgData.OPEN24HOUR != 'undefined') {
						that.SocketMsg.OPEN24HOUR = msgData.OPEN24HOUR;
					}
					if (typeof msgData.LOWHOUR != 'undefined') {
						that.SocketMsg.LOWHOUR = msgData.LOWHOUR;
					}
					if (typeof msgData.LOWDAY != 'undefined' && msgData.LOWDAY > 0) {
						that.SocketMsg.LOWDAY = msgData.LOWDAY;
						document.getElementById("t_lowday").innerText = msgData.LOWDAY;
					}
					if (typeof msgData.LOW24HOUR != 'undefined') {
						that.SocketMsg.LOW24HOUR = msgData.LOW24HOUR;
					}
					if (typeof msgData.HIGHHOUR != 'undefined') {
						that.SocketMsg.HIGHHOUR = msgData.HIGHHOUR;
					}
					if (typeof msgData.HIGHDAY != 'undefined' && msgData.HIGHDAY > 0) {
						that.SocketMsg.HIGHDAY = msgData.HIGHDAY;
						document.getElementById("t_highday").innerText = msgData.HIGHDAY;
					}
					if (typeof msgData.HIGH24HOUR != 'undefined') {
						that.SocketMsg.HIGH24HOUR = msgData.HIGH24HOUR;
					}

					var price = document.getElementById('t_price');
					// 判断涨跌
					if (parseFloat(oldPrice) < parseFloat(that.SocketMsg.PRICE)) {
						// 涨
						price.style.color = '#ff7975';
					}
					if (parseFloat(oldPrice) > parseFloat(that.SocketMsg.PRICE)) {
						// 跌
						price.style.color = '#36d47e';
					}
				}
			}
			ccStreamer.onclose = function onStreamClose() {
				console.log("连接关闭");
			}
		},

		onHide() {
			ccStreamer.close();
			this.ClearChart();
		},

		onUnload() {
			ccStreamer.close();
			this.ClearChart();
		},

		methods: {
			// 跳转持仓
			ClickCc(){
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
				if(typeof this.OrderCoinPrice == "undefined" || this.OrderCoinPrice == null || this.OrderCoinPrice == 0 || this.OrderCoinPrice =="" ){
					return;
				}
				// 提交
				var data = {
					"orderType": 1,
					"orderStatus": 0,
					"skuId": this.OrderItem,
					"skuCode": this.Name,
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

			ClearChart() {
				if (g_JSChart) {
					g_JSChart.ChartDestory();
					g_JSChart = null;
				}

				// #ifdef H5
				var divKLine = document.getElementById('kline');
				while (divKLine.hasChildNodes()) {
					divKLine.removeChild(divKLine.lastChild);
				}
				// #endif
			},


			OnSize() {
				// #ifdef H5
				this.OnSize_h5();
				// #endif
			},

			OnSize_h5() {
				var chartHeight = this.ChartHeight;
				var chartWidth = this.ChartWidth;

				var kline = this.$refs.kline;
				kline.style.width = chartWidth + 'px';
				kline.style.height = chartHeight + 'px';
				if (g_JSChart) g_JSChart.OnSize();
			},

			CreateKLineChart_h5() //创建K线图
			{
				this.ClearChart();

				var blackStyle = HQChart.HQChartStyle.GetStyleConfig(HQChart.STYLE_TYPE_ID.WHITE_ID);
				HQChart.JSChart.SetStyle(blackStyle);
				this.$refs.kline.style.backgroundColor = blackStyle.BGColor; //div背景色设置白色

				var chart = HQChart.JSChart.Init(this.$refs.kline);
				var option = DefaultData.GetKLineOption();
				option.Symbol = this.Symbol;
				option.Right = this.KLine.Right;
				option.Period = this.KLine.Period;
				option.NetworkFilter = this.NetworkFilter;
				chart.SetOption(option);
				chart.AddEventCallback({
					event: HQChart.JSCHART_EVENT_ID.ON_TITLE_DRAW,
					callback: this.OnTitleDraw
				});

				g_JSChart = chart;
			},

			CreateKLineChart_app() {
				this.ClearChart();

				var element = new JSCommon.JSCanvasElement();
				// #ifdef APP-PLUS
				element.IsUniApp = true; //canvas需要指定下 是uniapp的app
				// #endif
				element.ID = 'kline2';
				element.Height = this.ChartHeight; //高度宽度需要手动绑定!!
				element.Width = this.ChartWidth;

				var blackStyle = JSCommonHQStyle.GetStyleConfig(JSCommonHQStyle.STYLE_TYPE_ID.WHITE_ID);
				//blackStyle.DefaultTextColor='rgb(255,0,255)';

				JSCommon.JSChart.SetStyle(blackStyle);

				var chart = JSCommon.JSChart.Init(element);
				var option = DefaultData.GetKLineOption();
				option.NetworkFilter = this.NetworkFilter;
				option.Symbol = this.Symbol;
				option.Right = this.KLine.Right;
				option.Period = this.KLine.Period;
				option.IsClickShowCorssCursor = true;
				option.IsFullDraw = true; //每次手势移动全屏重绘
				chart.SetOption(option);
				//chart.AddEventCallback( { event:JSCommon.JSCHART_EVENT_ID.RECV_INDEX_DATA, callback:this.OnRecvHistoryData });
				chart.AddEventCallback({
					event: JSCommon.JSCHART_EVENT_ID.ON_TITLE_DRAW,
					callback: this.OnTitleDraw
				});

				g_JSChart = chart;
			},

			CreateKLineChart() {
				// #ifdef H5
				this.CreateKLineChart_h5();
				// #endif

				// #ifndef H5
				this.CreateKLineChart_app();
				// #endif
			},

			IsKLineChart() {
				if (!g_JSChart) return false;
				var className = g_JSChart.JSChartContainer.ClassName;
				if (className == "KLineChartContainer" || className == "KLineChartHScreenContainer") return true;

				return false;
			},

			IsMinuteChart() {
				if (!g_JSChart) return false;
				var className = g_JSChart.JSChartContainer.ClassName;
				if (className == "MinuteChartContainer" || className == "MinuteChartHScreenContainer") return true;

				return false;
			},

			//K线周期切换
			ChangeKLinePeriod: function(period) {
				this.KLine.Period = period;
				var periodList = document.getElementsByClassName("btn-period");
				for (var i = 0; i < periodList.length; i++) {
					periodList[i].style.backgroundColor = "#f8f8f8";
				}
				var selectBtn = "";
				switch (period) {
					case 0:
						selectBtn = "btn-1d";
						break;
					case 1:
						selectBtn = "btn-1w";
						break;
					case 2:
						selectBtn = "btn-1m";
						break;
					case 3:
						selectBtn = "btn-1y";
						break;
					case 4:
						selectBtn = "btn-1m";
						break;
					case 5:
						selectBtn = "btn-5m";
						break;
					case 6:
						selectBtn = "btn-15m";
						break;
					case 7:
						selectBtn = "btn-30m";
						break;
					case 8:
						selectBtn = "btn-1h";
						break;
					default:
						break;
				}
				document.getElementsByClassName(selectBtn)[0].style.backgroundColor = "#dedede";
				if (this.IsKLineChart()) {
					g_JSChart.ChangePeriod(period);
				} else {
					this.CreateKLineChart();
				}
			},

			CreateMinuteChart_h5() //创建日线图
			{
				this.ClearChart();

				var blackStyle = HQChart.HQChartStyle.GetStyleConfig(HQChart.STYLE_TYPE_ID.WHITE_ID);
				HQChart.JSChart.SetStyle(blackStyle);
				this.$refs.kline.style.backgroundColor = blackStyle.BGColor; //div背景色设置黑色

				var chart = HQChart.JSChart.Init(this.$refs.kline);
				var option = DefaultData.GetMinuteOption();
				option.NetworkFilter = this.MinuteNetworkFilter;
				option.Symbol = this.Symbol;
				option.DayCount = this.Minute.Days;
				chart.SetOption(option);
				chart.AddEventCallback({
					event: HQChart.JSCHART_EVENT_ID.ON_TITLE_DRAW,
					callback: this.OnTitleDraw
				});

				g_JSChart = chart;
			},

			CreateMinuteChart_app() {
				this.ClearChart();

				var element = new JSCommon.JSCanvasElement();
				// #ifdef APP-PLUS
				element.IsUniApp = true; //canvas需要指定下 是uniapp的app
				// #endif
				element.ID = 'kline2';
				element.Height = this.ChartHeight; //高度宽度需要手动绑定!!
				element.Width = this.ChartWidth;

				//用黑色风格
				var blackStyle = JSCommonHQStyle.GetStyleConfig(JSCommonHQStyle.STYLE_TYPE_ID.WHITE_ID);
				JSCommon.JSChart.SetStyle(blackStyle);
				//var testttt=JSCommon.MARKET_SUFFIX_NAME.GetSHODecimal();
				//JSCommon.MARKET_SUFFIX_NAME.GetSHODecimal=(symbol)=>{ return 4; };	//设置期权代码

				var chart = JSCommon.JSChart.Init(element);
				var option = DefaultData.GetMinuteOption();
				option.NetworkFilter = this.MinuteNetworkFilter;
				option.Symbol = this.Symbol;
				option.DayCount = this.Minute.Days;
				option.IsFullDraw = true; //每次手势移动全屏重绘
				chart.SetOption(option);
				//chart.AddEventCallback({ event: JSCommon.JSCHART_EVENT_ID.ON_TITLE_DRAW, callback: this.OnMinuteTitleDraw });

				g_JSChart = chart;
			},

			CreateMinuteChart() {
				// #ifdef H5
				this.CreateMinuteChart_h5();
				// #endif

				// #ifndef H5
				this.CreateMinuteChart_app();
				// #endif
			},

			//走势图多日切换
			ChangeMinutePeriod: function(period) {
				this.Minute.Days = period;
				if (this.IsMinuteChart()) {
					g_JSChart.ChangeDayCount(period);
				} else {
					this.CreateMinuteChart(); //类型不对 删了重建
				}
			},

			//切换指标 windowId=窗口索引 name=指标名字
			ChangeKLineIndex: function(windowId, name) {
				if (!g_JSChart) return;
				if (this.IsKLineChart(g_JSChart)) g_JSChart.ChangeIndex(windowId, name);
			},

			//切换股票
			ChangeSymbol: function(item) {
				var symbol = item.symbol;
				var name = item.name;
				if (this.PairName == name) return;
				this.PairName = name;
				this.Symbol = symbol + '.BIT';
				this.Name = symbol;
				this.FloatPrecision = 2;
				this.OrderCoin = name;
				console.log("PairName:" + this.PairName + ",Symbol:" + this.Symbol + ",Name:" + this.Name);
				if (g_JSChart) g_JSChart.ChangeSymbol(symbol);
			},

			MinuteNetworkFilter: function(data, callback) {
				data.PreventDefault = true; //禁止hqchart调用内置api数据
				// console.log('[BitKLine::NetworkFilter] data', data);
				switch (data.Name) {
					case 'KLineChartContainer::ReqeustHistoryMinuteData': //分钟全量数据下载
						this.ReqeustHistoryMinuteData(data, callback, {
							PageSize: 100
						});
						break;
					case 'KLineChartContainer::RequestMinuteRealtimeData': //分钟实时数据更新
						this.RequestMinuteRealtimeData(data, callback);
						break;
				}
			},

			NetworkFilter: function(data, callback) {
				// if (data.Name=='APIScriptIndex::ExecuteScript') this.CustomIndex(data, callback);
				// console.log(`[HQChart:NetworkFilter] Name=${data.Name} Explain=${data.Explain}`);
				switch (data.Name) {
					case 'KLineChartContainer::ReqeustHistoryMinuteData': //分钟全量数据下载
						this.ReqeustHistoryMinuteData(data, callback, {
							PageSize: 50
						});
						break;
					case 'KLineChartContainer::RequestHistoryData': //日线全量数据下载
						this.RequestHistoryData(data, callback);
						break;
					case 'KLineChartContainer::RequestMinuteRealtimeData': //分钟实时数据更新
						this.RequestMinuteRealtimeData(data, callback);
						break;
					case 'KLineChartContainer::RequestRealtimeData': //日线实时数据更新
						this.RequestRealtimeData(data, callback);
						break;
				}
			},
			//第3方分钟线历史数据请求
			ReqeustHistoryMinuteData(data, callback, option) {
				data.PreventDefault = true; //禁止hqchart调用内置api数据
				console.log("查询1分钟全量");
				uni.request({
					// url: 'https://api.coingecko.com/api/v3/coins/bitcoin/ohlc?vs_currency=usd&days=1',
					// url: 'https://min-api.cryptocompare.com/data/v2/histominute?tryConversion=false&fsym=' + this.Name +
					// 	'&tsym=USD&limit=1440&api_key=' + API_KEY,
					url: 'https://min-api.cryptocompare.com/data/v2/histominute?fsym=' + this.Name +
						'&tsym=USD&limit=1440&api_key=' + API_KEY,
					method: 'get',
					success: (res) => {
						var resultData = {};
						var dataArr = res.data.Data.Data;

						var yClose = null;
						var klineData = [];
						for (var i in dataArr) {
							var item = dataArr[i];
							// var date = moment(parseInt(item.time)).format('YYYYMMDD');
							var date = parseInt(moment.unix(parseInt(item.time)).format('YYYYMMDD'));
							var time = parseInt(moment.unix(parseInt(item.time)).format('hhmm'));
							var open = parseFloat(item.open);
							var high = parseFloat(item.high);
							var low = parseFloat(item.low);
							var close = parseFloat(item.close);
							var vol = parseFloat(item.volumefrom);
							klineData.push([date, yClose, open, high, low, close, vol, null, time]);
							yClose = close;
						}
						var hqChartData = {
							code: 0,
							data: klineData,
							symbol: this.Symbol,
							name: this.PairName
						};
						callback(hqChartData);
					}
				});

			},

			//第3方日线历史数据请求
			RequestHistoryData(data, callback, option) //日线全量数据下载
			{
				data.PreventDefault = true; //禁止hqchart调用内置api数据
				console.log("查询日线全量");
				uni.request({
					url: 'https://min-api.cryptocompare.com/data/v2/histoday?fsym=' + this.Name +
						'&tsym=USD&limit=30&api_key=' + API_KEY,
					method: 'get',
					success: (res) => {
						var resultData = {};
						var dataArr = res.data.Data.Data;
						var yClose = null;
						var klineData = [];
						for (var i in dataArr) {
							var item = dataArr[i];
							var date = moment.unix(parseInt(item.time)).format('YYYYMMDD');
							var open = parseFloat(item.open);
							var high = parseFloat(item.high);
							var low = parseFloat(item.low);
							var close = parseFloat(item.close);
							var vol = parseFloat(item.volumefrom);
							klineData.push([date, yClose, open, high, low, close, vol, null]);
							yClose = close;
						}

						var hqChartData = {
							code: 0,
							data: klineData,
							symbol: this.Symbol,
							name: this.PairName
						};
						callback(hqChartData);
					}
				});

			},

			//第3方分钟线实时数据请求
			RequestMinuteRealtimeData(data, callback) {
				data.PreventDefault = true; //禁止hqchart调用内置api数据
				console.log("查询分钟实时数据");
				uni.request({
					url: 'https://min-api.cryptocompare.com/data/v2/histominute?fsym=' + this.Name +
						'&tsym=USD&limit=5&api_key=' + API_KEY,
					method: 'get',
					success: (res) => {
						var resultData = {};
						var dataArr = res.data.Data.Data;

						var yClose = null;
						var klineData = [];
						for (var i in dataArr) {
							var item = dataArr[i];
							// var date = moment(parseInt(item.time)).format('YYYYMMDD');
							var date = parseInt(moment.unix(parseInt(item.time)).format('YYYYMMDD'));
							var time = parseInt(moment.unix(parseInt(item.time)).format('hhmm'));
							var open = parseFloat(item.open);
							var high = parseFloat(item.high);
							var low = parseFloat(item.low);
							var close = parseFloat(item.close);
							var vol = parseFloat(item.volumefrom);
							klineData.push([date, yClose, open, high, low, close, vol, null, time]);
							yClose = close;
						}
						var hqChartData = {
							code: 0,
							data: klineData,
							symbol: this.Symbol,
							name: this.PairName,
							ver: 2.0
						};
						callback(hqChartData);
					}
				});

			},
			//第3方日线实时数据请求
			RequestRealtimeData(data, callback) {
				data.PreventDefault = true; //禁止hqchart调用内置api数据
				console.log("查询当天最新实时数据");
				uni.request({
					url: 'https://min-api.cryptocompare.com/data/v2/histoday?fsym=' + this.Name +
						'&tsym=USD&limit=2&api_key=' + API_KEY,
					method: 'get',
					success: (res) => {
						var resultData = {};
						var dataArr = res.data.Data.Data;
						var yClose = null;
						var klineData = [];
						for (var i in dataArr) {
							var item = dataArr[i];
							var date = moment.unix(parseInt(item.time)).format('YYYYMMDD');
							var open = parseFloat(item.open);
							var high = parseFloat(item.high);
							var low = parseFloat(item.low);
							var close = parseFloat(item.close);
							var vol = parseFloat(item.volumefrom);
							klineData.push([date, yClose, open, high, low, close, vol, null]);
							yClose = close;
						}

						var hqChartData = {
							code: 0,
							data: klineData,
							symbol: this.Symbol,
							name: this.PairName
						};
						callback(hqChartData);
					}
				});

			},



			CustomIndex(data, callback) //自定义指标
			{
				var request = data.Request;
				if (request.Data.indexname == '多线段指标') {
					data.PreventDefault = true;

					var hqchart = data.HQChart;
					var kData = hqchart.ChartPaint[0].Data;
					var closeLine = {
						name: '收盘价线',
						type: 0,
						data: kData.GetClose()
					};
					var klineDate = kData.GetDate();

					//模拟异步请求指标数据
					setTimeout(function() {
						var apiData = {
							code: 0,
							stock: {
								name: hqchart.Name,
								symbol: hqchart.Symbol
							},
							outdata: {
								date: klineDate,
								outvar: [closeLine]
							}
						};
						console.log('[NetworkFilter::NetworkFilter] apiData ', apiData);
						callback(apiData);
					}, 200);

				}
			},

			OnRecvHistoryData(e, data, obj) {
				console.log('[HQChart:OnRecvHistoryData] ', data, obj);

				var period = data.HistoryData.Period;
				this.TestData = `${data.Stock.Name}, ${data.Stock.Symbol} 周期=${period}`;
			},

			OnTitleDraw(event, data, obj) //K线信息
			{
				console.log('[OnTitleDraw]', event, data);
				if (!data.Draw) {
					this.TestData = "";
				} else {
					var item = data.Draw;
					this.TestData = `日期:${item.Date} 收:${item.Close}`;
				}
			},

			OnMinuteTitleDraw(event, data, obj) //分时图信息
			{
				console.log('[OnMinuteTitleDraw]', event, data);
				if (!data.Draw) {
					this.TestData = "";
				} else {
					var item = data.Draw;
					this.TestData = `时间:${item.Time} 价格:${item.Close}`;
				}
			},

			///////////////////////////////////////////////
			//手势事件 app/小程序才有
			//KLine事件
			KLineTouchStart: function(event) {
				if (g_JSChart) g_JSChart.OnTouchStart(event);
			},

			KLineTouchMove: function(event) {
				if (g_JSChart) g_JSChart.OnTouchMove(event);
			},

			KLineTouchEnd: function(event) {
				if (g_JSChart) g_JSChart.OnTouchEnd(event);
			},

			ChangeSize() {
				// #ifdef H5
				this.ChangeSize_h5();
				// #endif

				// #ifndef H5
				this.ChangeSize_app();
				// #endif
			},

			ChangeSize_app() {
				if (g_JSChart) {
					//把画布大小设置到hqchart
					g_JSChart.OnSize({
						Width: this.ChartWidth,
						Height: this.ChartHeight,
						Type: 1
					}); //重新绘制
				}
			},

			ChangeSize_h5() {
				var chartHeight = this.ChartHeight;
				var chartWidth = this.ChartWidth;

				var kline = this.$refs.kline;
				kline.style.width = chartWidth + 'px';
				kline.style.height = chartHeight + 'px';
				if (g_JSChart) g_JSChart.OnSize();
			}
		}
	}
</script>

<style>

</style>
