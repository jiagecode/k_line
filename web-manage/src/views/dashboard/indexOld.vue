<template>
  <div class="dashboard-container">
    <!--header-->
    <div class="dashboard-header">
      <div class="dashboard-header-title">总数据统计</div>
      <div class="dashboard-headerRight">
      </div>
    </div>
    <!--allMOney-->
    <div class="allmoney">
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe843;</i>
        </div>
        <div class="allmoney-div2" :title="num1">
          <div class="allmoney-div2_1 oneTxt">{{homeData.todayUserNum}}</div>
          <div class="allmoney-div2_2">今日新增用户</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe843;</i>
        </div>
        <div class="allmoney-div2" :title="num1">
          <div class="allmoney-div2_1 oneTxt">{{homeData.todayAgentNum}}</div>
          <div class="allmoney-div2_2">今日新增代理</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe843;</i>
        </div>
        <div class="allmoney-div2" :title="num1">
          <div class="allmoney-div2_1 oneTxt">{{homeData.allUserNum}}</div>
          <div class="allmoney-div2_2">总用户</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe843;</i>
        </div>
        <div class="allmoney-div2" :title="num1">
          <div class="allmoney-div2_1 oneTxt">{{homeData.userAllMoney}}</div>
          <div class="allmoney-div2_2">用户总余额</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont1">&#xe68e;</i>
        </div>
        <div class="allmoney-div2" :title="num2">
          <div class="allmoney-div2_1 oneTxt">{{homeData.todayOrderNum}}</div>
          <div class="allmoney-div2_2">今日订单</div>
        </div>
      </div>
    </div>
    <div class="allmoney">
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe711;</i>
        </div>
        <div class="allmoney-div2" :title="num3">
          <div class="allmoney-div2_1 oneTxt">{{homeData.profitAndLoss}}</div>
          <div class="allmoney-div2_2">客户盈亏</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont">&#xe61e;</i>
        </div>
        <div class="allmoney-div2" :title="num4">
          <div class="allmoney-div2_1 oneTxt">{{homeData.todayWater}}</div>
          <div class="allmoney-div2_2">今日流水</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont2">&#xe742;</i>
        </div>
        <div class="allmoney-div2" :title="num5">
          <div class="allmoney-div2_1 oneTxt">{{homeData.cashIn}}</div>
          <div class="allmoney-div2_2">今日充值</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont2">&#xe742;</i>
        </div>
        <div class="allmoney-div2" :title="num5">
          <div class="allmoney-div2_1 oneTxt">{{homeData.cashOut}}</div>
          <div class="allmoney-div2_2">今日提现</div>
        </div>
      </div>
      <div class="allmoney-div">
        <div class="allmoney-div1">
          <i class="iconfont iconfont2">&#xe742;</i>
        </div>
        <div class="allmoney-div2" :title="num5">
          <div class="allmoney-div2_1 oneTxt">{{homeData.todayFee}}</div>
          <div class="allmoney-div2_2">今日手续费</div>
        </div>
      </div>
    </div>
    <div class="dashboard-header-title">最新交易记录</div>
    <div class="dashboard-headerRight">
      <div class="app-btn-box">
        <el-button type="primary" icon="el-icon-search" @click="seeMore">更多》》</el-button>
      </div>
    </div>
    <el-table
      :data="orderDataList.list"
      border
      :max-height="600+'px'"
      :header-cell-style="tabheaderFn"
      style="width: 100%"
      lazy
      row-key="payId"
      default-expand-all
      :tree-props="{children: 'children'}">
      <el-table-column
        v-for="(item,index) in tabHead"
        :prop="item.prop"
        :label="item.label">
        <template slot-scope="scope">
                <span v-if="item.prop==='orderId'">
                             {{ scope.row.orderId }}
                </span>

          <!--                <span v-else-if="item.prop==='updateBy'" @click="handleCopy(scope.row.updateBy)">-->
          <!--                             {{scope.row.updateBy}}-->
          <!--                </span>-->

          <!-- 正常的其他列 -->
          <span v-else>{{ scope.row[item.prop] }}</span>
        </template>
      </el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script>
import {queryHomeData,orderVoList} from '@/api/adminUser'
  export default {

    name: 'Dashboard',
    data() {
      return {
        homeData:'',
        orderDataList:'',
        tabHead: [
          {
            label: '订单编号',
            prop: 'orderId'
          }, {
            label: '交易账户',
            prop: 'accountId'
          }, {
            label: '用户姓名',
            prop: 'userRealName'
          }, {
            label: '订单时间',
            prop: 'addDate'
          }, {
            label: '产品信息',
            prop: 'skuName'
          }, {
            label: '状态',
            prop: 'orderStatus'
          }, {
            label: '方向',
            prop: 'investType'
          }, {
            label: '时间/点数',
            prop: 'orderCycle'
          }, {
            label: '建仓点位',
            prop: 'inPoint'
          }, {
            label: '平仓点位',
            prop: 'outPoint'
          }, {
            label: '委托余额',
            prop: 'investAmount'
          }, {
            label: '实际盈亏',
            prop: 'resultMoney'
          }, {
            label: '买后余额',
            prop: 'userMoney'
          }, {
            label: '当日盈亏',
            prop: 'todayMoney'
          }, {
            label: '总盈亏',
            prop: 'allMoney'
          }, {
            label: '所属代理商',
            prop: 'agentName'
          }
        ],
      }
    },
    created() {
      this.queryOrderDataList()
      this.queryHomeData()
    },
    mounted() {
    },
    methods: {
      seeMore(){
        console.log("点击更多按钮---》");
      },
      queryHomeData(){
        queryHomeData().then(res=>{
          if (res.code == 10000) {
            this.homeData = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      //获取交易订单list
      queryOrderDataList() {
        var data = {
          pageNum: this.currentPage
        }
        orderVoList(data).then(res => {
          //console.log(res)
          if (res.code == 10000) {
            this.orderDataList = res.data
          } else {
            this.$message.error(res.message)
          }

        })
      },
      },

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
