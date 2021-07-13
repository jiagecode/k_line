<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">客户列表</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" @click="queryForBut(1)">所有用户</el-button>
          <el-button type="primary" class="app-marginR add-btn" @click="queryForBut(2)">所有客户</el-button>
          <el-button type="primary" class="app-marginR add-btn" @click="queryForBut(3)">所有代理商</el-button>
          <el-button type="primary" class="app-marginR add-btn" @click="queryForBut(4)">今日客户</el-button>
          <el-button type="primary" class="app-marginR add-btn" @click="queryForBut(5)">今日代理商</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia(1)">添加客户</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia(2)">添加代理</el-button>

          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 55px;">用户ID：</div>
            <div style="width: 150px;"><el-input placeholder="请输入用户ID" v-model="input1"></el-input></div>
          </div>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt" style="width: 40px;">用户：</div>
            <el-input placeholder="请输入昵称/姓名/手机号" v-model="input2"></el-input>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
                  :data="userInfoVoList.list"
                  border
                  :max-height="600+'px'"
                  :header-cell-style="tabheaderFn"
                  style="width: 100%">
            <el-table-column
                    v-for="(item,index) in tabHead"
                    :min-width="columnWidth(item.prop)"
                    :key="index"
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
                             {{showTypeDesc(scope.row.userType)}}
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
                  <div style="display: flex; justify-content: flex-start;">
                    <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjTab(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button type="primary" class="app-tab-btn app-tab-btn2"
                               @click="changeMoney(scope.$index, scope.row)">资金
                    </el-button>
                    <el-button type="primary" class="app-tab-btn app-tab-btn2"
                               @click="changeWinRate(scope.$index, scope.row)">胜率
                    </el-button>
                    <el-button type="primary" class="app-tab-btn app-tab-btn2" v-show="scope.row.userType === 1"
                               @click="signUpForUser(scope.$index, scope.row)">签约
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
                      :total=userInfoVoList.total>
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
          <el-form-item label="用户昵称：" prop="pname">
            <el-input v-model="form.userNickName"></el-input>
          </el-form-item>
          <el-form-item label="手机号码：" prop="tel">
            <el-input v-model.number="form.userPhone" type="number"></el-input>
          </el-form-item>
          <el-form-item label="佣金：" prop="commission">
            <el-input v-model.number="form.commission" type="number"></el-input>
          </el-form-item>
          <el-form-item label="红利：" prop="bonus">
            <el-input v-model.number="form.bonus" type="number"></el-input>
          </el-form-item>
          <el-form-item label="用户密码：" prop="userPassword" v-if="!bjShow">
            <el-input type="password" v-model="form.userPassword1"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="userPassword" v-if="!bjShow">
            <el-input type="password" v-model="form.userPassword2"></el-input>
          </el-form-item>
          <el-form-item label="所属代理：" prop="leixing">
            <el-select v-model="form.userAgentId"  placeholder="请选择代理">
              <el-option
                v-for="item in agentOpt"
                :label="item.label"
                :value="item.value"
              ></el-option></el-select>
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
          <el-form-item label="当前余额：" prop="moeny">
            <el-input v-model="changeForm.beforeMoney" type="number" disabled></el-input>
          </el-form-item>
          <el-form-item label="调整金额：" prop="moeny">
            <el-input v-model="changeForm.moeny" type="number"></el-input>
          </el-form-item>
          <el-form-item label="调整类型：" prop="leixing">
            <el-select v-model="changeForm.leixing"  placeholder="请选择类型" @change="moneyChangeLx">
              <el-option label="手动入金" value="0"></el-option>
              <el-option label="系统加款" value="1"></el-option>
              <el-option label="系统扣款" value="2"></el-option>
              <el-option label="提现扣款" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="调后余额：" prop="moeny">
            <el-input v-model="changeForm.afterMoney" type="number" disabled></el-input>
          </el-form-item>
          <el-form-item label="调整备注：" prop="beizhu">
            <el-input v-model="changeForm.beizhu"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormChange('changeForm')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormChange('changeForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <!--修改胜率-->
    <el-dialog :visible.sync="dialogFormVisibleWin" :close-on-click-modal="false"
               :before-close="ai_dialog_closeChange">
            <span slot="title" class="dialog-footer">
                <span>调整胜率</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="changeForm" :model="winForm" label-width="130px" :rules="rules">
          <el-form-item label="用户编号：" prop="userId">
            <el-input v-model="winForm.userId" type="number" disabled></el-input>
          </el-form-item>
          <el-form-item label="用户姓名：" prop="userRealName" v-if="!bjShow">
            <el-input v-model="winForm.userRealName"  disabled></el-input>
          </el-form-item>
          <el-form-item label="用户昵称：" prop="userNickName">
            <el-input v-model="winForm.userNickName"  disabled></el-input>
          </el-form-item>
          <el-form-item label="当前胜率(%)：" prop="moeny">
            <el-input v-model="winForm.beforeWin" type="number" disabled></el-input>
          </el-form-item>
          <el-form-item label="胜率调为(%)：" prop="winRate">
            <el-input-number v-model="winForm.winRate" type="number" :max="100" :min="0" ></el-input-number>
          </el-form-item>
          <el-form-item label="调整备注：" prop="remarks">
            <el-input v-model="winForm.remarks"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormChange('changeForm')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitForm('changeForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addUser, changeUserMoney, listUser,queryOptData1,changeUserType } from '@/api/adminUser'

export default {
    name: 'index',
    data () {
        return {
            bjShow: false,
            dialogFormVisible: false,
            dialogFormVisibleWin: false,
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
                // {
                //     label: '创建日期',
                //     prop: 'userRegisterDate'
                // },
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
                    label: '胜率(%)',
                    prop: 'winRate'
                },
                {
                    label: '归属代理商',
                    prop: 'agentName'
                }
            ],
            form: {
                userRealName: '',
                userNickName: '',
                lastLoginDate:'',
                tel: '',
                userAgentId:'',
                userPassword1: '',
                userPassword2: '',
                region: '',
                txt: '',
                bonus:'',
                commission:'',
                userId: '',
                userPhone: '',
                userType: 1
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
            userInfoVoList: '',
            //  修改金额
            dialogFormVisibleChange: false,
            winForm:{
              userId:'',
              winRate:'',
              remarks:'',
              userRealName: '',
              userNickName: '',
              beforeWin: '',
            },
            changeForm: {
                userId:'',
                moeny: '',
                beforeMoney:'',
                afterMoney:'',
                leixing: '',
                beizhu: ''
            },
            changeId: '',
            agentOpt:[]
        }
    },
    created () {
        this.queryListForUserVo()
        this.queryOptData1Met()
    },
    methods: {
      // 动态设置宽度
      columnWidth(item) {
        let widthStr = ''
        // if(item)
        switch (item) {
          case 'userId':
            widthStr = '50'
            break
          case 'userNickName':
            widthStr = '150'
            break
          // case 'userRegisterDate':
          //   widthStr = '155'
          //   break
          case 'lastLoginDate':
            widthStr = '140'
            break
          case 'orderNum':
            widthStr = '60'
            break
          case 'bonusRate':
            widthStr = '60'
            break
          case 'commissionRate':
            widthStr = '60'
            break
          case 'winRate':
            widthStr = '60'
            break
          default:
            widthStr = '80'
        }
        return widthStr
      },

      signUpForUser(index, row) {
       // console.log(row)
        this.$confirm('此操作将签约用户为代理商, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          changeUserType(row).then(res=>{
            if (res.code == 10000) {
              this.$message.info('签约成功');
              this.queryListForUserVo();
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
      showTypeDesc(userTYpe){
        return userTYpe === 2? '代理商':'用户';
      },
      queryOptData1Met(){
        queryOptData1().then(res =>{
          if (res.code == 10000) {
              this.agentOpt = res.data;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      moneyChangeLx(){
        var t = this.changeForm.leixing;
        var m = this.changeForm.moeny;
        if(m === '' || m===undefined){
          m=0.0;
        }
        if(t<2 ){
          m = 0- m;
        }
        this.changeForm.afterMoney = this.changeForm.beforeMoney - m;
      },
      //调整胜率
      changeWinRate(index, row){
        this.winForm.userId = row.userId,
          this.winForm.remarks = row.remarks,
          this.winForm.beforeWin = row.winRate,
          this.winForm.winRate = row.winRate,
          this.winForm.userRealName = row.userRealName,
          this.winForm.userNickName = row.userNickName,
          this.dialogFormVisibleWin = true;
      },
        //修改余额
        changeMoney (index, row) {
            this.dialogFormVisibleChange = true
            console.log(row)
            this.changeForm.userId = row.userId;
            this.changeForm.beforeMoney = row.userMoney;
            this.changeForm.afterMoney = row.userMoney;
        },
        submitFormChange (formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log(this.changeForm)
                    var data = {
                      dealType : this.changeForm.leixing,
                      userId:this.changeForm.userId,
                      money:this.changeForm.moeny
                    }
                    changeUserMoney(data).then(res => {
                        if (res.code == 10000) {
                            this.$message.success(res.message)
                            this.dialogFormVisibleChange = false
                            this.$refs[`changeForm`].resetFields()
                            this.queryListForUserVo()
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
        resetFormChange (formName) {
            this.dialogFormVisibleWin = false;
            this.dialogFormVisibleChange = false
            this.$refs[`changeForm`].resetFields()
            this.$refs[`winForm`].resetFields()
        },
        ai_dialog_closeChange () {
            this.dialogFormVisibleChange = false
            this.$refs[`changeForm`].resetFields()
        },
        //复制
        handleCopy (e) {
            this.copy(e)
        },
        getroleId (val) {
            console.log(val)
            this.telRoleid = val
        },
        //编辑
        bjTab (index, row) {
            console.log(row)
            this.bjShow = true
            this.text = '编辑客户'
            this.dialogFormVisible = true
            this.telRoleid = row.userId
            this.form = {
              userId: row.userId,
              username: row.userRealName,
              userNickName: row.userNickName,
              userRealName: row.userRealName,
              userPhone: row.userPhone,
              commission: row.commissionRate,
              txt: row.remarks,
              bonus: row.bonusRate,
              userAgentId: row.agentId,
            }
        },
        //查询
        seeOther () {
            this.currentPage = 1
            var data = {
                userNickName: this.input2,
                userId: this.input1,
                pageNum: this.currentPage
            }
            listUser(data).then(res => {
                console.log(res)
                if (res.code == 10000) {
                    this.userInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        seeAll () {
            this.input1 = ''
            this.input2 = ''
            this.currentPage = 1
            this.queryListForUserVo()
        },
        //获取用户列表
        queryListForUserVo () {
            var data = {
                pageNum: this.currentPage
            }
            listUser(data).then(res => {
                console.log(res)
                if (res.code == 10000) {
                    this.userInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        tabheaderFn () {
            return 'background:#F6F7FB;color:#3B3269'
        },
        removeThis () {

        },
        //分页事件
        handleSizeChange (val) {
            console.log(`每页 ${val} 条`)

        },
        handleCurrentChange (val) {
            console.log(`当前页: ${val}`)
            this.queryListForUserVo()
        },
        refresh () {
            this.currentPage = 1
            this.queryListForUserVo()
        },
        ai_dialog_close () {
            this.dialogFormVisible = false
            this.$refs[`form`].resetFields()
        },
        queryForBut (type) {
            this.currentPage = 1
            var data
            if (type === 2) {
                //所有客户
                data = {
                    userType: 1,
                    del: 1,
                    pageNum: this.currentPage
                }
            } else if (type === 3) {
                //所有代理商
                data = {
                    userType: 2,
                    del: 1,
                    pageNum: this.currentPage
                }
            } else if (type === 4) {
                //今日客户
                data = {
                    queryDateFlag: 1,
                    del: 1,
                    pageNum: this.currentPage
                }
            } else if (type === 5) {
                //今日代理商
                data = {
                    queryDateFlag: 2,
                    del: 1,
                    userType: 2,
                    pageNum: this.currentPage
                }
            } else {
                //所有用户
                data = {
                    del: 1,
                    pageNum: this.currentPage
                }
            }
            listUser(data).then(res => {
                console.log(res)
                if (res.code == 10000) {
                    this.userInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        //新增
        openDia (userType) {
            // this.openDia = false;
            this.bjShow = false
            var t = userType === 1 ? '新增客户' : '新增代理商'
            this.text = t
            this.dialogFormVisible = true
            this.form = {
                userId: '',
                userNickName: '',
                userRealName: '',
                userPhone: '',
                userPassword1: '',
                userPassword2: '',
                userType: userType,
               commission: '',
               bonus: '',
                txt: '',
              userAgentId: ''
            }
        },
        submitForm (formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.form.userPassword1 != this.form.userPassword2) {
                        this.$message.error('两次密码不一致')
                        return false
                    }
                   // console.log(this.form)
                  var data;
                  if(formName === "form"){
                    data = {
                      userId:this.form.userId,
                      userNickName: this.form.userNickName,
                      userRealName: this.form.userRealName,
                      userPhone: this.form.userPhone,
                      userPassword: this.form.userPassword1,
                      userType: this.form.userType,
                      commissionRate: this.form.commission,
                      bonusRate: this.form.bonus,
                      remarks: this.form.txt,
                      agentId:this.form.userAgentId
                    }
                  }else {
                    data = {
                      userId:this.winForm.userId,
                      winRate: this.winForm.winRate,
                    }
                  }
                    addUser(data).then(res => {
                        console.log(res)
                        if (res.code == 10000) {
                            this.queryListForUserVo()
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
        resetForm (formName) {
            this.$refs[formName].resetFields()
            this.dialogFormVisible = false
        },
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

