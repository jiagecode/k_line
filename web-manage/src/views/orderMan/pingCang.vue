<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">平仓日志</div>
        <div class="app-box-changeBox">
          <div class="app-box-input-txt">平仓日期：</div>
          <div class="app-box-date app-marginR">
            <el-date-picker class="app-marginR"
                            v-model="value1"
                            type="date"
                            placeholder="默认查当日">
            </el-date-picker>
          </div>
          <el-date-picker class="app-marginR"
                          v-model="value2"
                          type="date"
                          placeholder="默认查当日">
          </el-date-picker>
          <div class="app-box-select app-marginR">
            <el-select v-model="region" placeholder="查询条件" @change="changeTj">
              <el-option label="盈亏类型" value="4"></el-option>
              <el-option label="用户信息" value="7"></el-option>
            </el-select>
          </div>

          <div class="app-box-input app-marginR" v-if="region==7">
            <el-input placeholder="请输入用户昵称/姓名/电话" v-model="input2"></el-input>
          </div>
          <div class="app-box-select app-marginR" v-if="region==4">
            <el-select v-model="region2" placeholder="盈亏类型">
              <el-option label="盈利" value="1"></el-option>
              <el-option label="亏损" value="2"></el-option>
              <el-option label="全部" value="3"></el-option>
            </el-select>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
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
            <!--   <el-table-column
                 label="操作"
                 width="180">
                 <template slot-scope="scope">
                   <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus==0"
                              @click="sucBtn(scope.$index, scope.row)">完成
                   </el-button>
                   <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.topUpStatus==0"
                              @click="cancelBtn(scope.$index, scope.row)">取消
                   </el-button>

                   <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus!=0" disabled>完成
                   </el-button>
                   <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.topUpStatus!=0" disabled>取消
                   </el-button>
                 </template>
               </el-table-column>
               -->
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
import {removejyList, caozuo} from '@/api/jiaoyi'
import {pingCangDataVo} from '@/api/adminUser'

export default {
  name: 'pingCang',
  data() {
    return {
      value1: '',
      value2: '',
      currentPage: 1,
      input2: undefined,
      region: '',
      region2: undefined,
      //  "pageSize": 10,
      tabHead: [
        {
          label: '编号',
          prop: 'recordId'
        }, {
          label: '用户ID',
          prop: 'userId'
        }, {
          label: '交易用户',
          prop: 'userRealName'
        }, {
          label: '订单ID',
          prop: 'orderId'
        }, {
          label: '增加金额',
          prop: 'changeMoney'
        }, {
          label: '增加积分',
          prop: 'integral'
        }, {
          label: '平仓后余额',
          prop: 'afterMoney'
        }, {
          label: '平仓时间',
          prop: 'addDate'
        }
      ],
      recordDataList: ''
    }
  },
  created() {
    this.queryRecordDataList()
  },
  methods: {
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
        queryType: 1,
        userRealName: this.input2,
        queryDataFlag: this.region2,
        pageNum: this.currentPage
      }
      pingCangDataVo(data).then(res => {
        //console.log(res)
        if (res.code == 10000) {
          this.recordDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },
    seeAll() {
      this.value1 = ''
      this.value2 = ''
      this.input2 = ''
      this.region2 = ''
      this.region = ''
      this.currentPage = 1
      var data = {
        queryType: 1,
        pageNum: this.currentPage
      }
      pingCangDataVo(data).then(res => {
        //   console.log(res)
        if (res.code == 10000) {
          this.recordDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },
    changeTj() {
      this.input2 = ''
      this.region2 = ''
    },
    //获取交易订单list
    queryRecordDataList() {
      var data = {
        queryType: 1,
        pageNum: this.currentPage
      }
      pingCangDataVo(data).then(res => {
        console.log(res)
        if (res.code == 10000) {
          this.recordDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },
    //完成，取消
    sucBtn(index, row) {
      console.log(row.payId)
      var data = {
        payId: row.payId,
        resultStatus: 1
      }
      caozuo(data).then(res => {
        if (res.code == 10000) {
          this.queryRecordDataList()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    cancelBtn(index, row) {
      console.log(row.payId)
      var data = {
        payId: row.payId,
        resultStatus: 0,
      }
      caozuo(data).then(res => {
        if (res.code == 10000) {
          this.queryRecordDataList()
        } else {
          this.$message.error(res.message)
        }
      })
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
        removejyList(data).then(res => {
          if (res.code == 10000) {
            this.$message.success(res.message)
            this.currentPage = 1
            this.queryRecordDataList()
          } else {
            this.$message.error(res.message)
          }
        })
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
      this.queryRecordDataList()
    },
    refresh() {
      this.currentPage = 1
      this.queryRecordDataList()
    }

  },
  filters: {
    accountNumber(val) {
      return val == 1 ? '支付宝' : '银行卡'
    },
    payStyle(val) {
      return val == 1 ? '单笔' : '批量'
    },
    payType(val) {
      return val == 1 ? '支付宝' : '银行卡'
    },
    callbackStatus(val) {
      return val == 1 ? '已回调' : '未回调'
    },
    topUpStatus(val) {
      return val == 0 ? '处理中' : '处理完成'
    },
    resultStatus(val) {
      return val == 0 ? '失败' : val == 1 ? '成功' : '未处理'
    }
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
