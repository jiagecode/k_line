<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">客户列表</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="queryForBut(1)">所有用户</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="queryForBut(2)">所有客户</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="queryForBut(3)">所有代理商</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="queryForBut(4)">今日客户</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="queryForBut(5)">今日代理商</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia(1)">添加客户+</el-button>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia(2)">添加代理+</el-button>

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
                  :data="userInfoVoList.list"
                  border
                  :max-height="600+'px'"
                  :header-cell-style="tabheaderFn"
                  style="width: 100%">
            <el-table-column
                    v-for="(item,index) in tabHead"
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
                <span v-else-if="item.prop==='userRegisterDate'" style="width:150px; ">
                             {{scope.row.userRegisterDate}}
                            </span>
                <span v-else-if="item.prop==='lastLoginDate'" style="width: 150px;">
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
                    width="300">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjTab(scope.$index, scope.row)">编辑
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2"
                           @click="changeMoney(scope.$index, scope.row)">查询资金
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2"
                           @click="changeUser(scope.$index, scope.row)">下级代理
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2"
                           @click="showTool(scope.$index, scope.row)">辅助工具
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
          <el-form-item label="用户昵称：" prop="ptname">
            <el-input v-model="form.userNickName"></el-input>
          </el-form-item>
          <el-form-item label="手机号码：" prop="tel">
            <el-input v-model.number="form.userPhone" type="number"></el-input>
          </el-form-item>
          <el-form-item label="用户密码：" prop="userPassword" v-if="!bjShow">
            <el-input type="password" v-model="form.userPassword1"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="userPassword" v-if="!bjShow">
            <el-input type="password" v-model="form.userPassword2"></el-input>
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
          <el-form-item label="金额：" prop="moeny">
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

    <!-- 辅助工具 -->
    <el-dialog width="60%" title="辅助工具" :visible.sync="dialogToolVisible">
      <div class="tool_conent" style="width: 94%;margin: 0 auto;">
        <el-form :inline="true" :model="toolUserFrom" class="tool-conent-form-inline">
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">推广码</div>
            </el-col>
            <el-col :span="4">
              <div>53</div>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">用户名</div>
            </el-col>
            <el-col :span="4">
              <div>xx</div>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">电话</div>
            </el-col>
            <el-col :span="4">
              <div>13399992222</div>
            </el-col>
            <el-col :span="6"></el-col>
          </el-row>
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">类型</div>
            </el-col>
            <el-col :span="4">
              <div>超级管理员</div>
            </el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">更改为代理</el-button>
              <el-button type="small" @click="toolUserSubmit(1)">更改为玩家</el-button>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-row>
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">金额</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="请输入金额"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">更改金额</el-button>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-row>
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">姓名</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="请输入姓名"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">更改姓名</el-button>
            </el-col>
            <el-col :span="12">
            </el-col>
          </el-row>
        </el-form>

        <div style="width: 100%; height: 10px;border-bottom: 2px solid #808080;margin-bottom: 10px"></div>

        <el-form :inline="true" :model="toolUserFrom" class="tool-conent-form-inline">
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">货币种类</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="比特币"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">货币编号</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="23"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">购买方向</div>
            </el-col>
            <el-col :span="4">
              <el-radio-group v-model="toolUserFrom.type">
                <el-radio :label="1">涨</el-radio>
                <el-radio :label="2">跌</el-radio>
              </el-radio-group>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">赢率</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      maxlength="2"
                      minlength="1"
                      placeholder="23"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
          </el-row>
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">起始时间</div>
            </el-col>
            <el-col :span="4">
              <el-date-picker
                      v-model="toolUserFrom.type"
                      type="date"
                      placeholder="选择日期">
              </el-date-picker>
            </el-col>
            <el-col :span="4">
              <el-time-picker
                      v-model="toolUserFrom.type"
                      :picker-options="{selectableRange: '18:30:00 - 20:30:00'}"
                      placeholder="任意时间点">
              </el-time-picker>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">购买局数</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="12"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">购买类型</div>
            </el-col>
            <el-col :span="6">
              <el-radio-group v-model="toolUserFrom.type">
                <el-radio :label="1">30秒</el-radio>
                <el-radio :label="2">60秒</el-radio>
                <el-radio :label="3">180秒</el-radio>
              </el-radio-group>
            </el-col>
          </el-row>
          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">起始金额</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="23"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">价格区间</div>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="12"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-input
                      size="small"
                      placeholder="23"
                      v-model="toolUserFrom.type">
              </el-input>

            </el-col>
            <el-col :span="4"></el-col>
          </el-row>

          <div style="width: 100%; height: 10px;border-bottom: 2px solid #808080;margin-bottom: 10px"></div>

          <el-row class="tool_row">
            <el-col :span="2">&nbsp;</el-col>
            <el-col :span="4">
              <el-button type="small" @click="toolUserSubmit(1)">赢率生成</el-button>
            </el-col>
            <el-col :span="10">
              <el-button type="small" @click="toolUserSubmit(1)">生成模拟数据</el-button>
            </el-col>
            <el-col :span="8">&nbsp;</el-col>
          </el-row>
          <el-row class="tool_row" style="min-height: 100px;max-height: 500px;">
            <el-col :span="2">&nbsp;</el-col>
            <el-col :span="4">
              <el-table
                      :data="null"
                      border
                      style="width: 100%">
                <el-table-body>

                </el-table-body>
              </el-table>
            </el-col>
            <el-col :span="10">
              <el-table
                      :data="null"
                      border
                      style="width: 100%">
                <el-table-body>

                </el-table-body>
              </el-table>
            </el-col>
            <el-col :span="8"></el-col>
          </el-row>

          <div style="width: 100%; height: 10px;border-bottom: 2px solid #808080;margin-bottom: 10px"></div>

          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">起始金额</div>
            </el-col>
            <el-col :span="4">
              0
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">剩余金额</div>
            </el-col>
            <el-col :span="4">
              0
            </el-col>
            <el-col :span="6">&nbsp;</el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">入库交易记录</el-button>
              <el-button type="small" @click="toolUserSubmit(1)">清除交易记录</el-button>
            </el-col>
          </el-row>

          <div style="width: 100%; height: 10px;border-bottom: 2px solid #808080;margin-bottom: 10px"></div>

          <el-row class="tool_row">
            <el-col :span="2">
              <div class="tool_row_title">提现时间</div>
            </el-col>
            <el-col :span="4">
              <el-date-picker
                      v-model="toolUserFrom.type"
                      type="date"
                      placeholder="选择日期">
              </el-date-picker>
            </el-col>
            <el-col :span="4">
              <el-time-picker
                      v-model="toolUserFrom.type"
                      :picker-options="{selectableRange: '18:30:00 - 20:30:00'}"
                      placeholder="任意时间点">
              </el-time-picker>
            </el-col>
            <el-col :span="2">
              <div class="tool_row_title">提现金额</div>
            </el-col>
            <el-col :span="2">
              <el-input
                      size="small"
                      placeholder="12"
                      v-model="toolUserFrom.type">
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-radio-group v-model="toolUserFrom.type">
                <el-radio :label="1">通过</el-radio>
                <el-radio :label="2">拒绝</el-radio>
              </el-radio-group>
            </el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">入库交易记录</el-button>
              <el-button type="small" @click="toolUserSubmit(1)">清除交易记录</el-button>
            </el-col>
          </el-row>

          <div style="width: 100%; height: 10px;border-bottom: 2px solid #808080;margin-bottom: 10px"></div>

          <el-row class="tool_row">
            <el-col :span="18">
              <div>
                生成“历史订单”和“提现记录”都会生成相应的资金流水记录。
              </div>
            </el-col>
            <el-col :span="6">
              <el-button type="small" @click="toolUserSubmit(1)">清除资金流水记录</el-button>
            </el-col>
          </el-row>

        </el-form>
      </div>

      <div class="user_info">

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogToolVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogToolVisible = false">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { addUser, changeUserMoney, listUser } from '@/api/adminUser'

export default {
    name: 'index',
    data () {
        var username = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入用户名称'))
            } else {
                callback()
            }
        }
        var ptname = (rule, value, callback) => {
            if (value === '') {
                return callback(new Error('请输入平台名称'))
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
            dialogToolVisible: false,
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
                userPassword1: '',
                userPassword2: '',
                region: '',
                txt: '',
                sysUserId: '',
                merchantInfoId: '',
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
            changeForm: {
                moeny: '',
                leixing: '',
                beizhu: ''
            },
            changeId: '',
            toolUserFrom: {
                type: ''
            }
        }
    },
    created () {
        this.queryListForUserVo()
        // this.getpeopleList()
    },
    methods: {
        //修改余额
        changeMoney (index, row) {
            this.dialogFormVisibleChange = true
            console.log(row)
            this.changeId = row.merchantInfo.merchantInfoId
        },
        submitFormChange (formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log(this.changeForm)
                    var data = {
                        consumptionAmount: this.changeForm.moeny,
                        remarks: this.changeForm.beizhu,
                        consumptionType: this.changeForm.leixing,
                        merchantInfoId: this.changeId
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
        resetFormChange () {
            this.dialogFormVisibleChange = false
            this.$refs[`changeForm`].resetFields()
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
                userNickName: '',
                userRealName: '',
                userPhone: '',
                userPassword1: '',
                userPassword2: '',
                userType: userType,
                txt: ''
            }
        },
        submitForm (formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.form.userPassword1 != this.form.userPassword2) {
                        this.$message.error('两次密码不一致')
                        return false
                    }
                    console.log(this.form)
                    var data = {
                        userNickName: this.form.userNickName,
                        userRealName: this.form.userRealName,
                        userPhone: this.form.userPhone,
                        userPassword: this.form.userPassword1,
                        userType: this.form.userType
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
        // 显示工具页面
        showTool (index, row) {
            console.log(row)
            this.dialogToolVisible = true
        },
        // 工具页面提交事件
        toolUserSubmit (subtype) {
            console.log('submit!' + subtype)
        }
    },
    filters: {
        // locked(val){
        //   return val==1?""
        // }
    }
}
</script>

<style type="text/css">
  .tool_row {
    height: 40px;
    line-height: 40px;
  }

  .tool_row_title {
    text-align: right;
    padding-right: 10px;
  }


</style>

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

