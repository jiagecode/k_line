<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">资金报表</div>
        <div class="app-box-changeBox">
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户名称：</div>
            <el-input placeholder="请输入姓名/昵称/手机号" v-model="input1"></el-input>
          </div>
          <div class="app-box-input-txt">结算时间：</div>
          <div class="app-box-date app-marginR">
            <el-date-picker class="app-marginR"
                            v-model="value1"
                            type="date"
                            placeholder="请选择开始时间">
            </el-date-picker>
          </div>
          <el-date-picker class="app-marginR"
                          v-model="value2"
                          type="date"
                          placeholder="请选择结束时间">
          </el-date-picker>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-box-changeBox" style="margin: -25px 0 -10px 0;">
          <!--入金总额-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">入金总额：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.allInMoney}}</div>
          </div>
          <!--出金总额-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">出金总额：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.allOutMoney}}</div>
          </div>
          <!--佣金总额-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">佣金总额：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.allCommission}}</div>
          </div>
          <!--当日盈亏-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">当日盈亏：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.todayMoney}}</div>
          </div>
          <!--出金总额-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">出金总额：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.allOutMoney}}</div>
          </div>
          <!--历史盈亏-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">历史盈亏：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{moneyDataAll.allMoney}}</div>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="moneyDataList.list"
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
              :min-width="columnWidth(item.prop)"
              :prop="item.prop"
              :label="item.label">
              <template slot-scope="scope">
                <span v-if="item.prop==='userRealName'">
                             {{ scope.row.userRealName }}
                </span>
                <span v-if="item.prop==='agentName'">
                             {{ scope.row.agentName }}
                </span>
                <span v-if="item.prop==='allInMoney'">
                             {{ scope.row.allInMoney }}
                </span>
                <span v-if="item.prop==='allInNum'">
                             {{ scope.row.allInNum }}
                </span>
                <span v-if="item.prop==='handInMoney'">
                             {{ scope.row.handInMoney }}
                </span>
                <span v-if="item.prop==='allOutMoney'">
                             {{ scope.row.allOutMoney }}
                </span>
                <span v-if="item.prop==='allOutNum'">
                             {{ scope.row.allOutNum }}
                </span>
                <span v-if="item.prop==='allCheckMoney'">
                   {{ showTodayMoneyDesc(scope.row.allCheckMoney) }}
                </span>
                <span v-if="item.prop==='allCommission'">
                             {{ scope.row.allCommission }}
                </span>
                <span v-if="item.prop==='allBonus'">
                             {{ scope.row.allBonus }}
                </span>
                <span v-if="item.prop==='accountMoney'">
                             {{ scope.row.accountMoney }}
                </span>
                <span v-if="item.prop==='reallyMoney'">
                             {{ scope.row.reallyMoney }}
                </span>
                <span v-if="item.prop==='reallyInMoney'">
                             {{ scope.row.reallyInMoney }}
                </span>
                <span v-if="item.prop==='todayMoney'">
                             {{ showTodayMoneyDesc(scope.row.todayMoney) }}
                </span>
                <span v-if="item.prop==='allMoney'">
                             {{ scope.row.allMoney }}
                </span>
                <span v-if="item.prop==='allFee'">
                             {{ scope.row.allFee }}
                </span>
                <!-- 正常的其他列 -->
<!--                <span v-else>{{ scope.row[item.prop] }}</span>-->
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
                :total=moneyDataList.total>
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
import {moneyTableData,moneyTableAll} from '@/api/adminUser'

export default {
  name: 'bonus',
  data() {
    return {
      value1: '',
      value2: '',
      input1:'',
      currentPage: 1,
      tabHead: [
        {
          label: '用户',
          prop: 'userRealName'
        },
      {
          label: '代理商',
          prop: 'agentName'
        },
        {
          label: '入金总额',
          prop: 'allInMoney'
        }, {
          label: '入金次数',
          prop: 'allInNum'
        }, {
          label: '手动入金',
          prop: 'handInMoney'
        }, {
          label: '出金总额',
          prop: 'allOutMoney'
        }, {
          label: '出金次数',
          prop: 'allOutNum'
        }, {
          label: '出金审核中',
          prop: 'allCheckMoney'
        }, {
          label: '佣金',
          prop: 'allCommission'
        }, {
          label: '红利',
          prop: 'allBonus'
        }, {
          label: '当前余额',
          prop: 'accountMoney'
        }, {
          label: '实际余额',
          prop: 'reallyMoney'
        }, {
          label: '净入金',
          prop: 'reallyInMoney'
        }, {
          label: '当日盈亏',
          prop: 'todayMoney'
        }, {
          label: '总盈亏',
          prop: 'allMoney'
        }, {
          label: '总手续费',
          prop: 'allFee'
        }
      ],
      moneyDataList: '',
      moneyDataAll: ''
    }
  },
  created() {
    this.queryMoneyDataList()
    this.queryTotal()
  },
  methods: {
    showTodayMoneyDesc(todayMoney){
      return (todayMoney ==null || todayMoney ==undefined) ? 0 :todayMoney;
    },
    // 动态设置宽度
    columnWidth(item) {
      let widthStr = ''
      // if(item)
      switch (item) {
        case 'allCheckMoney':
          widthStr = '100'
          break
        default:
          widthStr = '80'
      }
      return widthStr
    },
    //复制
    handleCopy(e) {
      this.copy(e)
    },
    //查询
    seeOther() {
      this.currentPage = 1
      var time1 = '', time2 = ''
      console.log(this.value1)
      if (this.value1 != '' && this.value1 != null) {
        time1 = this.value1.getTime()
      }
      if (this.value2 != '' && this.value2 != null) {
        time2 = this.value2.getTime() + 86399000
      }
      if (this.input1 == '') {
        this.input1 = undefined
      }
      var data = {
        beginDate: time1,
        endDate: time2,
        userRealName:this.input1,
        pageNum: this.currentPage
      }
      moneyTableData(data).then(res => {
        //console.log(res)
        if (res.code == 10000) {
          this.moneyDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
      moneyTableAll(data).then(res=>{
        if (res.code == 10000) {
          this.moneyDataAll = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    seeAll() {
      this.value1 = ''
      this.value2 = ''
      this.currentPage = 1
      var data = {
        pageNum: this.currentPage
      }
      moneyTableData(data).then(res => {
        //   console.log(res)
        if (res.code == 10000) {
          this.moneyDataList = res.data
        } else {
          this.$message.error(res.message)
        }
      this.queryTotal();
      })
    },
    changeTj() {
    },
    queryTotal(){
      var data = {}
      moneyTableAll(data).then(res=>{
        if (res.code == 10000) {
          this.moneyDataAll = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    //获取交易订单list
    queryMoneyDataList() {
      var data = {
        pageNum: this.currentPage
      }
      moneyTableData(data).then(res => {
        console.log(res)
        if (res.code == 10000) {
          this.moneyDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },
    //完成，取消
    sucBtn(index, row) {
    },
    cancelBtn(index, row) {
    },
    tabheaderFn() {
      return 'background:#F6F7FB;color:#3B3269'
    },
    removeThis(index, row) {
      console.log(row)
      this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var data = {
          transactionOrderId: row.transactionOrderId,
          flag: 1
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    //分页事件
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)

    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.queryMoneyDataList()
    },
    refresh() {
      this.currentPage = 1
      this.queryMoneyDataList()
    }

  },
  filters: {
  }
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
