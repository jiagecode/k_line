<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">用户持仓</div>
        <div class="app-tab-box">
          <el-table
            :data="recordDataList.list"
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
                <span v-if="item.prop==='positionId'">
                             {{ scope.row.positionId }}
                </span>
                <span v-if="item.prop==='userId'">
                             {{ scope.row.userId }}
                </span>
                <span v-if="item.prop==='userRealName'">
                             {{ scope.row.userRealName }}
                </span>
                <span v-if="item.prop==='agentName'">
                             {{ scope.row.agentName }}
                </span>
                <span v-if="item.prop==='orderId'">
                             {{ scope.row.orderId }}
                </span>
                <span v-if="item.prop==='skuName'">
                             {{ scope.row.skuName }}
                </span>
                <span v-if="item.prop==='investType'">
                             {{ showDesc(1,scope.row.investType)}}
                </span>
                <span v-if="item.prop==='beaginPrice'">
                             {{ scope.row.beaginPrice }}
                </span>
                <span v-if="item.prop==='beginDate'">
                             {{ scope.row.beginDate }}
                </span>
                <span v-if="item.prop==='orderCycle'">
                             {{ scope.row.orderCycle }}
                </span>
                <span v-if="item.prop==='settlementDate'">
                             {{ scope.row.settlementDate }}
                </span>
                <span v-if="item.prop==='investAmount'">
                             {{ scope.row.investAmount }}
                </span>
                <span v-if="item.prop==='expectedReturn'">
                             {{ scope.row.expectedReturn }}
                </span>
                <span v-if="item.prop==='winFlag'">
                             {{ showDesc(2,scope.row.winFlag) }}
                </span>
<!--                <span v-else>{{ scope.row[item.prop] }}</span>-->
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="300">
              <template slot-scope="scope">
                <div style="display: flex; justify-content: flex-start;">
                  <el-button type="primary" class="app-tab-btn app-tab-btn2" v-show="scope.row.winFlag != 1"
                             @click="changeWin(1, scope.row.orderId)">控赢
                  </el-button>
                  <el-button type="primary" class="app-tab-btn app-tab-btn2" v-show="scope.row.winFlag != 2"
                             @click="changeWin(2, scope.row.orderId)">控输
                  </el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <div class="block">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size=10
                background
                layout="prev, pager, next, jumper"
                :total=recordDataList.total>
              </el-pagination>
            </div>
            <div class="refresh">
              <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {pullPosList, editWinFlag} from '@/api/adminUser'

export default {
  name: 'posiVo',
  data() {
    return {
      value1: '',
      value2: '',
      currentPage: 1,
      input2: undefined,
      region: '',
      region2: undefined,
      tabHead: [
        {
          label: '持仓编号',
          prop: 'positionId'
        }, {
          label: '用户ID',
          prop: 'userId'
        }, {
          label: '用户姓名',
          prop: 'userRealName'
        }, {
          label: '代理名称',
          prop: 'agentName'
        }, {
          label: '订单ID',
          prop: 'orderId'
        }, {
          label: '商品名称',
          prop: 'skuName'
        }, {
          label: '投资方向',
          prop: 'investType'
        }, {
          label: '买入价格',
          prop: 'beaginPrice'
        }, {
          label: '买入时间',
          prop: 'beginDate'
        }, {
          label: '订单周期',
          prop: 'orderCycle'
        }, {
          label: '结算时间',
          prop: 'settlementDate'
        }, {
          label: '投资金额',
          prop: 'investAmount'
        }, {
          label: '预估收益',
          prop: 'expectedReturn'
        }, {
          label: '预估输赢',
          prop: 'winFlag'
        }
      ],
      recordDataList: ''
    }
  },
  created() {
    this.queryRecordDataList()
  },
  methods: {

    changeWin(winFlag, orderId) {
      // console.log(row)
      this.$confirm('此操作改变此订单输赢结果, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var data={winFlag:winFlag,
        orderId:orderId}
        editWinFlag(data).then(res=>{
          if (res.code == 10000) {
            var re = res.data;
            if(re ==1){
              this.$message.info('调整成功');
            }else if(re == -2){
              this.$message.error('订单已结算无法调整');
            }
          }else {
            this.$message.error('调整失败')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    showDesc(type,val){
      if(type == 1){
        return val == 1? "买涨":"买跌";
      }else {
        return  val == 0?"未知":val == 1?"赢":"输";
      }
    },
    changeTj() {
      this.input2 = ''
      this.region2 = ''
    },
    //获取交易订单list
    queryRecordDataList() {
      var data = {
        pageNum: this.currentPage
      }
      pullPosList(data).then(res => {
        console.log(res)
        if (res.code == 10000) {
          this.recordDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },

    tabheaderFn() {
      return 'background:#F6F7FB;color:#3B3269'
    },

    //分页事件
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)

    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.queryRecordDataList()
    },
    refresh() {
      this.currentPage = 1
      this.queryRecordDataList()
    }

  },

}
</script>

<style lang="less">
#transaction {
  height: 800px;
  opacity: 1;
  margin: 0 auto;
  margin-top: 30px;

  .el-table {
    border-radius: 10px;
    text-align: center;

    td, tr, th {
      text-align: center;
    }

    .removeBnt {
      font-size: 20px;
      color: #F75F78;
      cursor: pointer;
    }
  }

  .app-box-date, .el-date-picker, .el-date-editor {
    width: 140px !important;
  }

  .app-box-date > .el-input__inner {
    width: 140px !important;
  }
}
</style>
