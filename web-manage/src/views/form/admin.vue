<template>
  <div id="recharge" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">充值订单</div>
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
              <el-option label="商户ID" value="2"></el-option>
              <el-option label="操作员ID" value="3"></el-option>
              <el-option label="订单状态" value="4"></el-option>
              <el-option label="银行卡号" value="5"></el-option>
            </el-select>
          </div>
          <div class="app-box-input app-marginR" v-if="region==1">
            <el-input placeholder="请输入订单号" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==2">
            <el-input placeholder="请输入商户ID" v-model="input2"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==3">
            <el-input placeholder="请输入操作员ID" v-model="input3"></el-input>
          </div>
          <div class="app-box-input app-marginR" v-if="region==5">
            <el-input placeholder="请输入银行卡号" v-model="input4"></el-input>
          </div>
          <div class="app-box-select app-marginR" v-if="region==4">
            <el-select v-model="region1" placeholder="订单状态">
              <el-option label="审核中" value="0"></el-option>
              <el-option label="成功" value="1"></el-option>
              <el-option label="失败" value="2"></el-option>
            </el-select>
          </div>

          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="czList.list"
            border
            :max-height="600+'px'"
            :header-cell-style="tabheaderFn"
            style="width: 100%">
            <el-table-column
              v-for="(item,index) in tabHead"
              :prop="item.prop"
              :label="item.label">
              <template slot-scope="scope">
                <!--复制-->
                <span v-if="item.prop==='accountType'">
                             {{scope.row.accountType | accountType}}
                </span>
                <span v-else-if="item.prop==='topUpStatus'">
                             {{scope.row.topUpStatus | topUpStatus}}
                </span>
                <!--复制-->
                <span v-else-if="item.prop==='topUpNumber'" @click="handleCopy(scope.row.topUpNumber)">
                             {{scope.row.topUpNumber}}
                </span>
                <span v-else-if="item.prop==='merchantId'" @click="handleCopy(scope.row.merchantId)">
                             {{scope.row.merchantId}}
                </span>
                <span v-else-if="item.prop==='updateBy'" @click="handleCopy(scope.row.updateBy)">
                             {{scope.row.updateBy}}
                </span>
                <span v-else-if="item.prop==='bankNumber'" @click="handleCopy(scope.row.bankNumber)">
                             {{scope.row.bankNumber}}
                </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="180">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus==0" @click="changeStatus(scope.$index, scope.row)">完成</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.topUpStatus!=0" disabled>完成</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeThis(scope.$index, scope.row)" v-if="scope.row.topUpStatus==0">
                  失败
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" disabled v-if="scope.row.topUpStatus==1 ||scope.row.topUpStatus==2">
                  失败
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
                :total=czList.total>
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
  import { getczList, removeList,changeStatus } from '@/api/chongzhi'

  export default {
    name: 'admin',
    data() {
      return {
        value1: '',
        value2: '',
        currentPage: 1,
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        region: '',
        region1: '',
        tabHead: [
          {
            label: '订单号',
            prop: 'topUpNumber'
          },
          {
            label: '时间',
            prop: 'createTime'
          },
          {
            label: '银行名称',
            prop: 'bankName'
          },
          {
            label: '开户行',
            prop: 'bankAddress'
          },
          {
            label: '账户类型',
            prop: 'accountType'
          },
          {
            label: '银行卡号',
            prop: 'bankNumber'
          },
          {
            label: '充值金额',
            prop: 'amount'
          },
          {
            label: '充值费率',
            prop: 'rate'
          }, {
            label: '手续费',
            prop: 'tip'
          }, {
            label: '商户ID',
            prop: 'merchantId'
          },
          {
            label: '操作员ID',
            prop: 'updateBy'
          },
          {
            label: '订单状态',
            prop: 'topUpStatus'
          },
          {
            label: '备注',
            prop: 'remarks'
          }

        ],
        czList: ''
      }
    },
    created() {
      this.getAllList()
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
        if (this.value1 != ''&&this.value1 != null) {
          time1 = this.value1.getTime()
        }
        if (this.value2 != ''&&this.value2 != null) {
          time2 = this.value2.getTime()+86399000
        }
        var data = {
          startTime: time1,
          endTime: time2,
          topUpNumber: this.input1,
          merchantId: this.input2,
          updateBy: this.input3,
          bankNumber: this.input4,
          topUpStatus: this.region1,
          pageNum: this.currentPage
        }
        getczList(data).then(res => {
          if (res.code == 10000) {
            this.czList = res.data.pt
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
        this.input4 = ''
        this.region1 = ''
        this.region = ''
        this.currentPage = 1
        var data = {
          pageNum: this.currentPage
        }
        getczList(data).then(res => {
          if (res.code == 10000) {
            this.czList = res.data.pt
          } else {
            this.$message.error(res.message)
          }
        })
      },
      //获取充值list
      getAllList() {
        var data = {
          pageNum: this.currentPage
        }
        getczList(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.czList = res.data.pt
          } else {
            this.$message.error(res.message)
          }
        })
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },
      //完成。删除
      changeStatus(index,row){
        console.log(row)

        this.$prompt('请先确定该笔充值到账，填写备注信息, 是否继续?', '备注', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true,
          inputPattern:  /\S/,
          inputErrorMessage: '请填写备注'
        }).then(({ value }) => {
          var data = {
            topUpId: row.topUpId,
            topUpStatus:1,
            amount:row.amount,
            bankId:row.bankId,
            bankName:row.bankName,
            bankNumber:row.bankNumber,
            bankAddress:row.bankAddress,
            cardholderName:row.cardholderName,
            merchantName:this.$store.getters.name,
            remarks:value
          }
          changeStatus(data).then(res => {
            if (res.code == 10000) {
              this.$message.success(res.message)
              this.getAllList()
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已经取消'
          });
        });
      },
      removeThis(index, row) {
        console.log(row)

        this.$prompt('请先确定该笔充值到未到账，填写备注信息, 是否继续?', '备注', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true,
          inputPattern:  /\S/,
          inputErrorMessage: '请填写备注'
        }).then(({ value }) => {
          var data = {
            topUpId: row.topUpId,
            topUpStatus:2,
            amount:row.amount,
            bankId:row.bankId,
            bankName:row.bankName,
            bankNumber:row.bankNumber,
            bankAddress:row.bankAddress,
            cardholderName:row.cardholderName,
            merchantName:this.$store.getters.name,
            remarks:value
          }
          changeStatus(data).then(res => {
            if (res.code == 10000) {
              this.$message.success(res.message)
              this.getAllList()
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已经取消'
          });
        });
      },
      //分页事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.getAllList()
      },
      refresh() {
        this.seeAll()
      },
      changeTj() {
        this.input1 = ''
        this.input2 = ''
        this.input3 = ''
        this.input4 = ''
        this.region1 = ''
        this.value1 = ''
        this.value2 = ''
      }
    },
    filters: {
      accountType(val) {
        return val == 1 ? '支付宝' : '银行卡'
      },
      topUpStatus(val) {
        return val == 0 ? '审核中' : val == 1 ? '成功' : '失败'
      }
    }
  }
</script>

<style lang="less">
  #recharge {
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
