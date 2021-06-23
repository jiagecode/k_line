<template>
	<div class='divchart' >{{zjs}}
		<div class='kline' id="kline" ref='kline'  v-show="KLine.IsShow"></div>
	</div>
</template>

<script>
	
import HQChart from '../../umychart_uniapp_h5/umychart.uniapp.h5.js'

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
        ], 
 
        IsCorssOnlyDrawKLine:true,
        CorssCursorTouchEnd:true,
 
        Border: //边框
        {
            Left:   1,
            Right:  1, //右边间距
            Top:    25,
            Bottom: 25,
        },
 
        KLine:
        {
            Right:1,                            //复权 0 不复权 1 前复权 2 后复权
            Period:0,                           //周期: 0 日线 1 周线 2 月线 3 年线 
            PageSize:70,
            IsShowTooltip:false
        },
		
		ExtendChart:
		[
			{Name:'KLineTooltip' },	//开启手机端tooltip
		], 
        
    };
 
    return data;
}
 

export default 
{
	data() 
	{
		let data=
		{
			Symbol:'600000.sh',
			ChartWidth:300,
			ChartHeight:600,
			KLine:
			{
				JSChart:null,
				Option:DefaultData.GetKLineOption(), 
				IsShow:true,
			}
		};
		
		return data;
	},
	
	onLoad() 
	{
		
	},
	
	onReady()
	{
		this.$nextTick(()=>
		{
			this.OnSize();
			this.CreateKLineChart(); 
		});
	},
	
	methods: 
	{
		OnSize()
		{
			var chartHeight = this.ChartHeight;
			var chartWidth = this.ChartWidth;
 
			var kline=this.$refs.kline;
			kline.style.width=chartWidth+'px';
			kline.style.height=chartHeight+'px';
			if (this.KLine.JSChart) this.KLine.JSChart.OnSize();
		},
				
		CreateKLineChart()  //创建K线图
        {
            if (this.KLine.JSChart) return;
            this.KLine.Option.Symbol=this.Symbol;
            let chart=HQChart.JSChart.Init(this.$refs.kline);
            chart.SetOption(this.KLine.Option);
            this.KLine.JSChart=chart;
        },
	}
}
</script>

<style>
	
</style>