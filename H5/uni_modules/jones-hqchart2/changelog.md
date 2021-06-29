## 1.1.10065（2021-06-28）
10064 修正分时图没有判断是否支持区间选择导致报错
## 1.1.10063（2021-06-28）
10061 小程序十字光标X轴日期文字支持3种格式 0=YYYY-MM-DD 1=YYYY/MM/DD  2=YYYY/MM/DD/W 3=DD/MM/YYYY<br>
10060 十字光标X轴日期显示 支持YY/MM/YYYY<br>
10057 修正百分比坐标价格和百分比两个颜色配置颠倒了.<br>
10055 修正分笔K线区间统计框起始时间格式显示不对.<br>
## 1.1.10040（2021-06-25）
10039 K线图支持上下拖拽<br>
10037 分笔K线增加支持叠加指标<br>
10035 分笔K线数据更新增加2.0格式<br>
10034 1. 分笔K线全量数据增加新的数据格式<br>
      2. 分笔标题信息和tooltip显示样式修改<br>
      3. 分笔增加百分比坐标<br>

## 1.1.9984（2021-06-17）
9983 修正SplitLogarithmicXYCoordinate()预留高度为负数是,显示错误<br>
9978 修正KLineChartContainer::ChangeIndexWindowCount() 显示错位<br>
     增加AddIndexWindow() 添加指标窗口<br>
9977 K线图增加事件ON_SPLIT_XCOORDINATE<br>
9958 KLineChartContainer.JsonDataToHistoryData(), KLineChartContainer.JsonDataToMinuteRealtimeData(), KLineChartContainer.JsonDataToMinuteHistoryData() 去掉价格<0的无效价格判断.<br>
9954 ChartVericaltLine, ChartHorizontalLine 支持横屏<br>
9953 绘图函数HORLINE()支持单数值条件<br>
9951 增加绘图函数HORLINE<br>
9950 增加绘图函数VERTLINE<br>
9949 部分替换 for(var i in array ) 改成 for(var i=0;i<array.length;++i)<br>
## 1.1.9948（2021-06-12）
9948 小程序分时图和K线图十字光标移动优化<br>
9947 小程序增加分时图十字光标移动延迟绘图间隔设置<br>
## 1.1.9946（2021-06-12）
9946 小程序优化分时图十字光标移动效率<br>
9945 小程序修正ChangePeriod()未完成数据下载切换周期报错<br>
9942 小程序ChartMinutePriceLine迁移到umychart.chartpaint.wechart.js中<br>
## 1.1.9938（2021-06-10）
9937 小程序K线训练增加是否显示最高最低价格显示控制<br>
9936, 9935 把部分数组循环遍历 (var i in array) 改成=> (var i=0;i<array.length;++i)<br>
## 1.1.9933（2021-06-09）
9931 小程序更新定时器增加判断是否图形已销毁<br>
9925 期货增加 郑州商品交易所-红枣(CJ)<br>
9921 JsonDataToMinuteDataArray 修正跨天数据 日期时间显示错误<br>
9919 修正分时图标题高度没有设置为0<br>
9917 大盘指数(INDEXA, INDEXC....)支持对3放数据对接<br>
## 1.1.9907（2021-06-06）
9907 修正IFrameSplitOperator::IntegerCoordinateSplit() 负数最小值计算错误<br>
9902 增加指标标题绘制回调函数 ON_INDEXTITLE_DRAW<br>
## 1.1.9901（2021-06-06）
9900 小程序修正IFrameSplitOperator::IntegerCoordinateSplit()最低价计算错误 <br>
9899 IFrameSplitOperator::IntegerCoordinateSplit() 修正最低价计算错误 <br>
## 1.1.9893（2021-06-04）
9892 小程序IFrameSplitOperator::IntegerCoordinateSplit()算法优化<br>
9890 IFrameSplitOperator::IntegerCoordinateSplit() 修正计算错误<br>
9886 增加多日集合竞价最新数据更新<br>
9885 修正FrameSplitY::GetCallAcutionSplitY() 多日分时Y轴多算了一个刻度<br>
     多日分时图集合竞价Y轴统一最大,最小值<br>
## 1.1.9864（2021-05-30）
9863 分时图集合竞价十字光标显示最近的有效数据<br>
9862 多日集合竞价十字光标支持Y轴数据显示<br>
9861 多日分时图Y轴显示集合竞价刻度<br>
## 1.1.9792（2021-05-21）
9792 小程序  <br>
1. 修正DRAWRECTREL()位置正确性没有判断<br>
2. Y轴内部刻度增加属性YTextBaseline,支持上下位置可以配置<br>
9790 FrameSplitMinutePriceY::GetMaxMin() 修正计算错误. Y轴内部刻度文字支持上下位子配置<br>
9788 导出 CoordinateInfo <br>

## 1.1.9759（2021-05-19）
9758 DynamicMinuteTitlePainting调整集合竞价1.0数据格式显示样式<br>
9756 小程序 ChartVolStick柱子高度<1,统一显示为1 <br>
9754 ChartVolStick() 高度小于1, 统一调整为1 <br>
9748 ShowSelectRect() 分时图显示位置调整<br>
9747 分时图增加区间选择接口<br>
9746 RequestDragMinuteData, RequestDragDayData 数据回调增加周期和复权参数<br>
9743 集合竞价支持绘制点 <br>


## 1.1.9725（2021-05-15）
9724 修正ChartMultiHtmlDom() 在页面放大倍数以后, DOM坐标计算错误 <br>
9719 小程序 JSComplier.Execute() 去掉ios回报错的日志 <br>
## 1.1.9694（2021-05-10）
9694 小程序 1. JSCHART_EVENT_ID定义迁移到umychart.data.wechart.js里面 <br>
      2. 支持 JSCHART_EVENT_ID.ON_SPLIT_YCOORDINATE 事件 <br>
9687 小程序ChartBorder增加 GetBorder(),GetHScreenBorder() <br>
## 1.1.9683（2021-05-09）
修改readme
## 1.1.9682（2021-05-09）
修改readme
## 1.1.9681（2021-05-09）
9671 增加分时图集合竞价关闭按钮<br>
9670 分时图集合竞价支持横屏<br>
9668 增加收盘集合竞价<br>
## 1.1.9654（2021-05-04）
9653 1.分时图双击事件 <br>
     2.集合竞价宽度设置 <br>
9651 修正ChartMinutePriceLine::DrawBeforeOpen()报错 <br>
9649 MULTI_LINE多线段支持"C","H"等特殊标识价格 <br>
9648 1.修正分时图叠加指标共享Y轴横屏没有读取主图Y轴信息 <br>
     2.ChartMultiSVGIcon 使用GetBorder获取坐标信息 <br>
9647 分时图集合竞价框架重新调整 <br>
## 1.1.9617（2021-04-28）
9616 修正IsSHSZStockA() 没有包含300创业板<br>
9610 UpdateFrameMaxMin()  主图计算Y轴最大最小值增加叠加指标数据 <br>
9604 分时图和K线主图支持ON_SPLIT_YCOORDINATE事件 <br>
9599 Y轴分割函数，增加是否支持Y轴缩放查询接口 <br>
9597 增加 Y轴分割刻度回调事件 <br>


## 1.1.9536（2021-04-23）
增加插件教程说明
## 1.1.9535（2021-04-22）
增加HQChart模板样例
## 1.1.9534（2021-04-22）
9532 公告信息地雷支持去重更新 <br>
9529 信息地理拖拽下载数据以后,支持更新调用 <br>
9520 增加AB波浪尺 <br>
## 1.1.9518（2021-04-21）
9517 修正多日分时图更新当天数据时,叠加指标没有重新计算 <br>
9516 小程序增加ON_PHONE_TOUCH事件回调 <br>
9509 修正OverlayScriptIndex::CreateTextLine()画法保存错误 <br>
9501 1. 信息地雷顶部画不下了, 移动下面画<br>
     2. 修正单击K线和双击K线同时触发回调的问题<br>

## 1.1.9500（2021-04-19）
9481 增加画图工具图标<br>
9479 小程序修正BARSSINCEN算法错误<br>
9478 修正BARSSINCEN算法<br>
9477 修正 JSCHART_OPERATOR_ID.OP_LEFT_ZOOM_OUT 数据索引计算错误<br>
9497 走势图支持后台指标API数据<br>
9496 修正"BindOverlayPositionData", "ClearBindOverlayPositionData" 指标变量写错了.<br>
9495 分时图支持叠加指标<br>
9499 h5增加手势事件回调ON_PHONE_TOUCH. <br>

## 1.1.9473（2021-04-12）
9472 修正K线数据更新以后叠加指标没有执行更新<br>
9471 小程序"CIRCLEDOT","POINTDOT"点半径支持设置<br>
9469 CIRCLEDOT, POINTDOT支持半径大小设置<br>
9466 小程序ChartOperator右移K线支持下载功能<br>
9465 小程序COLORSTICK支持标题字体颜色设置<br>
9469 COLORSTICK 支持后面增加颜色控制标题的字体颜色<br>
9462 增加点击回调事件<br>
9458 ChartOperator() 数据右移增加，历史数据下载功能<br>
## 1.1.9444（2021-04-08）
9443 增加K线叠加指标是否显示指标名字配置
9441 ChangeIndexTemplate() 支持指标标题字体设置
9338 ChartSingleText 获取X轴坐标分时图和K线图分开

## 1.1.9417（2021-04-01）
9416 增加水平线段
## 1.1.9416（2021-03-31）
增加测试用例项目
## 1.1.9415（2021-03-31）
把原来hqchart改成uniapp插件模式. 版本号 1.9415
