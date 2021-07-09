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
                  <div>{{specialUserInfo.userType}}</div>
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
                  <el-select @change="skuSelectInfoChange" v-model="this.skuSelectInfo.skuName" placeholder="请选择" style="width:100%;">
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
                          v-model="this.skuSelectInfo.skuCode">
                  </el-input>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">购买方向</div>
                </el-col>
                <el-col :span="2">
                  <el-radio-group v-model="toolUserFrom.type">
                    <el-radio :label="1" >涨</el-radio>
                    <el-radio :label="2">跌</el-radio>
                  </el-radio-group>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">赢率</div>
                </el-col>
                <el-col :span="2">
                  <el-input
                          size="small"
                          maxlength="2"
                          minlength="1"
                          placeholder="23"
                          v-model="toolUserFrom.type">
                  </el-input>
                </el-col>
                <el-col :span="4">&nbsp;</el-col>
              </el-row>
              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">起始时间</div>
                </el-col>
                <el-col :span="4">
                  <el-date-picker
                          style="width: 100%"
                          v-model="toolUserFrom.type"
                          type="date"
                          placeholder="选择日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="2">
                  <el-time-picker
                          v-model="toolUserFrom.type"
                          :picker-options="{selectableRange: '18:30:00 - 20:30:00'}"
                          placeholder="任意时间点">
                  </el-time-picker>
                </el-col>
                <el-col :span="2">
                  <div class="tool_row_title">购买局数</div>
                </el-col>
                <el-col :span="2">
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
                <el-col :span="4">&nbsp;</el-col>
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

              <div class="div-line"></div>

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

                  </el-table>
                </el-col>
                <el-col :span="10">
                  <el-table
                          :data="null"
                          border
                          style="width: 100%">

                  </el-table>
                </el-col>
                <el-col :span="8"></el-col>
              </el-row>

              <div class="div-line"></div>

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

              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="2">
                  <div class="tool_row_title">提现时间</div>
                </el-col>
                <el-col :span="4">
                  <el-date-picker
                          style="width: 100%"
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

              <div class="div-line"></div>

              <el-row class="tool_row">
                <el-col :span="18">
                  <div style="padding-left: 60px">
                    生成“历史订单”和“提现记录”都会生成相应的资金流水记录。
                  </div>
                </el-col>
                <el-col :span="6">
                  <el-button type="small" @click="toolUserSubmit(1)">清除资金流水记录</el-button>
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
               :before-close="select_user_dialog_close">
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
      <div style="margin: 0 auto">
        <el-button class="payNameBnt1" @click="select_user_dialog_close()">取消</el-button>
        <el-button class="payNameBnt2" @click="select_user_dialog_ok()">确定</el-button>
      </div>
    </el-dialog>


  </div>


</template>

<script>
import { listUser } from '@/api/adminUser'
import { querySpecialUserInfo, updateSpecialUserInfo, querySkuDataList } from '@/api/tool'

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
                userType: null,
            },
            skuInfoVoList: [
                {
                    nowPrice: 0,
                    skuCode: 1,
                    skuId: 0,
                    skuName: '',
                },
            ],
            skuSelectInfo: {
                nowPrice: '',
                skuCode: '',
                skuId: '',
                skuName: '',
            },
            userInfo: {
                id: '',
                name: ''
            },
            changeForm: {
                type: ''
            }, toolUserFrom: {
                type: ''
            }
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
                diyUserMoney: this.specialUserInfo.trueMoney,
            }
            updateSpecialUserInfo(data).then(res => {
                if (res.code == 10000) {
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
                if (res.code == 10000) {
                    this.userInfoVoList = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 查询商品列表
        querySkuData(){
            var data = {
            }
            querySkuDataList(data).then(res => {
                console.log(res)
                if (res.code == 10000) {
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
        querySpecialUserInfo(){
            // 查询用户信息
            var data = {
                diyUserId:this.tempUserInfo.userId
            }
            querySpecialUserInfo(data).then(res=>{
                if (res.code == 10000) {
                    this.specialUserInfo = res.data
                } else {
                    this.$message.error(res.message)
                }
            })
        },
        // 下拉框事件
        skuSelectInfoChange(data){
            console.log(data)
            this.skuSelectInfo.skuId = data.skuId
            this.skuSelectInfo.skuCode = data.skuCode
            this.skuSelectInfo.skuName = data.skuName
            this.skuSelectInfo.nowPrice = data.nowPrice
        }
    }

}
</script>

<style scoped>
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
</style>
