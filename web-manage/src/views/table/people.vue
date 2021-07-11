<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">个人报表</div>
        <div class="app-box-changeBox">
          <div class="app-box-input-txt">订单时间：</div>
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
                  <button @click="queryDet(scope.row.orderId)">查看订单详情</button>
                </span>
                <!-- 正常的其他列 -->
                <span v-else>{{ scope.row[item.prop] }}</span>
              </template>
            </el-table-column>
<!--            <el-table-column
              label="操作"
              width="180">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus==0"
                           @click="sucBtn(scope.$index, scope.row)">完成
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.topUpStatus==0"
                           @click="cancelBtn(scope.$index, scope.row)">取消
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
import {pingCangDataVo,orderDetail} from '@/api/adminUser'

export default {
  name: 'people',
  data() {
    return {
      dialogFormVisible:false,
      value1: '',
      value2: '',
      currentPage: 1,
      tabHead: [
        {
          label: '编号',
          prop: 'recordId'
        }, {
          label: '代理名称',
          prop: 'agentName'
        }, {
          label: '订单ID',
          prop: 'orderId'
        }, {
          label: '佣金金额',
          prop: 'commissionMoney'
        }, {
          label: '备注',
          prop: 'remarks'
        }, {
          label: '时间',
          prop: 'addDate'
        }
      ],
      recordDataList: '',
      orderDetail:{},
    }
  },
  created() {
    this.queryRecordDataList()
  },
  methods: {
    //查询订单详情
    queryDet(orderId){
      var par = {"orderId":orderId}
      orderDetail(par).then(res =>{
        if (res.code == 10000) {
          this.orderDetail = res.data
          this.dialogFormVisible = true;
        } else {
          this.$message.error(res.message)
        }
      })
    },
    ai_dialog_close () {
      this.dialogFormVisible = false
      this.orderDetail={}
    },
    closeDetail(){
      this.dialogFormVisible = false;
      this.orderDetail={}
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
        queryType: 2,
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
      this.currentPage = 1
      var data = {
        queryType: 2,
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
    },
    //获取交易订单list
    queryRecordDataList() {
      var data = {
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
      this.queryRecordDataList()
    },
    refresh() {
      this.currentPage = 1
      this.queryRecordDataList()
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
