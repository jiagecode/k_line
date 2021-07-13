<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">银行卡管理</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia()">添加银行卡</el-button>
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
              :min-width="columnWidth(item.prop)"
              :prop="item.prop"
              :label="item.label">
              <template slot-scope="scope">
                <!--复制-->
                <span v-if="item.prop==='bankCardId'" >
                             {{scope.row.bankCardId}}
                </span>
                <!-- 是否 禁用-->
                <span v-else-if="item.prop==='cardStatus'" >
                             {{scope.row.cardStatus === 0 ? "否" : "是"}}
                </span>
                <!-- 正常的其他列 -->
                <span v-else >{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="80">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjTab(scope.$index, scope.row)">编辑
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
                :total=cardDataList.total>
              </el-pagination>
            </div>
            <div class="refresh">
              <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
            </div>
          </div>


        </div>
        <!--编辑-->
        <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
                   :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>{{text}}</span>
            </span>
          <div class="payNameDiaBox">
            <div style="margin-left: -80px;">
              <el-form ref="form" :model="form" label-width="200px" :rules="rules">
  <!--              <el-form-item label="编号：" prop="bankCardId" v-show="bjShow" >-->
  <!--                <el-input v-model="form.bankCardId" type="number" ></el-input>-->
  <!--              </el-form-item>-->
  <!--              <el-form-item label="用户编号：" prop="userId" >-->
  <!--                <el-input v-model="form.userId" type="number"></el-input>-->
  <!--              </el-form-item>-->
                <el-form-item label="开户行：" prop="bankName">
                  <el-input v-model="form.bankName" ></el-input>
                </el-form-item>
                <el-form-item label="省份：" prop="province">
                  <el-input v-model.number="form.province"></el-input>
                </el-form-item>
                <el-form-item label="城市：" prop="city">
                  <el-input v-model.number="form.city"></el-input>
                </el-form-item>
                <el-form-item label="支行：" prop="subBranch">
                  <el-input v-model.number="form.subBranch"></el-input>
                </el-form-item>
                <el-form-item label="开户名：" prop="cardOwnerName">
                  <el-input v-model.number="form.cardOwnerName"></el-input>
                </el-form-item>
                <el-form-item label="卡号：" prop="cardNo">
                  <el-input v-model.number="form.cardNo" type="number"></el-input>
                </el-form-item>
                <el-form-item label="持卡人身份证号：" prop="cardOwnerNo">
                  <el-input v-model.number="form.cardOwnerNo" type="number"></el-input>
                </el-form-item>
                <el-form-item label="预留手机号：" prop="cardPhone">
                  <el-input v-model.number="form.cardPhone" type="number"></el-input>
                </el-form-item>
                <el-form-item label="卡状态：" prop="cardStatus">
                  <el-select v-model="form.cardStatus" placeholder="请选择卡状态">
                    <el-option label="否" value="0"></el-option>
                    <el-option label="是" value="1"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="是否删除：" prop="cardStatus">
                  <el-select v-model="form.del" placeholder="请选择是否删除">
                    <el-option label="是" value="0"></el-option>
                    <el-option label="否" value="1"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="备注：">
                  <el-input v-model="form.remarks"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button class="payNameBnt1" @click="resetFormChange('form')">取消</el-button>
                  <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import {queryBankVoData, editBankCard} from '@/api/adminUser'

  export default {
    name: 'bankcard',
    data() {
      return {
        bjShow: false,
        dialogFormVisible: false,
        currentPage: 1,
        input2: '',
        value1: '',
        value2: '',
        tabHead: [
          // {
          //   label: '编号',
          //   prop: 'bankCardId'
          // },
          {
            label: '用户ID',
            prop: 'userId'
          },
          // {
          //   label: '用户姓名',
          //   prop: 'userRealName'
          // },
          {
            label: '持卡人',
            prop: 'cardOwnerName'
          },
          {
            label: '省份',
            prop: 'province'
          },
          {
            label: '城市',
            prop: 'city'
          },
          {
            label: '卡号',
            prop: 'cardNo'
          },
          {
            label: '开户行',
            prop: 'bankName'
          },
          {
            label: '支行',
            prop: 'subBranch'
          },
          {
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
        form: {
          bankCardId: '',
          userId: '',
          bankName: '',
          province: '',
          city: '',
          subBranch:  '',
          cardOwnerName:  '',
          remarks:  '',
          cardNo: '',
          cardOwnerNo:  '',
          cardPhone: '',
          cardStatus:  '',
          del:  '',
        },
      }
    },
    created() {
      this.queryBankDataList()
    },
    methods: {
      // 动态设置宽度
      columnWidth(item) {
        console.log(item);
        let widthStr = ''
        // if(item)
        switch (item) {
          case 'bankName':
            widthStr = '115'
            break
          case 'province':
            widthStr = '65'
            break
          case 'city':
            widthStr = '65'
            break
          case 'addDate':
            widthStr = '155'
            break
          case 'subBranch':
            widthStr = '190'
            break
          case 'cardNo':
            widthStr = '165'
            break
          case 'cardPhone':
            widthStr = '110'
            break
          case 'userMoney':
            widthStr = '100'
            break
          case 'remarks':
            widthStr = '150'
            break
          default:
            widthStr = '80'
        }
        return widthStr
      },
      openDia () {
        this.bjShow = false
        this.text = '新增银行卡'
        this.dialogFormVisible = true
        this.form = {
          bankCardId: '',
          userId: '',
          bankName: '',
          province: '',
          city: '',
          subBranch:  '',
          cardOwnerName:  '',
          remarks:  '',
          cardNo: '',
          cardOwnerNo:  '',
          cardPhone: '',
          cardStatus:  '',
          del:  '',
        }
      },
      //编辑
      bjTab(index, row) {
       // console.log(row)
        this.bjShow = true
        this.text = '银行卡管理'
        this.dialogFormVisible = true
        this.form = {
          bankCardId:row.bankCardId,
          userId: row.userId,
          bankName: row.bankName,
          province: row.province,
          city: row.city,
          subBranch: row.subBranch,
          cardOwnerName: row.cardOwnerName,
          remarks: row.remarks,
          cardNo: row.cardNo,
          cardOwnerNo: row.cardOwnerNo,
          cardPhone: row.cardPhone,
          cardStatus: String(row.cardStatus),
          del: String(row.del),
        }
      },
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
           // console.log(this.form)
            var data = {
              bankCardId: this.form.bankCardId,
              userId:  this.form.userId,
              bankName:  this.form.bankName,
              province:  this.form.province,
              city:  this.form.city,
              subBranch:   this.form.subBranch,
              cardOwnerName: this.form.cardOwnerName,
              remarks:  this.form.remarks,
              cardNo: this.form.cardNo,
              cardOwnerNo:  this.form.cardOwnerNo,
              cardPhone: this.form.cardPhone,
              cardStatus:  Number(this.form.cardStatus),
              del:  this.form.del,
            }
            editBankCard(data).then(res => {
              //console.log(res)
              if (res.code == 10000) {
                this.queryBankDataList()
                this.$message.success(res.message)
                this.$refs[formName].resetFields()
                this.dialogFormVisible = false
              } else {
                this.$message.error(res.message)
              }

            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      resetFormChange () {
        this.dialogFormVisible= false
        this.$refs[`form`].resetFields()
      },
      //查询
      seeOther() {
        this.currentPage = 1
        var time1 = '', time2 = ''
        if (this.value1 != '' && this.value1 != null) {
          time1 = this.value1.getTime()
        }
        if (this.value2 != '' && this.value2 != null) {
          time2 = this.value2.getTime()
        }
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
      },
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

