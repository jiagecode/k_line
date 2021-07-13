<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">提现列表</div>
        <div class="app-box-changeBox">
          <!--总提现金额-->
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 80px;">提现总金额：</div>
            <div style="color: #5C3882; font-weight: bold; height: 28px; line-height: 28px; width: 85px;">{{totalMoney}}</div>
          </div>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户ID：</div>
            <el-input placeholder="请输入商户ID" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户：</div>
            <el-input placeholder="请输入昵称/姓名/手机号" v-model="input2"></el-input>
          </div>
          <div class="app-box-select app-marginR" >
            <div class="app-box-input-txt">审核状态：</div>
            <el-select v-model="regionCheckStatus" placeholder="请选择审核状态">
              <el-option label="无需审核" value="0"></el-option>
              <el-option label="待审核" value="1"></el-option>
              <el-option label="审核中" value="2"></el-option>
              <el-option label="审核通过" value="3"></el-option>
              <el-option label="审核拒绝" value="4"></el-option>
            </el-select>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="cashVoDataList.list"
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
                <span v-if="item.prop==='cashId'" @click="handleCopy(scope.row.cashId)">
                             {{scope.row.cashId}}
                </span>
                <span v-else-if="item.prop==='accountId'" @click="handleCopy(scope.row.accountId)">
                             {{scope.row.accountId}}
                            </span>
                <span v-else-if="item.prop==='userRealName'">
                             {{scope.row.userRealName}}
                            </span>
                <span v-else-if="item.prop==='addDate'">
                             {{scope.row.addDate}}
                            </span>
                <span v-else-if="item.prop==='cashMoney'">
                             {{scope.row.cashMoney}}
                            </span>
                <span v-else-if="item.prop==='userMoney'">
                             {{scope.row.userMoney}}
                            </span>
                <span v-else-if="item.prop==='userMoney'">
                             {{scope.row.userMoney}}
                            </span>
                <span v-else-if="item.prop==='remarks'">
                             {{scope.row.remarks}}
                            </span>
                <span v-else-if="item.prop==='checkDate'">
                             {{scope.row.checkDate}}
                            </span>
                <span v-else-if="item.prop==='checkStatus'">
                             {{showCheckStatusDesc(scope.row.checkStatus)}}
                            </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="160">
              <template slot-scope="scope">
                <div style="display: flex; justify-content: flex-start;">
                  <el-button type="primary" class="app-tab-btn app-tab-btn2" v-show="scope.row.checkStatus === 1" style="margin-left: 10px;"
                             @click="bjTab(scope.$index, scope.row)">审核
                  </el-button>
                  <el-button type="primary" class="app-tab-btn app-tab-btn2" style="color: red"
                             @click="deleteThisCash(scope.$index, scope.row)">删除
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
                :total=cashVoDataList.total>
              </el-pagination>
            </div>
            <div class="refresh">
              <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
            </div>
          </div>
          <!--审核-->
          <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
                     :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>{{text}}</span>
            </span>
            <div class="payNameDiaBox">
              <el-form ref="form" :model="form" label-width="130px" :rules="rules">
                <el-form-item label="交易账户：" prop="username" >
                  <el-input v-model="form.accountId" disabled></el-input>
                </el-form-item>
                <el-form-item label="交易姓名：" prop="username" >
                  <el-input v-model="form.userRealName" disabled></el-input>
                </el-form-item>
                <el-form-item label="金额：" prop="pname">
                  <el-input v-model="form.cashMoney" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请时间：" prop="bonus">
                  <el-input v-model.number="form.addDate" type="number" disabled></el-input>
                </el-form-item>
                <el-form-item label="审核结果：" prop="bonus">
                  <el-select v-model="form.checkStatus" placeholder="请选择审核状态">
                    <el-option label="审核通过" value="3"></el-option>
                    <el-option label="审核拒绝" value="4"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="备注：">
                  <el-input v-model="form.remarks"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
                  <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-dialog>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {listCashVo,sumMoneyForCash, changeUserMoney, checkCash} from '@/api/adminUser'

  export default {
    name: 'index',
    data() {
      var username = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名称'))
        } else {
          callback()
        }
      }
      var ptname = (rule, value, callback) => {
        if (value === '') {
         return  callback(new Error('请输入平台名称'))
        } else if (value.length < 3 || value.length > 11) {
          callback(new Error('长度在3到11个字符'))
        } else {
          callback()
        }
      }
      var gettel = (rule, value, callback) => {
        var reg = /^1[3456789]\d{9}$/
        if (value === '') {
          callback(new Error('请输入手机号码'))
        } else if (!reg.test(value)) {
          callback(new Error('请输入正确手机号'))
        } else {
          callback()
        }
      }
      var region = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择商户类型'))
        } else {
          callback()
        }
      }
      var txt = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入备注'))
        } else {
          callback()
        }
      }

      var moeny = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入金额'))
        } else {
          callback()
        }
      }
      var leixing = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择类型'))
        } else {
          callback()
        }
      }
      var beizhu = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入备注'))
        } else {
          callback()
        }
      }

      return {
        bjShow: false,
        dialogFormVisible: false,
        currentPage: 1,
        input1: '',
        input2: '',
        region: '',
        totalMoney:'',
        regionCheckStatus: undefined,
        cashType:1,
        tabHead: [
          {
            label: '提现编号',
            prop: 'cashId'
          },
          {
            label: '交易账号',
            prop: 'accountId'
          },
          {
            label: '交易姓名',
            prop: 'userRealName'
          },
          {
            label: '交易金额',
            prop: 'cashMoney'
          },
          {
            label: '会员账户余额',
            prop: 'userMoney'
          },
          {
            label: '审核状态',
            prop: 'checkStatus'
          },
          {
            label: '操作时间',
            prop: 'addDate'
          },
          {
            label: '审核时间',
            prop: 'checkDate'
          },
          {
            label: '备注',
            prop: 'remarks'
          }
        ],
        form: {
          accountId: '',
          cashMoney: '',
          addDate: '',
          remarks: '',
          userRealName: '',
          cashId: '',
          checkStatus:''
        },
        text: '',
        dialogFormVisibleTel: false,
        formtel: {
          tel: ''
        },
        telRoleid: '',
        rules: {
          // username: [
          //   { validator: username, trigger: 'blur' }
          // ],
          // ptname: [
          //   { required: true, message: '请输入平台名称', trigger: 'blur' },
          //   { min: 3, max: 11, message: '长度在 3 到 11 个字符', trigger: 'blur' }
          // ],
          // tel: [
          //   { required: true, validator: gettel, trigger: 'blur' }
          // ],
          // region: [
          //   { validator: region, trigger: 'blur' }
          // ],
          // txt: [
          //   { validator: txt, trigger: 'blur' }
          // ],
          //
          // moeny: [
          //   { validator: moeny, trigger: 'blur' }
          // ],
          // leixing: [
          //   { validator: leixing, trigger: 'blur' }
          // ],
          // beizhu: [
          //   { validator: beizhu, trigger: 'blur' }
          // ],
        },
        peopleList: [],
        cashVoDataList: '',
        //  修改金额
        dialogFormVisibleChange: false,
        changeForm: {
          moeny: '',
          leixing: '',
          beizhu: ''
        },
        changeId:""
      }
    },
    created() {
      this.queryDetailForCash()
      this.queryAllCash()
    },
    methods: {
      // 动态设置宽度
      columnWidth(item) {
        console.log(item)
        let widthStr = ''
        // if(item)
        switch (item) {
          case 'addDate':
            widthStr = '150'
            break
          case 'checkDate':
            widthStr = '150'
            break
          case 'userMoney':
            widthStr = '100'
            break
          case 'remarks':
            widthStr = '200'
            break
          default:
            widthStr = '80'
        }
        return widthStr
      },
      queryAllCash(){
        var par ={"cashType":this.cashType}
        sumMoneyForCash(par).then(res =>{
          if (res.code == 10000) {
            this.totalMoney = res.data;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //删除
      deleteThisCash(index, row){
        this.$confirm('此操作将删除此提现记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          var data ={
            cashId :row.cashId,
            del :0
          }
          checkCash(data).then(res=>{
            if (res.code == 10000) {
              this.$message.info('删除成功');
              this.queryDetailForCash();
            }else {
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
      submitFormChange(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.changeForm)
            var data={
              consumptionAmount:this.changeForm.moeny,
              remarks:this.changeForm.beizhu,
              consumptionType:this.changeForm.leixing,
              merchantInfoId:this.changeId
            }
            changeUserMoney(data).then(res=>{
              if(res.code==10000){
                this.$message.success(res.message);
                this.dialogFormVisibleChange = false
                this.$refs[`changeForm`].resetFields();
                this.queryDetailForCash();
              }else {
                this.$message.error(res.message);
              }
            })


          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      resetFormChange(){
        this.dialogFormVisibleChange = false
        this.$refs[`changeForm`].resetFields()
      },
      ai_dialog_closeChange() {
        this.dialogFormVisibleChange = false
        this.$refs[`changeForm`].resetFields()
      },
      //复制
      handleCopy(e) {
        this.copy(e)
      },
      getroleId(val) {
        console.log(val)
        this.telRoleid = val
      },
      //编辑
      bjTab(index, row) {
        console.log(row)
        this.bjShow = true
        this.text = '提现审核'
        this.dialogFormVisible = true
        this.form = {
          accountId:row.accountId,
          cashMoney: row.cashMoney,
          addDate: row.addDate,
          remarks: row.remarks,
          userRealName: row.userRealName,
          cashId: row.cashId,
        }
      },
      //查询
      seeOther() {
        this.currentPage = 1
        var data = {
          userRealName: this.input2,
          userId: this.input1,
          pageNum: this.currentPage,
          checkStatus:this.regionCheckStatus,
          cashType:this.cashType
        }
        listCashVo(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.cashVoDataList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      seeAll() {
        this.input1 = ''
        this.input2 = ''
        this.currentPage = 1
        this.queryDetailForCash()
      },
      //提现明细列表
      queryDetailForCash() {
        var data = {
          pageNum: this.currentPage,
          cashType:this.cashType
        }
        listCashVo(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.cashVoDataList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      showCheckStatusDesc(sta){
       // 审核状态 0-无需审核 1-待审核 2-审核中 3-审核通过 4-审核拒绝
        return sta>2?(sta ===3?"审核通过":"审核拒绝"):(sta ===2?"审核中":(sta===1?"待审核":"无需审核"));
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
        this.queryDetailForCash()
      },
      refresh() {
        this.currentPage = 1
        this.queryDetailForCash()
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.form)
            var data = {
              accountId:this.form.accountId,
              //   cashMoney:this.form.cashMoney,
              //   addDate: this.form.addDate,
              remarks: this.form.remarks,
              checkStatus: this.form.checkStatus,
              cashId:this.form.cashId
            }
            checkCash(data).then(res => {
              console.log(res)
              if (res.code == 10000) {
                this.queryDetailForCash()
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
      resetForm(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisible = false
      }
    },
    filters: {
      // locked(val){
      //   return val==1?""
      // }
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

