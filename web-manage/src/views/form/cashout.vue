<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">提现列表</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增
          </el-button>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户ID：</div>
            <el-input placeholder="请输入商户ID" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">用户：</div>
            <el-input placeholder="请输入昵称/姓名/手机号" v-model="input2"></el-input>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="shoppingList.list"
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
                <span v-if="item.prop==='userId'" @click="handleCopy(scope.row.userId)">
                             {{scope.row.userId}}
                </span>
                <span v-else-if="item.prop==='userNickName'" @click="handleCopy(scope.row.userNickName)">
                             {{scope.row.userNickName}}[{{scope.row.userPhone}}]
                            </span>
                <span v-else-if="item.prop==='userRealName'">
                             {{scope.row.userRealName}}
                            </span>
                <span v-else-if="item.prop==='userRegisterDate'">
                             {{scope.row.userRegisterDate}}
                            </span>
                <span v-else-if="item.prop==='lastLoginDate'">
                             {{scope.row.lastLoginDate}}
                            </span>
                <span v-else-if="item.prop==='orderNum'">
                             {{scope.row.orderNum}}
                            </span>
                <span v-else-if="item.prop==='userMoney'">
                             {{scope.row.userMoney}}
                            </span>
                <span v-else-if="item.prop==='userType'">
                             {{scope.row.userType}}
                            </span>
                <span v-else-if="item.prop==='bonusRate'">
                             {{scope.row.bonusRate}}
                            </span>
                <span v-else-if="item.prop==='commissionRate'">
                             {{scope.row.commissionRate}}
                            </span>
                <span v-else-if="item.prop==='agentName'">
                             {{scope.row.agentName}}
                            </span>

<!--                <span v-else-if="item.prop==='merchantInfo.platformName'">-->
<!--                             {{scope.row.merchantInfo['platformName']}}-->
<!--                            </span>-->
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
                           @click="changeMoney(scope.$index, scope.row)">修改余额
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.locked==1"
                           @click="changeUser(scope.$index, scope.row)">禁用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">启用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">下级客户
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">下级代理
                </el-button><el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">签约
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
                :total=shoppingList.total>
              </el-pagination>
            </div>
            <div class="refresh">
              <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
            </div>
          </div>


        </div>
      </div>
    </div>
    <!--新增-->
    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
               :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>{{text}}</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="130px" :rules="rules">
          <el-form-item label="用户姓名：" prop="username" v-if="!bjShow">
            <el-input v-model="form.userRealName"></el-input>
          </el-form-item>
          <el-form-item label="用户昵称：" prop="ptname">
            <el-input v-model="form.userNickName"></el-input>
          </el-form-item>
          <el-form-item label="手机号码：" prop="tel">
            <el-input v-model.number="form.userPhone" type="number"></el-input>
          </el-form-item>
          <el-form-item label="备注：">
            <el-input v-model="form.txt"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <!--修改金额-->

    <el-dialog :visible.sync="dialogFormVisibleChange" :close-on-click-modal="false"
               :before-close="ai_dialog_closeChange">
            <span slot="title" class="dialog-footer">
                <span>修改金额</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="changeForm" :model="changeForm" label-width="130px" :rules="rules">
          <el-form-item label="金额：" prop="moeny" >
            <el-input v-model="changeForm.moeny" type="number"></el-input>
          </el-form-item>
          <el-form-item label="类型：" prop="leixing">
            <el-select v-model="changeForm.leixing" placeholder="请选择类型">
              <el-option label="系统加款" value="1"></el-option>
              <el-option label="系统扣款" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注：" prop="beizhu">
            <el-input v-model="changeForm.beizhu"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormChange('changeForm')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormChange('changeForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listUser, listPeople, addUser, removeUser, changeUser, guanliUser,changeUserMoney } from '@/api/adminUser'

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
        tabHead: [
          {
            label: '用户ID',
            prop: 'userId'
          },
          {
            label: '用户信息',
            prop: 'userNickName'
          },
          {
            label: '客户姓名',
            prop: 'userRealName'
          },
          {
            label: '创建日期',
            prop: 'userRegisterDate'
          },
          {
            label: '最后登录',
            prop: 'lastLoginDate'
          },
          {
            label: '订单数',
            prop: 'orderNum'
          },
          {
            label: '账户余额',
            prop: 'userMoney'
          },
          {
            label: '身份',
            prop: 'userType'
          },
          {
            label: '红利',
            prop: 'bonusRate'
          },
          {
            label: '佣金',
            prop: 'commissionRate'
          },
          {
            label: '归属代理商',
            prop: 'agentName'
          }
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
        shoppingList: '',
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
      this.getshlist()
      this.getpeopleList()
    },
    methods: {
      //修改余额
      changeMoney(index, row) {
        this.dialogFormVisibleChange = true;
        console.log(row)
        this.changeId = row.merchantInfo.merchantInfoId
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
                this.getshlist();
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
          userNickName: this.input2,
          userId: this.input1,
          pageNum: this.currentPage,
        }
        listUser(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.shoppingList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      seeAll() {
        this.input1 = ''
        this.input2 = ''
        this.currentPage = 1
        this.getshlist()
      },
      //获取商户列表
      getshlist() {
        var data = {
          pageNum: this.currentPage,
        }
        listUser(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.shoppingList = res.data
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
        this.getshlist()
      },
      refresh() {
        this.currentPage = 1
        this.getshlist()
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },
      //获取角色组
      // getpeopleList() {
      //   var data = {
      //     pageNum: this.currentPage
      //   }
      //   listPeople(data).then(res => {
      //     console.log(res)
      //     if (res.code == 10000) {
      //       let list = res.data.list
      //       var arr = []
      //       for (var i = 0; i < list.length; i++) {
      //         var obj = {}
      //         obj.userId = list[i].userId
      //         // obj.roleName = list[i].roleName
      //         arr.push(obj)
      //       }
      //       this.peopleList = arr
      //     } else {
      //       this.$message.error(res.message)
      //     }
      //   })
      // },
      //禁用启用
      // changeUser(index, row) {
      //   console.log(row)
      //   let locked = row.locked
      //   var userlocked = ''
      //   if (locked == 1) {
      //     userlocked = 2
      //   } else {
      //     userlocked = 1
      //   }
      //   var data = {
      //     locked: userlocked,
      //     sysUserId: row.sysUserId
      //   }
      //   guanliUser(data).then(res => {
      //     console.log(res)
      //     if (res.code == 10000) {
      //       this.getshlist()
      //     } else {
      //       this.$message.error(res.message)
      //     }
      //   })
      // },

      //新增
      openDia() {
        // this.openDia = false;
        this.bjShow = false
        this.text = '新增客户'
        this.dialogFormVisible = true
        this.form = {
          userNickName: '',
          userRealName: '',
          userPhone: '',
          userType: '',
          txt: ''
        }
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
                this.getshlist()
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

