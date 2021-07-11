<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">订单流水</div>
        <div class="app-box-changeBox">
          <div class="app-box-input-txt">订单日期：</div>
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
              <el-option label="用户编号" value="1"></el-option>
              <el-option label="订单编号" value="2"></el-option>
              <el-option label="用户类型" value="3"></el-option>
              <el-option label="盈亏类型" value="4"></el-option>
              <el-option label="投资类型" value="5"></el-option>
              <el-option label="订单状态" value="6"></el-option>
              <el-option label="用户信息" value="7"></el-option>
            </el-select>
          </div>
          <div class="app-box-input app-marginR" v-if="region==1">
            <el-input placeholder="请输入用户编号" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==7">
            <el-input placeholder="请输入用户昵称/姓名/电话" v-model="input2"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==2">
            <el-input placeholder="请输入订单号" v-model="input3"></el-input>
          </div>
          <div class="app-box-select app-marginR" v-if="region==3">
            <el-select v-model="region1" placeholder="用户类型">
              <el-option label="客户" value="1"></el-option>
              <el-option label="代理商" value="2"></el-option>
              <el-option label="全部" value="3"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==4">
            <el-select v-model="region2" placeholder="盈亏类型">
              <el-option label="盈利" value="1"></el-option>
              <el-option label="亏损" value="2"></el-option>
              <el-option label="全部" value="3"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==5">
            <el-select v-model="region3" placeholder="投资类型">
              <el-option label="买涨" value="1"></el-option>
              <el-option label="买跌" value="2"></el-option>
              <el-option label="全部" value="3"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==6">
            <el-select v-model="region4" placeholder="订单状态">
              <el-option label="持仓" value="1"></el-option>
              <el-option label="平仓" value="2"></el-option>
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
            :data="orderDataList.list"
            border
            :max-height="600+'px'"
            :header-cell-style="tabheaderFn"
            style="width: 100%"
            lazy
            row-key="orderId"
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
                <span v-if="item.prop==='accountId'">
                             {{ scope.row.accountId }}
                </span>
                <span v-if="item.prop==='userRealName'">
                             {{ scope.row.userRealName }}
                </span>
                <span v-if="item.prop==='addDate'">
                             {{ scope.row.addDate }}
                </span>
                <span v-if="item.prop==='skuName'">
                             {{ scope.row.skuName }}
                </span>
                <span v-if="item.prop ==='orderStatus'" style="color: red">
                 {{ showOrderStatus(scope.row.orderStatus)}}
                </span>
                <span v-if="item.prop==='investType'">
                             {{ showInvestTypeDesc(scope.row.investType)}}
                </span>
                <span v-if="item.prop==='orderCycle'">
                             {{ scope.row.orderCycle }}
                </span>
                <span v-if="item.prop==='inPoint'">
                             {{ scope.row.inPoint }}
                </span>
                <span v-if="item.prop==='outPoint'">
                             {{ scope.row.outPoint }}
                </span>
                <span v-if="item.prop==='investAmount'">
                             {{ scope.row.investAmount }}
                </span>
                <span v-if="item.prop==='resultMoney'">
                             {{ scope.row.resultMoney }}
                </span>
                <span v-if="item.prop==='userMoney'">
                             {{ scope.row.userMoney }}
                </span>
                <span v-if="item.prop==='todayMoney'">
                             {{ scope.row.todayMoney }}
                </span>
                <span v-if="item.prop==='allMoney'">
                             {{ scope.row.allMoney }}
                </span>
                <span v-if="item.prop==='afterMoney'">
                             {{ scope.row.afterMoney }}
                </span>
                <span v-if="item.prop==='agentName'">
                             {{ scope.row.agentName }}
                </span>
                <!-- 正常的其他列 -->
<!--                <span v-else>{{ scope.row[item.prop] }}</span>-->
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="180">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2"
                           @click="showDetail(scope.$index, scope.row)">详情
                </el-button>
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
                :total=orderDataList.total>
              </el-pagination>
            </div>
            <div class="refresh">
              <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
            </div>
          </div>
        </div>
        <!--订单详情-->
        <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
                   :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>订单详情</span>
            </span>
          <div class="payNameDiaBox">
            <el-form  :model="orderDetail" label-width="130px" >
              <el-form-item label="用户名：" prop="userRealName" >
                <el-input v-model="orderDetail.userRealName" disabled></el-input>
              </el-form-item>
              <el-form-item label="用户编号：" prop="userId" >
                <el-input v-model="orderDetail.userId" disabled></el-input>
              </el-form-item>
              <el-form-item label="用户余额：" prop="userMoney">
                <el-input v-model="orderDetail.userMoney" disabled></el-input>
              </el-form-item>
              <el-form-item label="订单编号：" prop="orderId">
                <el-input v-model.number="orderDetail.orderId"  disabled></el-input>
              </el-form-item>
              <el-form-item label="商品：" prop="skuName">
                <el-input v-model.number="orderDetail.skuName"  disabled></el-input>
              </el-form-item>
              <el-form-item label="订单状态：" prop="orderStatusDesc">
                <el-input v-model.number="orderDetail.orderStatusDesc" disabled></el-input>
              </el-form-item>
              <el-form-item label="入仓价格：" prop="inPoint">
                <el-input v-model.number="orderDetail.inPoint" type="number" disabled></el-input>
              </el-form-item>
              <el-form-item label="平仓价格：" prop="outPoint">
                <el-input v-model.number="orderDetail.outPoint" type="number" disabled></el-input>
              </el-form-item>
              <el-form-item label="入仓金额：" prop="investAmount">
                <el-input v-model.number="orderDetail.investAmount" type="number" disabled></el-input>
              </el-form-item>
              <el-form-item label="入仓时间：" prop="addDate">
                <el-input v-model.number="orderDetail.addDate" disabled></el-input>
              </el-form-item>
              <el-form-item label="平仓时间：" prop="editDate">
                <el-input v-model.number="orderDetail.editDate"  disabled></el-input>
              </el-form-item>
              <el-form-item label="本单盈亏：" prop="subMoney">
                <el-input v-model.number="orderDetail.subMoney" type="number" disabled></el-input>
              </el-form-item>
              <el-form-item label="备注：">
                <el-input v-model="orderDetail.remarks" disabled></el-input>
              </el-form-item>
              <el-form-item>
                <el-button class="payNameBnt2" @click="closeDetail()">确定</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import {removejyList, caozuo} from '@/api/jiaoyi'
import {orderVoList} from '@/api/adminUser'

export default {
  name: 'OrderWater',
  data() {
    return {
      value1: '',
      value2: '',
      currentPage: 1,
      dialogFormVisible : false,
      orderDetail:{},
      input1: undefined,
      input2: undefined,
      input3: undefined,
      region: '',
      region1: undefined,
      region2: undefined,
      region3: undefined,
      region4: undefined,
      //  "pageSize": 10,
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
      orderDataList: ''
    }
  },
  created() {
    this.queryOrderDataList()
  },
  methods: {
    //详情
    showDetail(index,row){
      this.orderDetail = row;
      this.dialogFormVisible = true;
    },
    //复制
    handleCopy(e) {
      this.copy(e)
    },
    showIvestType(investType){
      return investType === 1;
    },
    showInvestTypeDesc(investType){
      return this.showIvestType(investType)? "买涨":"买跌";
    },
    showOrderStatus(orderStatus){
      return orderStatus === 1 ? "持仓":"平仓";
    },
    ai_dialog_close () {
      this.dialogFormVisible = false
      this.orderDetail={}
    },
    closeDetail(){
      this.dialogFormVisible = false;
      this.orderDetail={}
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
        userId: this.input1,
        userRealName: this.input2,
        orderId: this.input3,
        userType: this.region1,
        queryDataFlag: this.region2,
        investType: this.region3,
        orderStatus: this.region3,
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
    seeAll() {
      this.value1 = ''
      this.value2 = ''
      this.input1 = ''
      this.input2 = ''
      this.input3 = ''
      this.region1 = ''
      this.region2 = ''
      this.region3 = ''
      this.region4 = ''
      this.region = ''
      this.currentPage = 1
      var data = {
        pageNum: this.currentPage
      }
      orderVoList(data).then(res => {
        //   console.log(res)
        if (res.code == 10000) {
          this.orderDataList = res.data
        } else {
          this.$message.error(res.message)
        }

      })
    },
    changeTj() {
      this.input1 = ''
      this.input2 = ''
      this.input3 = ''
      this.region1 = ''
      this.region2 = ''
      this.region3 = ''
      this.region4 = ''
    },
    //获取交易订单list
    queryOrderDataList() {
      var data = {
        pageNum: this.currentPage
      }
      orderVoList(data).then(res => {
        console.log(res)
        if (res.code == 10000) {
          this.orderDataList = res.data
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
        resultStatus: 1,
      }
      caozuo(data).then(res => {
        if (res.code == 10000) {
          this.queryOrderDataList()
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
          this.queryOrderDataList()
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
            this.queryOrderDataList()
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
      this.queryOrderDataList()
    },
    refresh() {
      this.currentPage = 1
      this.queryOrderDataList()
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
