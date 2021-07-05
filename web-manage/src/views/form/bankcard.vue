<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">银行卡管理</div>
        <div class="app-box-changeBox">
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户：</div>
            <el-input placeholder="请输入昵称/姓名/手机号" v-model="input2"></el-input>
          </div>
          <div class="app-box-input-txt">录入日期：</div>
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
        <div class="app-tab-box">
          <el-table
            :data="cardDataList.list"
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
                <span v-if="item.prop==='bankCardId'" >
                             {{scope.row.bankCardId}}
                </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="250">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjTab(scope.$index, scope.row)">编辑
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2">禁用</el-button>
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
                :total=cardDataList.total>
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
  import { queryBankVoData} from '@/api/adminUser'

  export default {
    name: 'bankcard',
    data() {
      return {
        dialogFormVisible: false,
        currentPage: 1,
        input2: '',
        value1: '',
        value2: '',
        tabHead: [
          {
            label: '编号',
            prop: 'bankCardId'
          },
          {
            label: '用户ID',
            prop: 'userId'
          },
          {
            label: '用户姓名',
            prop: 'userRealName'
          },
          {
            label: '开户行',
            prop: 'bankName'
          },
          {
            label: '省份',
            prop: 'province'
          }, {
            label: '城市',
            prop: 'city'
          }, {
            label: '支行',
            prop: 'subBranch'
          }, {
            label: '持卡人',
            prop: 'cardOwnerName'
          }, {
            label: '卡号',
            prop: 'cardNo'
          }, {
            label: '手机号',
            prop: 'cardPhone'
          },
          {
            label: '录入时间',
            prop: 'addDate'
          },
          {
            label: '是否禁用',
            prop: 'cardStatus'
          },
          {
            label: '备注',
            prop: 'remarks'
          }
        ],
        text: '',
        dialogFormVisibleTel: false,
        rules: {
        },
        cardDataList: '',
        //  修改金额
        dialogFormVisibleChange: false,
      }
    },
    created() {
      this.queryBankDataList()
    },
    methods: {
      //查询
      seeOther() {
        this.currentPage = 1
        var time1 = '', time2 = ''
        if (this.value1 != '' && this.value1 != null) {
          time1 = this.value1.getTime()
        }
        if (this.value2 != '' && this.value2 != null) {
          time2 = this.value2.getTime() + 86399000
        }
        if (this.input1 == '') {
          this.input1 = undefined
          var data = {
          userRealName: this.input2,
          pageNum: this.currentPage,
          beginDate: time1,
          endDate: time2,
        }
        queryBankVoData(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.cardDataList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      }},
      seeAll() {
        this.input1 = ''
        this.input2 = ''
        this.currentPage = 1
        this.queryBankDataList()
      },
      //提现明细列表
      queryBankDataList() {
        var data = {
          pageNum: this.currentPage,
        }
        queryBankVoData(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.cardDataList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },
      removeThis() {

      },
      //编辑
      bjTab(index, row) {},
      //分页事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.queryBankDataList()
      },
      refresh() {
        this.currentPage = 1
        this.queryBankDataList()
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },

    },
    filters: {
    }
  }
</script>

<style lang="less">
  #eerchant {
    height: 800px;
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;

    .el-select {
      width: 150px !important;
    }

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

    .el-dialog {
      width: 460px;
    }

    .payNameDiaBox {
      width: 80%;
      margin: 0 auto;

      .el-select {
        width: 100% !important;
      }

      .payNameBnt1 {
        width: 60px;
        height: 28px;
        background: rgba(246, 232, 255, 1);
        opacity: 1;
        line-height: 28px;
        padding: 0;
        color: #3B3269;
        font-size: 13px;
        border-color: rgba(246, 232, 255, 1);
        float: left;
      }

      .payNameBnt2 {
        width: 60px;
        height: 28px;
        background: rgba(92, 56, 130, 1);
        opacity: 1;
        line-height: 28px;
        padding: 0;
        color: #FFFFFF;
        font-size: 13px;
        border-color: rgba(92, 56, 130, 1);
        float: right;
      }

      .payNameBnt2:focus, .payNameBnt2:hover {
        color: #FFFFFF;
        border-color: rgba(92, 56, 130, 1);
        background: rgba(92, 56, 130, 1);
      }

      .payNameBnt1:focus, .payNameBnt1:hover {
        color: #3B3269;
        border-color: rgba(246, 232, 255, 1);
        background: rgba(246, 232, 255, 1);
      }
    }
  }
</style>

