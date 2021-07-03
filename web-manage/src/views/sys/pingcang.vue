<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">平仓日志</div>
        <div class="app-box-changeBox">
          <div class="app-box-date app-marginR">
            <el-date-picker class="app-marginR"
                            v-model="value1"
                            type="date"
                            placeholder="选择日期">
            </el-date-picker>
          </div>
          <el-date-picker class="app-marginR"
                          v-model="value2"
                          type="date"
                          placeholder="选择日期">
          </el-date-picker>
          <div class="app-box-select app-marginR">
            <el-select v-model="region" placeholder="查询条件" @change="changeTj">
              <el-option label="订单号" value="1"></el-option>
              <el-option label="代付类型" value="2"></el-option>
              <el-option label="代付方式" value="3"></el-option>
              <el-option label="处理结果" value="4"></el-option>
              <!--              <el-option label="回调状态" value="5"></el-option>-->
              <el-option label="商户ID" value="6"></el-option>
              <el-option label="操作员ID" value="7"></el-option>
            </el-select>
          </div>
          <div class="app-box-input app-marginR" v-if="region==1">
            <el-input placeholder="请输入订单号" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==6">
            <el-input placeholder="请输入商户ID" v-model="input2"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==7">
            <el-input placeholder="请输入操作员ID" v-model="input3"></el-input>
          </div>
          <div class="app-box-select app-marginR" v-if="region==2">
            <el-select v-model="region1" placeholder="代付类型">
              <el-option label="银行卡" value="2"></el-option>
              <el-option label="支付宝" value="1"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==3">
            <el-select v-model="region2" placeholder="代付方式">
              <el-option label="单笔" value="1"></el-option>
              <el-option label="批量" value="2"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==4">
            <el-select v-model="region3" placeholder="处理结果">
              <el-option label="失败" value="0"></el-option>
              <el-option label="成功" value="1"></el-option>
              <el-option label="未处理" value="2"></el-option>
            </el-select>
          </div>
          <div class="app-box-select app-marginR" v-if="region==5">
            <el-select v-model="region4" placeholder="回调状态">
              <el-option label="已回调" value="1"></el-option>
              <el-option label="未回调" value="2"></el-option>
            </el-select>
          </div>

          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="jyList.list"
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
                <span v-if="item.prop==='accountNumber'">
                             {{scope.row.accountNumber | accountNumber}}
                </span>
                <span v-else-if="item.prop==='payNumber'" @click="handleCopy(scope.row.payNumber)">
                             {{scope.row.payNumber}}
                </span>
                <span v-else-if="item.prop==='merchantId'" @click="handleCopy(scope.row.merchantId)">
                             {{scope.row.merchantId}}
                </span>
                <span v-else-if="item.prop==='createBy'" @click="handleCopy(scope.row.createBy)">
                             {{scope.row.createBy}}
                </span>
                <span v-else-if="item.prop==='updateBy'" @click="handleCopy(scope.row.updateBy)">
                             {{scope.row.updateBy}}
                </span>
                <span v-else-if="item.prop==='payStyle'">
                             {{scope.row.payStyle | payStyle}}
                </span>
                <span v-else-if="item.prop==='payType'">
                             {{scope.row.payType | payType}}
                </span>
                <span v-else-if="item.prop==='callbackStatus'">
                             {{scope.row.callbackStatus | callbackStatus}}
                </span>
                <span v-else-if="item.prop==='topUpStatus'">
                             {{scope.row.topUpStatus | topUpStatus}}
                </span>
                <span v-else-if="item.prop==='resultStatus'">
                             {{scope.row.resultStatus | resultStatus}}
                </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="180">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus==0" @click="sucBtn(scope.$index, scope.row)">完成</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.topUpStatus==0" @click="cancelBtn(scope.$index, scope.row)">取消</el-button>

                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus!=0" disabled>完成</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.topUpStatus!=0" disabled>取消</el-button>
                <!--                <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeThis(scope.$index, scope.row)">-->
                <!--                  删除-->
                <!--                </el-button>-->

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
                :total=jyList.total>
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
  import { getjyList, removejyList,changeList,caozuo } from '@/api/jiaoyi'

  export default {
    name: 'log',
    data() {
      return {
        value1: '',
        value2: '',
        currentPage: 1,
        input1: undefined,
        input2: undefined,
        input3: undefined,
        region: '',
        region1: undefined,
        region2: undefined,
        region3: undefined,
        region4: undefined,
        tabHead: [
          {
            label: '订单号',
            prop: 'payNumber'
          },
          {
            label: '时间',
            prop: 'createTime'
          },
          {
            label: '代付类型',
            prop: 'payType'
          },
          {
            label: '代付方式',
            prop: 'payStyle'
          },
          {
            label: '收款账户',
            prop: 'payAccount'
          },
          // {
          //   label: '订单金额',
          //   prop: 'orderAmount'
          // },
          {
            label: '代付金额',
            prop: 'payAmount'
          },
          {
            label: '代付手续费',
            prop: 'tip'
          },
          {
            label: '订单状态',
            prop: 'topUpStatus'
          },{
            label: '处理结果',
            prop: 'resultStatus'
          }, {
            label: '商户ID',
            prop: 'merchantId'
          },
          {
            label: '操作员ID',
            prop: 'updateBy'
          }

        ],
        jyList: ''
      }
    },
    created() {
      this.getallList()
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
          time2 = this.value2.getTime()+86399000
        }
        if (this.input1 == '') {
          this.input1 = undefined
        }
        var data = {
          startTime: time1,
          endTime: time2,
          payNumber: this.input1,
          merchantId: this.input2,
          updateBy: this.input3,
          payType: this.region1,
          payStyle: this.region2,
          resultStatus: this.region3,
          pageNum: this.currentPage
        }
        getjyList(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.jyList = res.data.pp
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
        getjyList(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.jyList = res.data.pp
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
      getallList() {
        var data = {
          pageNum: this.currentPage
        }
        getjyList(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.jyList = res.data.pp
          } else {
            this.$message.error(res.message)
          }

        })
      },
      //完成，取消
      sucBtn(index,row){
        console.log(row.payId)
        var data={
          payId:row.payId,
          resultStatus:1,
        }
        caozuo(data).then(res=>{
          if(res.code==10000){
            this.getallList()
          }else {
            this.$message.error(res.message)
          }
        })
      },
      cancelBtn(index,row){
        console.log(row.payId)
        var data={
          payId:row.payId,
          resultStatus:0,
        }
        caozuo(data).then(res=>{
          if(res.code==10000){
            this.getallList()
          }else {
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
              this.getallList()
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
        this.getallList()
      },
      refresh() {
        this.currentPage = 1
        this.getallList()
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
        return val == 0 ? '处理中':'处理完成'
      },
      resultStatus(val){
        return val == 0 ? '失败':val==1?'成功':'未处理'
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
