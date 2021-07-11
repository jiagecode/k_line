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
      </div>
    </div>
  </div>
</template>

<script>
import {pingCangDataVo} from '@/api/adminUser'

export default {
  name: 'people',
  data() {
    return {
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
