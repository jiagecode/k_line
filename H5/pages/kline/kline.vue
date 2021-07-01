<template>
	<div class='divchart' style='background-color:#101010;' >
		<!--  #ifdef  H5  -->
		<div>
		<div class='kline' id="kline" ref='kline'></div>
		</div>
		<!--  #endif -->
		
		<!--  #ifndef  H5 -->
		<view>
			<canvas id="kline2" canvas-id='kline2' class='kline2' v-bind:style="{width: ChartWidth+'px', height: ChartHeight+'px'}" 
			  @touchstart="KLineTouchStart" @touchmove='KLineTouchMove' @touchend='KLineTouchEnd' ></canvas>  
		</view>
		<!--  #endif -->
		
		<div class="button-sp-area">
			<button class="mini-btn" type="default" size="mini" @click="ChangeMinutePeriod(MINUTE_PERIOD_ID.MINUTE_ID)">分时</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeMinutePeriod(MINUTE_PERIOD_ID.MINUTE_5DAY_ID)">5日</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_DAY_ID)">日线</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_WEEK_ID)">周线</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_MINUTE_ID)">1分钟</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLinePeriod(KLINE_PERIOD_ID.KLINE_15MINUTE_ID)">15分钟</button>
		</div>
		<div class="button-sp-area">
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(0,'EMPTY')">主图空指标</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(0,'BOLL')">BOLL</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'MACD')">MACD</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'VOL')">VOL</button>
			<button class="mini-btn" type="default" size="mini" @click="ChangeKLineIndex(1,'KDJ')">KDJ</button>
		</div>
		<div style='color: #007AFF;'>{{TestData}}</div>
		
	</div>
</template>

<script>
///////////////////////////////////////////////////////////////////////////////////////////////////
//	HQChart单画布模式,K线/分时共享使用一个画布
//
//
//
///////////////////////////////////////////////////////////////////////////////////////////////////


// #ifdef H5	
import HQChart from '@/uni_modules/jones-hqchart2/js_sdk/umychart.uniapp.h5.js'
// #endif

// #ifndef H5
import {JSCommon} from '@/uni_modules/jones-hqchart2/js_sdk/umychart.wechat.3.0.js'
import {JSCommonHQStyle} from '@/uni_modules/jones-hqchart2/js_sdk/umychart.style.wechat.js'
import {JSConsole} from '@/uni_modules/jones-hqchart2/js_sdk/umychart.console.wechat.js'

//禁用日志
//JSConsole.Complier.Log=()=>{ };
//JSConsole.Chart.Log=()=>{ };

// #endif

function DefaultData() { }

DefaultData.GetKLineOption = function () 
{
    let data = 
    {
        Type: '历史K线图', 
        
        Windows: //窗口指标
        [
            {Index:"MA",Modify: false, Change: false}, 
			{Index:"VOL",Modify: false, Change: false}
			/*
            { 
				Index:'多线段指标',Modify: false, Change: false,
				API: 
				{
					Name:'多线段指标',
					Script:null,
					Args:null, 
					Url:'http://127.0.0.1:18080/api/jsindex' 
				} 
			}
			*/
        ], 
 
        IsCorssOnlyDrawKLine:true,
        CorssCursorTouchEnd:true,
		IsClickShowCorssCursor:true,
		CorssCursorInfo:{ Left:2, Right:2, Bottom:1, IsShowCorss:true},  //十字光标刻度设置
		//IsFullDraw:true,
 
        Border: //边框
        {
            Left:   1,
            Right:  1, //右边间距
            Top:    25,
            Bottom: 25,
        },
 
        KLine:
        {
			DragMode:1, 
            Right:1,                            //复权 0 不复权 1 前复权 2 后复权
            Period:0,                           //周期: 0 日线 1 周线 2 月线 3 年线 
            PageSize:30,
            IsShowTooltip:false,
			DrawType:0, 
			DataWidth:10
        },
		
		ExtendChart:
		[
			{Name:'KLineTooltip' },	//开启手机端tooltip
		], 
		
		Frame:  //子框架设置
		[
		    {SplitCount:3,Custom: [{ Type: 0, Position: 'right'}]},
		    {SplitCount:2,},
		    {SplitCount:3},
		],
        
    };
 
    return data;
}
 
DefaultData.GetMinuteOption=function()
{
    var option= 
    {
        Type:'分钟走势图',   //创建图形类型
            
        Windows: //窗口指标
        [
            
        ], 
            
        IsAutoUpdate:true,       //是自动更新数据
        DayCount:1,                 //1 最新交易日数据 >1 多日走势图
        IsShowRightMenu:false,       //是否显示右键菜单
        CorssCursorTouchEnd:true,
		//IsFullDraw:true,
		
        MinuteLine:
        {
            //IsDrawAreaPrice:false,      //是否画价格面积图
        },
 
        Border: //边框
        {
            Left:1,    //左边间距
            Right:1,   //右边间距
            Top:20,
            Bottom:20
        },
            
        Frame:  //子框架设置
        [
            {SplitCount:3},
            {SplitCount:2,Height:0 },
            {SplitCount:3},
        ],
 
        ExtendChart:    //扩展图形
        [
            {Name:'MinuteTooltip' }  //手机端tooltip
        ],
    };
 
    return option;
}


//周期枚举
var KLINE_PERIOD_ID=
{
    KLINE_DAY_ID:0,
    KLINE_WEEK_ID:1,
    KLINE_MONTH_ID:2,
    KLINE_YEAR_ID:3,

    KLINE_MINUTE_ID:4,
    KLINE_5MINUTE_ID:5,
    KLINE_15MINUTE_ID:6,
    KLINE_30MINUTE_ID:7,
    KLINE_60MINUTE_ID:8
}

//周期枚举
var MINUTE_PERIOD_ID=
{
    MINUTE_ID:1,
    MINUTE_2DAY_ID:2,
    MINUTE_3DAY_ID:3,
    MINUTE_4DAY_ID:4,
    MINUTE_5DAY_ID:5,
}

var g_JSChart=null;

export default 
{
	data() 
	{
		let data=
		{
			Symbol:'600000.sh',
			ChartWidth:350,
			ChartHeight:500,
			KLine:
			{
				Period:0,
				Right:0
			},
			Minute:
			{
				Days:1
			},
			
			MINUTE_PERIOD_ID:MINUTE_PERIOD_ID,
			KLINE_PERIOD_ID:KLINE_PERIOD_ID,
			TestData:'测试测试'
		};
		
		return data;
	},
	
	onLoad() 
	{
		
	},
	
	onReady()
	{
		
	},
	
	onShow(){
		
		document.title = '币安秒合约';
		
		uni.getSystemInfo({
		    success:  (res) =>
			{
				var width=res.windowWidth;
				var height=res.windowHeight;
		        this.ChartWidth=width;
				this.ChartHeight=height-130;
				this.$nextTick(()=>
				{
					this.ChangeSize();
					this.CreateKLineChart(); 
				})
		    }
		});
	},
	
	onHide()
	{
		this.ClearChart();
	},
	
	onUnload()
	{
		this.ClearChart();
	},
	
	methods: 
	{
		ClearChart()
		{
			if (g_JSChart)
			{
				g_JSChart.ChartDestory();
				g_JSChart=null;
			}
			
			// #ifdef H5
			var divKLine=document.getElementById('kline');
			while (divKLine.hasChildNodes()) 
			{
				divKLine.removeChild(divKLine.lastChild);
			}　
			// #endif
		},
		
		
		OnSize()
		{
			// #ifdef H5
			this.OnSize_h5();
			// #endif
		},
		
		OnSize_h5()
		{
			var chartHeight = this.ChartHeight;
			var chartWidth = this.ChartWidth;
			 
			var kline=this.$refs.kline;
			kline.style.width=chartWidth+'px';
			kline.style.height=chartHeight+'px';
			if (g_JSChart) g_JSChart.OnSize();
		},
				
		CreateKLineChart_h5()  //创建K线图
        {
            this.ClearChart();
			
			var blackStyle=HQChart.HQChartStyle.GetStyleConfig(HQChart.STYLE_TYPE_ID.BLACK_ID);
			HQChart.JSChart.SetStyle(blackStyle);
			this.$refs.kline.style.backgroundColor=blackStyle.BGColor;	//div背景色设置黑色
			
			var chart=HQChart.JSChart.Init(this.$refs.kline);
			var option=DefaultData.GetKLineOption();		
            option.Symbol=this.Symbol;
			option.Right=this.KLine.Right;
			option.Period=this.KLine.Period;
			option.NetworkFilter=this.NetworkFilter;
            chart.SetOption(option);
			chart.AddEventCallback({ event: HQChart.JSCHART_EVENT_ID.ON_TITLE_DRAW, callback: this.OnTitleDraw });
			
            g_JSChart=chart;
        },
		
		CreateKLineChart_app()
		{
			this.ClearChart();
			
			var element = new JSCommon.JSCanvasElement();
			// #ifdef APP-PLUS
			element.IsUniApp=true;	//canvas需要指定下 是uniapp的app
			// #endif
			element.ID = 'kline2';
			element.Height = this.ChartHeight;  //高度宽度需要手动绑定!!
			element.Width = this.ChartWidth;
			
			var blackStyle=JSCommonHQStyle.GetStyleConfig(JSCommonHQStyle.STYLE_TYPE_ID.BLACK_ID);
			//blackStyle.DefaultTextColor='rgb(255,0,255)';
			
			JSCommon.JSChart.SetStyle(blackStyle);
				
			var chart = JSCommon.JSChart.Init(element);
			var option=DefaultData.GetKLineOption();
			option.NetworkFilter=this.NetworkFilter;
			option.Symbol=this.Symbol;
			option.Right=this.KLine.Right;
			option.Period=this.KLine.Period;
			option.IsClickShowCorssCursor=true;
			option.IsFullDraw=true; 	//每次手势移动全屏重绘
			chart.SetOption(option);
			//chart.AddEventCallback( { event:JSCommon.JSCHART_EVENT_ID.RECV_INDEX_DATA, callback:this.OnRecvHistoryData });
			chart.AddEventCallback({ event: JSCommon.JSCHART_EVENT_ID.ON_TITLE_DRAW, callback: this.OnTitleDraw });
			
			g_JSChart=chart;
		},
		
		CreateKLineChart()
		{
			// #ifdef H5
			this.CreateKLineChart_h5();
			// #endif
			
			// #ifndef H5
			this.CreateKLineChart_app();
			// #endif
		},
		
		IsKLineChart()
		{
			if (!g_JSChart) return false;
			var className=g_JSChart.JSChartContainer.ClassName;
			if (className=="KLineChartContainer" || className=="KLineChartHScreenContainer") return true;
			
			return false;
		},
		
		IsMinuteChart()
		{
			if (!g_JSChart) return false;
			var className=g_JSChart.JSChartContainer.ClassName;
			if (className=="MinuteChartContainer" || className=="MinuteChartHScreenContainer") return true;
			
			return false;
		},
		
		//K线周期切换
		ChangeKLinePeriod:function(period)
		{
			this.KLine.Period=period;
			if (this.IsKLineChart())
			{
				g_JSChart.ChangePeriod(period);
			}
			else
			{
				this.CreateKLineChart();
			}
		},
		
		CreateMinuteChart_h5() //创建日线图
		{
			this.ClearChart();
			
			var blackStyle=HQChart.HQChartStyle.GetStyleConfig(HQChart.STYLE_TYPE_ID.BLACK_ID);
			HQChart.JSChart.SetStyle(blackStyle);
			this.$refs.kline.style.backgroundColor=blackStyle.BGColor;	//div背景色设置黑色
			
			var chart=HQChart.JSChart.Init(this.$refs.kline);
			var option=DefaultData.GetMinuteOption();
			option.Symbol=this.Symbol;
			option.NetworkFilter=this.NetworkFilter;
			option.DayCount=this.Minute.Days;
			chart.SetOption(option);
			chart.AddEventCallback({ event: HQChart.JSCHART_EVENT_ID.ON_TITLE_DRAW, callback: this.OnTitleDraw });
			
			g_JSChart=chart;
		},
		
		CreateMinuteChart_app()
		{
			this.ClearChart();
			
			var element = new JSCommon.JSCanvasElement();
			// #ifdef APP-PLUS
			element.IsUniApp=true;	//canvas需要指定下 是uniapp的app
			// #endif
			element.ID = 'kline2';
			element.Height = this.ChartHeight;  //高度宽度需要手动绑定!!
			element.Width = this.ChartWidth;
				
			//用黑色风格
			var blackStyle=JSCommonHQStyle.GetStyleConfig(JSCommonHQStyle.STYLE_TYPE_ID.BLACK_ID);	
			JSCommon.JSChart.SetStyle(blackStyle);
			//var testttt=JSCommon.MARKET_SUFFIX_NAME.GetSHODecimal();
			//JSCommon.MARKET_SUFFIX_NAME.GetSHODecimal=(symbol)=>{ return 4; };	//设置期权代码
					
			var chart = JSCommon.JSChart.Init(element);
			var option=DefaultData.GetMinuteOption();
			option.NetworkFilter=this.NetworkFilter;
			option.Symbol=this.Symbol;
			option.DayCount=this.Minute.Days;
			option.IsFullDraw=true; 	//每次手势移动全屏重绘
			chart.SetOption(option);
			//chart.AddEventCallback({ event: JSCommon.JSCHART_EVENT_ID.ON_TITLE_DRAW, callback: this.OnMinuteTitleDraw });
			
			g_JSChart=chart;
		},
		
		CreateMinuteChart()
		{
			// #ifdef H5
			this.CreateMinuteChart_h5();
			// #endif
			
			// #ifndef H5
			this.CreateMinuteChart_app();
			// #endif
		},
		
		//走势图多日切换
		ChangeMinutePeriod:function(period)
		{
			this.Minute.Days=period;
			if (this.IsMinuteChart())
			{
				g_JSChart.ChangeDayCount(period);
			}
			else
			{
				this.CreateMinuteChart();	//类型不对 删了重建
			}
		},
		
		//切换指标 windowId=窗口索引 name=指标名字
		ChangeKLineIndex:function(windowId,name)
		{
			if (!g_JSChart) return;
			if (this.IsKLineChart(g_JSChart)) g_JSChart.ChangeIndex(windowId,name);
		},
		
		//切换股票
		ChangeSymbol:function(symbol)
		{
			this.Symbol=symbol;
			if (g_JSChart) g_JSChart.ChangeSymbol(symbol);
		},
		
		NetworkFilter:function(data, callback)
		{
			console.log(`[HQChart:NetworkFilter] Name=${data.Name} Explain=${data.Explain}` );
			if (data.Name=='APIScriptIndex::ExecuteScript') this.CustomIndex(data, callback);
		},
		
		CustomIndex(data, callback)	//自定义指标
		{
			 var request=data.Request;
			 if (request.Data.indexname=='多线段指标')
			 {
				 data.PreventDefault=true;
				 
				 var hqchart=data.HQChart;
				 var kData=hqchart.ChartPaint[0].Data;
				 var closeLine={name:'收盘价线', type:0, data:kData.GetClose() };
				 var klineDate=kData.GetDate();
				 
				 //模拟异步请求指标数据
				 setTimeout(function() 
				 {
					var apiData=
					{
						 code:0, 
						 stock:{ name:hqchart.Name, symbol:hqchart.Symbol }, 
						 outdata: { date:klineDate, outvar:[closeLine] } 
					};
					console.log('[NetworkFilter::NetworkFilter] apiData ', apiData);
					callback(apiData); 
				 }, 200);
				 
			 }
		},
		
		OnRecvHistoryData(e, data, obj)
		{
			console.log('[HQChart:OnRecvHistoryData] ', data, obj );
			
			var period=data.HistoryData.Period;
			this.TestData=`${data.Stock.Name}, ${data.Stock.Symbol} 周期=${period}`;
		},
		
		OnTitleDraw(event, data, obj)	//K线信息
		{
			console.log('[OnTitleDraw]', event, data);
			if (!data.Draw) 
			{
				this.TestData="隐藏";
			}
			else
			{
				var item=data.Draw;
				this.TestData=`日期:${item.Date} 收:${item.Close}`;
			}
		},
		
		OnMinuteTitleDraw(event, data, obj)	//分时图信息
		{
			console.log('[OnMinuteTitleDraw]', event, data);
			if (!data.Draw) 
			{
				this.TestData="隐藏";
			}
			else 
			{
				var item=data.Draw;
				this.TestData=`时间:${item.Time} 价格:${item.Close}`;
			}
		},
		
		///////////////////////////////////////////////
		//手势事件 app/小程序才有
		//KLine事件
		KLineTouchStart: function (event) 
		{
		  if (g_JSChart) g_JSChart.OnTouchStart(event);
		},
		
		KLineTouchMove: function (event) 
		{
		  if (g_JSChart) g_JSChart.OnTouchMove(event);
		},
		
		KLineTouchEnd: function (event) 
		{
		  if (g_JSChart) g_JSChart.OnTouchEnd(event);
		},
		
		ChangeSize()
		{
			// #ifdef H5
			this.ChangeSize_h5();
			// #endif
			
			// #ifndef H5
			this.ChangeSize_app();
			// #endif
		},
		
		ChangeSize_app()
		{
			if (g_JSChart)	
			{
				//把画布大小设置到hqchart
				g_JSChart.OnSize({Width:this.ChartWidth, Height:this.ChartHeight, Type:1});	//重新绘制
			}
		},
		
		ChangeSize_h5()
		{
			var chartHeight = this.ChartHeight;
			var chartWidth = this.ChartWidth;
			 
			var kline=this.$refs.kline;
			kline.style.width=chartWidth+'px';
			kline.style.height=chartHeight+'px';
			if (g_JSChart) g_JSChart.OnSize();
		}
	}
}

</script>

<style>
	
</style>
