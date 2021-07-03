<template>
  <div class="dashboard-container">
    <!--header-->
    <div class="dashboard-header">
      <div class="dashboard-header-title">总数据统计</div>
      <div class="dashboard-headerRight">
        <div class="app-box-date app-marginR">
          <el-date-picker class="app-marginR"
                          v-model="value1"
                          type="week"
                          format="yyyy 第 WW 周"
                          placeholder="选择日期">
          </el-date-picker>
        </div>
        <div class="app-btn-box">
          <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
          <!--          <el-button type="primary" icon="el-icon-search">历史</el-button>-->
        </div>
      </div>
    </div>
    <!--allMOney-->
    <div class="allmoney">
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe843;</i>
        </div>
        <div class="allmoney-div2" :title="num1">
          <div class="allmoney-div2_1 oneTxt">{{num1}}</div>
          <div class="allmoney-div2_2">商户数量</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont1">&#xe68e;</i>
        </div>
        <div class="allmoney-div2" :title="num2">
          <div class="allmoney-div2_1 oneTxt">{{num2}}</div>
          <div class="allmoney-div2_2">订单数量</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe711;</i>
        </div>
        <div class="allmoney-div2" :title="num3">
          <div class="allmoney-div2_1 oneTxt">{{num3}}</div>
          <div class="allmoney-div2_2">订单金额</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe61e;</i>
        </div>
        <div class="allmoney-div2" :title="num4">
          <div class="allmoney-div2_1 oneTxt">{{num4}}</div>
          <div class="allmoney-div2_2">充值金额</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont2">&#xe742;</i>
        </div>
        <div class="allmoney-div2" :title="num5">
          <div class="allmoney-div2_1 oneTxt">{{num5}}</div>
          <div class="allmoney-div2_2">代付金额</div>
        </div>
      </div>
    </div>
    <!--图-->
    <div class="allImg1">
      <div class="allImg1-box">
        <span>商户数量</span>
        <div id="tu1"></div>
      </div>
      <div class="allImg1-box">
        <span>充值金额</span>
        <div id="tu2"></div>
      </div>
      <div class="allImg1-box">
        <span>代付金额</span>
        <div id="tu3"></div>
      </div>
    </div>
    <div class="allImg2">
      <div class="allImg2-box">
        <span>订单数量</span>
        <div id="tu4"></div>
      </div>
      <div class="allImg2-box">
        <span>订单金额</span>
        <div id="tu5"></div>
      </div>

    </div>
  </div>
</template>

<script>
  import echarts from 'echarts'
  import { getHome } from '@/api/user'

  export default {

    name: 'Dashboard',
    data() {
      return {
        value1: '',
        nowTime: '',
        num1: '',
        num2: '',
        num3: '',
        num4: '',
        num5: ''
      }
    },
    mounted() {
      this.getZ()
    },
    methods: {
      //查询
      seeOther() {
        var time1 = '', time2 = ''
        if (this.value1 != '' && this.value1 != null) {
          time1 = this.value1.getTime()
          time2 = time1 + 604799000
        }
        console.log(time2)
        this.getData(this.formatDate(new Date(time2)))
      },
      //获取当前时间的周天
      getZ() {
        let _nowTime = new Date().getTime()
        let _oldTime = new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1).getTime() - _nowTime
        let _week = new Date().getDay()
        let _dayLongTime = 24 * 60 * 60 * 1000
        let _furtureSundayTimes = _nowTime + (7 - _week) * _dayLongTime + _oldTime
        console.log(_furtureSundayTimes)
        this.nowTime = _furtureSundayTimes
        this.getData(this.formatDate(new Date(_furtureSundayTimes)))
      },
      sum(arr) {
        return eval(arr.join('+'))
      },
      //获取首页图标数据
      getData(time) {
        getHome({ endTime: time }).then(res => {
          console.log(res)
          var merchantNumber = [], topUpAmount = [], countNumber = [], successNumber = [], orderAmount = [],
            successAmount = [], payAmount = []
          if (res.code == 10000) {
            let list = res.data
            for (var i = 0; i < list.length; i++) {
              merchantNumber.push(list[i].merchantNumber)
              topUpAmount.push(list[i].topUpAmount)
              countNumber.push(list[i].countNumber)
              successNumber.push(list[i].successNumber)
              orderAmount.push(list[i].orderAmount)
              successAmount.push(list[i].successAmount)
              payAmount.push(list[i].payAmount)
            }
            this.num1 = this.sum(merchantNumber);
            this.num2 = this.sum(countNumber);
            this.num3 = this.sum(orderAmount);
            this.num4 = this.sum(topUpAmount);
            this.num5 = this.sum(successAmount);

            this.cavans1(merchantNumber)
            this.cavans2('tu2', '#FF7EA5', topUpAmount, '充值金额')
            this.cavans2('tu3', '#FF7EA5', successAmount, '代付金额')
            this.cavans3('tu4', '#5C3882', '#20DEFF', countNumber, successNumber, '订单数', '订单成功数')
            this.cavans3('tu5', '#CF8FFF', '#20DEFF', orderAmount, successAmount, '订单金额', '成功订单金额')

          }
        })
      },
      cavans1(data1) {
        let myChart = this.$echarts.init(document.getElementById('tu1'))
        // 绘制图表。
        myChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#283b56'
              }
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top: '15%',
            containLabel: true
          },

          xAxis: {
            type: 'category',
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
            axisLine: {
              lineStyle: {
                color: '#9695B7'
              }
            }
          },
          yAxis: {
            name: '单位:人',
            type: 'value',
            axisLine: {
              lineStyle: {
                color: '#9695B7'
              }
            }
          },
          series: [{
            name: '商户数量',
            color: ['#CF8FFF'],
            data: data1,
            type: 'line',
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: '#E6C5FF'
              }, {
                offset: 1,
                color: '#FDFDFE'
              }])
            }
          }]
        })
        window.addEventListener('resize', () => {
          myChart.resize()
        })
      },
      cavans2(id, color, data, text) {
        let myChart = this.$echarts.init(document.getElementById(id))
        // 绘制图表。
        myChart.setOption({
          color: color,
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#283b56'
              }
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top: '15%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
              axisTick: {
                alignWithLabel: true
              },
              axisLine: {
                lineStyle: {
                  color: '#9695B7'
                }
              }
            }
          ],
          yAxis: [

            {
              type: 'value',
              name: '单位:元',
              axisLine: {
                lineStyle: {
                  color: '#9695B7'
                }
              }
            }

          ],
          series: [
            {
              name: text,
              type: 'bar',
              barWidth: '20',  //设置柱状宽度
              barMaxWidth: '20',
              data: data
            }
          ]
        })
        window.addEventListener('resize', () => {
          myChart.resize()
        })
      },
      cavans3(id, color1, color2, data1, data2, txt1, txt2) {
        let myChart = this.$echarts.init(document.getElementById(id))
        // 绘制图表。
        myChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#283b56'
              }
            }
          },
          legend: {
            data: [txt1, txt2]
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            axisLine: {
              lineStyle: {
                color: '#9695B7'
              }
            }
          },
          yAxis: {
            axisLine: {
              lineStyle: {
                color: '#9695B7'
              }
            },
            type: 'category',
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
          },
          series: [
            {
              name: txt1,
              type: 'bar',
              stack: '总量',
              color: color1,
              barWidth: '30',  //设置柱状宽度
              barMaxWidth: '30',
              label: {
                position: 'insideRight'
              },
              data: data1
            },
            {
              name: txt2,
              type: 'bar',
              stack: '总量',
              color: color2,
              barWidth: '30',  //设置柱状宽度
              barMaxWidth: '30',
              label: {
                position: 'insideRight'
              },
              data: data2
            }
          ]
        })
        window.addEventListener('resize', () => {
          myChart.resize()
        })
      }
    }
  }
</script>

<style lang="less">
  .dashboard-container {
    width: 98%;
    height: 90vh;
    margin: 0 auto;
    margin-top: 2vh;

    .dashboard-header {
      width: 100%;
      height: 50px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      .dashboard-header-title {
        font-weight: bolder;
        color: #3B3269;
        font-size: 16px;
      }

      .dashboard-headerRight {
        float: right;
        display: flex;
        flex-direction: row;

        .app-box-date, .el-date-picker, .el-date-editor {
          width: 160px !important;
        }

        .app-box-date > .el-input__inner {
          width: 140px !important;
        }
      }
    }

    .allmoney {
      width: 100%;
      height: 80px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      .allmoney-div {
        background: rgba(255, 255, 255, 1);
        opacity: 1;
        border-radius: 10px;
        width: 18%;
        display: flex;
        flex-direction: row;

        .allmoney-div1 {
          width: 50%;
          height: 100%;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;

          .iconfont {
            font-size: 40px;
            color: #20DEFF;
            margin: 0 auto;
          }

          .iconfont1 {
            font-size: 50px;
          }

          .iconfont2 {
            font-size: 35px;
          }
        }

        .allmoney-div2 {
          width: 50%;
          height: 100%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          text-align: center;

          .allmoney-div2_1 {
            color: #3B3269;
            font-size: 26px;
            font-weight: bold;
          }

          .allmoney-div2_2 {
            color: #3B3269;
            font-size: 13px;
            margin-top: 10px;
          }
        }
      }
    }

    .allImg1 {
      width: 100%;
      height: 32vh;
      margin-top: 10px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      .allImg1-box {
        width: 33%;
        height: 100%;
        background: rgba(255, 255, 255, 1);
        opacity: 1;
        border-radius: 20px;
        overflow: hidden;

        span {
          color: #3B3269;
          font-weight: bold;
          font-size: 16px;
          margin-top: 3%;
          margin-left: 3%;
          display: block;
        }

        #tu1 {
          width: 100%;
          height: 90%;
        }

        #tu2 {
          width: 100%;
          height: 90%;
        }

        #tu3 {
          width: 100%;
          height: 90%;
        }
      }
    }

    .allImg2 {
      width: 100%;
      height: 42vh;
      margin-top: 10px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      .allImg2-box {
        width: 49.8%;
        height: 100%;
        background: rgba(255, 255, 255, 1);
        opacity: 1;
        border-radius: 20px;
        overflow: hidden;

        span {
          color: #3B3269;
          font-weight: bold;
          font-size: 16px;
          margin-top: 3%;
          margin-left: 3%;
          display: block;
        }

        #tu4 {
          width: 100%;
          height: 90%;
        }

        #tu5 {
          width: 100%;
          height: 90%;
        }
      }
    }
  }
</style>
