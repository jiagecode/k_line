<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">提现列表</div>
        <div class="app-box-changeBox">
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
              width="250">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjTab(scope.$index, scope.row)">编辑
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2"
                           @click="changeMoney(scope.$index, scope.row)">审核
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
                :total=cashVoDataList.total>
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
  import { listCashVo,  addUser,changeUserMoney } from '@/api/adminUser'

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
            label: '操作时间',
            prop: 'addDate'
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
            label: '备注',
            prop: 'remarks'
          },
          {
            label: '审核时间',
            prop: 'checkDate'
          },
          {
            label: '审核状态',
            prop: 'checkStatus'
          },
        ],
        form: {
          username: '',
          ptname: '',
          tel: '',
          region: '',
          txt: '',
          sysUserId: '',
          merchantInfoId: ''
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
    //  this.getpeopleList()
    },
    methods: {

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
        this.text = '编辑客户'
        this.dialogFormVisible = true
        this.telRoleid = row.roleId
        this.form = {
          username: row.sysUserName,
          ptname: row.merchantInfo.platformName,
          tel: row.phone,
          region: row.role.roleName,
          txt: row.remarks,
          sysUserId: row.sysUserId,
          merchantInfoId: row.merchantInfo.merchantInfoId
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
              userNickName: this.form.userNickName,
              userRealName: this.form.userRealName,
              userPhone: this.form.userPhone,
              userType: 1,
            }
            addUser(data).then(res => {
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

