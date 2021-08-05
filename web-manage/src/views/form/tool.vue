<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">辅助工具</div>
        <div class="app-box-changeBox">
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-menu" @click="select_user_dialog_change">切换用户</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <div class="tool-conent" style="width: 100%;margin: 0 auto;">
            <el-form :inline="true" :model="toolUserFrom" class="tool-conent-form-inline">
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">推广码</div>
                </el-col>
                <el-col :span="4">
                  <div>{{specialUserInfo.diyUserId}}</div>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">用户名</div>
                </el-col>
                <el-col :span="4">
                  <div>{{specialUserInfo.diyUserName}}</div>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">电话</div>
                </el-col>
                <el-col :span="4">
                  <div>{{specialUserInfo.tel}}</div>
                </el-col>
                <el-col :span="6"></el-col>
              </el-row>
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">类型</div>
                </el-col>
                <el-col :span="4">
                  <div v-if="specialUserInfo.userType === 0"> 超级管理员</div>
                  <div v-if="specialUserInfo.userType === 1"> 玩家</div>
                  <div v-if="specialUserInfo.userType === 2"> 代理</div>
                </el-col>
                <el-col :span="6">
                  <el-button type="small" @click="toolUserSubmit(2)">更改为代理</el-button>
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
                          v-model="specialUserInfo.trueMoney">
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
                          v-model="specialUserInfo.diyUserName">
                  </el-input>
                </el-col>
                <el-col :span="6">
                  <el-button type="small" @click="toolUserSubmit(1)">更改姓名</el-button>
                </el-col>
                <el-col :span="12">
                </el-col>
              </el-row>
            </el-form>

            <div class="div-line"></div>

            <el-form :inline="true" :model="toolUserFrom" class="tool-conent-form-inline">
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">货币种类</div>
                </el-col>
                <el-col :span="4">
                  <el-select @change="skuSelectInfoChange" v-model="skuSelectInfo.skuName" placeholder="请选择" style="width:100%;">
                    <el-option
                            v-for="item in skuInfoVoList"
                            :key="item.skuId"
                            :label="item.skuName"
                            :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">货币编号</div>
                </el-col>
                <el-col :span="4">
                  <el-input
                          size="small"
                          readonly
                          v-model="skuSelectInfo.skuCode">
                  </el-input>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">购买方向</div>
                </el-col>
                <el-col :span="2">
                  <el-radio-group v-model="dataCreateObject.investType">
                    <el-radio :label="1">涨</el-radio>
                    <el-radio :label="2">跌</el-radio>
                  </el-radio-group>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">赢率</div>
                </el-col>
                <el-col :span="2">
                  <el-input-number size="mini" v-model="dataCreateObject.winRate" :min="0" :max="10" :step="1" label="赢率"></el-input-number>
                </el-col>
                <el-col :span="4">&nbsp;</el-col>
              </el-row>
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">起始时间</div>
                </el-col>
                <el-col :span="4">
                  <el-date-picker
                          v-model="dataCreateObject.dateStr"
                          @change="date1Change"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime"
                          placeholder="选择日期时间"
                          style="width: 100%;"
                  >
                  </el-date-picker>

                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">购买局数</div>
                </el-col>
                <el-col :span="2">
                  <el-input-number size="mini" label="局数" v-model="dataCreateObject.orderNum" :min="1" style="width: 100%;"></el-input-number>
                </el-col>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="2">
                  <div class="tool_row_title">购买类型</div>
                </el-col>
                <el-col :span="6">
                  <el-radio-group v-model="dataCreateObject.orderCycle">
                    <el-radio :label="30">30秒</el-radio>
                    <el-radio :label="60">60秒</el-radio>
                    <el-radio :label="180">180秒</el-radio>
                  </el-radio-group>
                </el-col>
                <el-col :span="4">&nbsp;</el-col>
              </el-row>
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">订单金额</div>
                </el-col>
                <el-col :span="4">
                  <el-input-number size="mini" label="订单金额" v-model="dataCreateObject.moneyStr" :min="1" :precision="2" style="width: 100%;"></el-input-number>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">价格区间</div>
                </el-col>
                <el-col :span="3">
                  <el-input-number size="mini" label="价格" v-model="dataCreateObject.minPriceStr" :min="1" :precision="2" style="width: 100%;"></el-input-number>
                </el-col>
                <el-col :span="3">
                  <el-input-number size="mini" label="价格" v-model="dataCreateObject.maxPriceStr" :min="1" :precision="2" style="width: 100%;"></el-input-number>
                </el-col>
                <el-col :span="6">&nbsp;</el-col>
              </el-row>

              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="4">
                  <el-button type="small" @click="toolSendSubmit(1)" style="width: 100%;">生成模拟数据</el-button>
                </el-col>
                <el-col :span="18">&nbsp;</el-col>
              </el-row>
              <el-table
                      :data="dataVirtualItem.orders"
                      border
                      style="width: 94%; margin: 0 auto;">
                <el-table-column prop="skuName" label="商品名称"></el-table-column>
                <el-table-column prop="skuCode" label="商品代码"></el-table-column>
                <el-table-column label="买入点位">
                  <template slot-scope="scope">
                    <span>{{parseFloat(scope.row.inPoint).toFixed(4)}}</span>
                  </template>
                </el-table-column>
                <el-table-column label="投资方向">
                  <template slot-scope="scope">
                    <span v-if="scope.row.investType === 1"><div style="color: red">买涨</div></span>
                    <span v-if="scope.row.investType === 2"><div style="color: green">买跌</div></span>
                  </template>
                </el-table-column>
                <el-table-column prop="orderAmount" label="订单金额"></el-table-column>
                <!--                <el-table-column prop="orderCharge" label="手续费"></el-table-column>-->
                <el-table-column prop="orderCycle" label="订单周期(s)">
<!--                  <template slot-scope="scope">-->
<!--                    <span v-if="scope.row.orderCycle === 30">30</span>-->
<!--                    <span v-if="scope.row.orderCycle === 60">60</span>-->
<!--                    <span v-if="scope.row.orderCycle === 180">180</span>-->
<!--                  </template>-->
                </el-table-column>
                <el-table-column label="结算点位" width="150px">
                  <template slot-scope="scope">
                    <span>{{parseFloat(scope.row.outPoint).toFixed(4)}}</span>
                  </template>
                </el-table-column>
                <el-table-column label="实际盈亏金额" width="150px">
                  <template slot-scope="scope">
                    <span style="color: red" v-if="parseFloat(scope.row.subMoney) > 0">+{{parseFloat(scope.row.subMoney).toFixed(4)}}</span>
                    <span style="color: green" v-if="parseFloat(scope.row.subMoney) < 0">{{parseFloat(scope.row.subMoney).toFixed(4)}}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="addDate" label="买入时间" width="160px"></el-table-column>
                <el-table-column prop="editDate" label="结算时间" width="160px"></el-table-column>
              </el-table>
              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">起始金额</div>
                </el-col>
                <el-col :span="4">
                  {{specialUserInfo.trueMoney}}
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">剩余金额</div>
                </el-col>
                <el-col :span="4">
                  {{dataVirtualItem.endMoney}}
                </el-col>
                <el-col :span="6">&nbsp;</el-col>
                <el-col :span="6">
                  <el-button type="small" @click="editDiyRecordByType(1)">入库交易记录</el-button>
                  <el-button type="small" @click="editDiyRecordByType(2)">清除交易记录</el-button>
                </el-col>
              </el-row>

              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">提现时间</div>
                </el-col>
                <el-col :span="4">
                  <el-date-picker
                          v-model="dataCreateTxObject.dateStr"
                          @change="date1Change"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime"
                          placeholder="选择日期时间"
                          style="width: 100%;"
                  >
                  </el-date-picker>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">提现金额</div>
                </el-col>
                <el-col :span="4">
                  <el-input-number size="mini" label="提现金额" v-model="dataCreateTxObject.cashStr" :min="1" :precision="2" style="width: 100%;"></el-input-number>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">审核</div>
                </el-col>
                <el-col :span="4">
                  <el-radio-group v-model="dataCreateTxObject.checkStatus">
                    <el-radio :label="3">通过</el-radio>
                    <el-radio :label="4">拒绝</el-radio>
                  </el-radio-group>
                </el-col>
                <el-col :span="6">
                  <el-button type="small" @click="toolSendSubmit(2)">生成提现记录</el-button>
                  <el-button type="small" @click="editDiyRecordByType(3)">清除提现记录</el-button>
                </el-col>
              </el-row>

              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="18">
                  <div style="padding-left: 60px">
                    生成“历史订单”和“提现记录”都会生成相应的资金流水记录。
                  </div>
                </el-col>
                <el-col :span="6">
                  <el-button type="small" @click="toolUserSubmit(4)">清除资金流水记录</el-button>
                </el-col>
              </el-row>

            </el-form>
          </div>

        </div>
      </div>
    </div>

    <!--选择用户-->
    <el-dialog :visible.sync="dialogUserTabVisible"
               :close-on-click-modal="false"
               :before-close="select_user_dialog_close"
               width="60%">
      <span slot="title" class="dialog-footer">
          <span>选择用户</span>
      </span>
      <div class="user-table">
        <el-table
                :data="userInfoVoList.list"
                border
                :max-height="600+'px'"
                highlight-current-row
                @current-change="handleCurrentUserChange"
                :header-cell-style="tabheaderFn"
                style="width: 100%">
          <el-table-column
                  type="index"
                  width="50">
          </el-table-column>
          <el-table-column
                  property="userId"
                  label="ID">
          </el-table-column>
          <el-table-column
                  property="userNickName"
                  label="昵称">
          </el-table-column>
          <el-table-column
                  property="userRealName"
                  label="姓名">
          </el-table-column>
          <el-table-column
                  property="userPhone"
                  label="电话">
          </el-table-column>
          <el-table-column
                  property="userMoney"
                  label="余额">
          </el-table-column>
          <el-table-column
                  property="userType"
                  label="类型">
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
      <div style="display: flex; justify-content: flex-end;">
        <el-button class="payNameBnt1" disabled @click="select_user_dialog_close()">取消</el-button>
        <el-button class="payNameBnt2" @click="select_user_dialog_ok()">确定</el-button>
      </div>
    </el-dialog>


  </div>


</template>

<script>
import { listUser } from '@/api/adminUser'
import { createImitateData, editDiyRecordByType, querySkuDataList, querySpecialUserInfo, updateSpecialUserInfo } from '@/api/tool'

export default {
    name: 'tool',
    data () {
        return {
            userInfoVoList: '',
            currentPage: 1,
            dialogUserTabVisible: true,
            tempUserInfo: null,
            specialUserInfo: {
                diyId: '',
                diyUserId: null,
                diyUserName: '',
                tel: '',
                trueMoney: 0,
                userRefereeCode: null,
                userType: null
            },
            skuInfoVoList: [
                {
                    nowPrice: 0,
                    skuCode: 1,
                    skuId: 0,
                    skuName: ''
                }
            ],
            skuSelectInfo: {
                nowPrice: '',
                skuCode: '',
                skuId: '',
                skuName: ''
            },
            // tempDateItem: {
            //     date1: new Date(),
            //     date2: new Date()
            // },
            dataCreateObject: {
                diyUserId: null,
                dealType: 1,
                diyId: null,
                dateStr: '',
                diyUserName: '',
                skuName: '',
                skuCode: '',
                skuId: null,
                moneyStr: '1000',
                maxPriceStr: '36000',
                minPriceStr: '35000',
                winRate: 5,
                investType: 1,
                orderCycle: 30,
                orderNum: 1,
                diyUserType: null,
                diyUserMoney: ''
            },
            dataCreateTxObject: {
                diyUserId: null,
                dealType: 2,
                diyId: null,
                cashStr: '',
                checkStatus: 3,
                dateStr: ''
            },
            dataVirtualItem: {
                diyId: 1,
                endMoney: 0,
                orders: []
            },
            toolUserFrom: {}
        }
    },
    // 初始化
    created () {
        // 获取用户列表
        this.queryListForUserVo()
        // 查询商品列表
        this.querySkuData()
    },
    methods: {
        tabheaderFn () {
            return 'background:#F6F7FB;color:#3B3269'
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
        // 修改用户信息
        toolUserSubmit (subtype) {
            var data = {
                diyUserId: this.specialUserInfo.diyUserId,
                diyUserName: this.specialUserInfo.diyUserName,
                diyUserType: subtype,
                diyUserMoney: this.specialUserInfo.trueMoney
            }
            updateSpecialUserInfo(data).then(res => {
                if (res.code === 10000) {
                    this.$message.success('修改成功！')
                    // 重新查询用户信息
                    this.querySpecialUserInfo()
                } else {
                    this.$message.error(res.message)
                }
            })

        },
        // 选中用户
        handleCurrentUserChange (val) {
            this.tempUserInfo = val
        },
        //获取用户列表
        queryListForUserVo () {
            var data = {
                pageNum: this.currentPage
            }
            listUser(data).then(res => {
                if (res.code === 10000) {
                    this.userInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 查询商品列表
        querySkuData () {
            var data = {}
            querySkuDataList(data).then(res => {
                console.log(res)
                if (res.code === 10000) {
                    this.skuInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 切换用户点击事件
        select_user_dialog_change () {
            this.dialogUserTabVisible = true
        },
        // 选择用户窗口关闭事件
        select_user_dialog_close () {
            this.dialogUserTabVisible = false
        },
        // 确定
        select_user_dialog_ok () {
            if (this.tempUserInfo == null || typeof this.tempUserInfo == 'undefined' || typeof this.tempUserInfo.userId == 'undefined') {
                this.$message.error('未选中用户信息')
            }
            this.querySpecialUserInfo()
            this.dialogUserTabVisible = false

        },
        // 查询用户信息
        querySpecialUserInfo () {
            // 查询用户信息
            var data = {
                diyUserId: this.tempUserInfo.userId
            }
            querySpecialUserInfo(data).then(res => {
                if (res.code === 10000) {
                    this.specialUserInfo = res.data
                    // 初始化页面数据
                    this.dataVirtualItem.orders = res.data.orders
                    this.dataVirtualItem.endMoney = res.data.trueMoney
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 下拉框事件
        skuSelectInfoChange (data) {
            this.skuSelectInfo.skuId = data.skuId
            this.skuSelectInfo.skuCode = data.skuCode
            this.skuSelectInfo.skuName = data.skuName
            this.skuSelectInfo.nowPrice = data.nowPrice
        },
        // 日期改变
        date1Change (value) {
            this.dataCreateObject.dateStr = value
        },
        // 点击模拟请求
        toolSendSubmit (type) {
            if (type === 1) {
                // 生成模拟数据
                this.createMoniData()
            }
            if (type === 2) {
                // 生成提现记录
                this.createTixData()
            }
        },
        createMoniData () {
            // 货币种类
            if (this.skuSelectInfo.skuCode == null || typeof this.skuSelectInfo.skuCode == 'undefined' || this.skuSelectInfo.skuCode === '') {
                this.$message.error('请选择货币种类！')
                return
            }
            // 赢率
            if (this.dataCreateObject.winRate == null || typeof this.dataCreateObject.winRate == 'undefined') {
                this.$message.error('请输入赢率！')
                return
            }
            // 起始时间
            if (this.dataCreateObject.dateStr == null || typeof this.dataCreateObject.dateStr == 'undefined' || this.dataCreateObject.dateStr === '') {
                this.$message.error('请输入起始时间！')
                return
            }
            // 局数
            if (this.dataCreateObject.orderNum == null || typeof this.dataCreateObject.orderNum == 'undefined') {
                this.$message.error('请输入局数！')
                return
            }
            // 起始金额
            if (this.dataCreateObject.moneyStr == null || typeof this.dataCreateObject.moneyStr == 'undefined') {
                this.$message.error('请输入起始金额！')
                return
            }
            // 价格区间 1
            if (this.dataCreateObject.minPriceStr == null || typeof this.dataCreateObject.minPriceStr == 'undefined') {
                this.$message.error('请输入起始价格区间！')
                return
            }
            // 价格区间 2
            if (this.dataCreateObject.maxPriceStr == null || typeof this.dataCreateObject.maxPriceStr == 'undefined') {
                this.$message.error('请输入结束价格区间！')
                return
            }

            var data = {
                diyUserId: this.specialUserInfo.diyUserId,
                dealType: 1,
                diyId: this.specialUserInfo.diyId,
                dateStr: this.dataCreateObject.dateStr,
                diyUserName: this.specialUserInfo.diyUserName,
                skuName: this.skuSelectInfo.skuName,
                skuCode: this.skuSelectInfo.skuCode,
                skuId: this.skuSelectInfo.skuId,
                moneyStr: String(this.dataCreateObject.moneyStr),
                maxPriceStr: String(this.dataCreateObject.minPriceStr),
                minPriceStr: String(this.dataCreateObject.maxPriceStr),
                winRate: this.dataCreateObject.winRate,
                investType: this.dataCreateObject.investType,
                orderCycle: this.dataCreateObject.orderCycle,
                orderNum: this.dataCreateObject.orderNum,
                diyUserType: this.specialUserInfo.userType,
                diyUserMoney: String(this.dataCreateObject.diyUserMoney)
            }
            this.createImitateData(data)
        },
        createTixData () {
            // 起始时间
            if (this.dataCreateTxObject.dateStr == null || typeof this.dataCreateTxObject.dateStr == 'undefined' || this.dataCreateTxObject.dateStr === '') {
                this.$message.error('请输入提现时间！')
                return
            }
            // 提现金额
            if (this.dataCreateTxObject.cashStr == null || typeof this.dataCreateTxObject.cashStr == 'undefined') {
                this.$message.error('请输入起始金额！')
                return
            }

            var data = {
                diyUserId: this.specialUserInfo.diyUserId,
                dealType: 2,
                diyId: this.specialUserInfo.diyId,
                dateStr: this.dataCreateTxObject.dateStr,
                cashStr: String(this.dataCreateTxObject.cashStr),
                checkStatus: this.dataCreateTxObject.checkStatus
            }
            this.createImitateData(data)
        },

        // 提交请求
        createImitateData (data) {
            createImitateData(data).then(res => {
                if (res.code === 10000) {
                    if (data.dealType === 1) {
                        this.$message.success('生成模拟数据成功！')
                        this.dataVirtualItem = res.data
                    }
                    if (data.dealType === 2) {
                        this.$message.success('生成提现记录成功！')
                    }
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 入库交易记录1 删除交易记录2 清除提现记录3 清除资金流水记录4
        editDiyRecordByType (type) {
            var data = {
                diyUserId: this.specialUserInfo.diyUserId,
                diyId: this.specialUserInfo.diyId,
                dealType: type
            }
            editDiyRecordByType(data).then(res => {
                if (res.code === 10000) {
                    if (type === 1) {
                        this.$message.success('入库交易记录成功！')
                    }
                    if (type === 2) {
                        this.$message.success('删除交易记录成功！')
                        this.dataVirtualItem.orders = []
                    }
                    if (type === 3) {
                        this.$message.success('清除提现记录成功！')
                    }
                    if (type === 4) {
                        this.$message.success('清除资金流水记录成功！')
                    }
                } else {
                    this.$message.error(res.message)
                }
            })
        }

    }

}
</script>

<style scoped>
  #eerchant {
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;
  }

  .app-box-tab {
    width: 95%;
    height: 100%;
    margin: 0 auto;
    overflow: hidden;
    overflow-x: auto;
    padding-bottom: 30px;
  }

  .tool-conent {
    padding-top: 20px;
    padding-bottom: 20px;
    background-color: #f9fafc;
  }

  .el-row {
    height: 30px;
    line-height: 30px;
    margin-bottom: 15px;
  }

  .el-row:last-child {
    margin-bottom: 0;
  }


  /*.el-row:nth-child(2n){background:orange;}*/

  .el-col {
    border-radius: 4px;
  }

  .tool_row_title {
    text-align: right;
    padding-right: 10px;
  }

  .div-line {
    width: 94%;
    margin: 0 auto;
    height: 20px;
    border-bottom: 2px solid #cacdd1;
    margin-bottom: 20px
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
</style>
