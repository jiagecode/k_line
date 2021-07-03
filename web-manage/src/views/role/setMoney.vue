<template>
  <div id="safe" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">手续费设置</div>
        <div class="app-box-changeBox">

        </div>
        <div class="app-tab-box">
          <el-table
            :data="feiLvList"
            border
            :max-height="600+'px'"
            :header-cell-style="tabheaderFn"
            style="width: 100%">
            <el-table-column
              v-for="(item,index) in tabHead"
              :prop="item.prop"
              :label="item.label">
            </el-table-column>
            <el-table-column
              label="操作"
              width="180">
              <template slot-scope="scope">
                <el-button-group>
                  <el-button type="primary" class="app-tab-btn app-tab-btn2"
                             @click="removeTab(scope.$index, scope.row)">修改
                  </el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>

        </div>
      </div>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
               :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>修改{{text}}</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="150px" :rules="rules">
          <el-form-item :label="text+'：'" prop="num">
            <el-input v-model="form.num" type="number"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { sxMoney, changeAllMoney } from '@/api/setmoney'

  export default {
    name: 'setMoney',
    data() {
      var num = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('不能为空'))
        } else {
          callback()
        }
      }
      return {
        dialogFormVisible: false,
        currentPage: 1,
        tabHead: [
          {
            label: '手续费类型',
            prop: 'name'
          },
          {
            label: '手续费参数',
            prop: 'num'
          }
        ],
        feiLvList: [
          {
            name: '商户代付手续费',
            num: '1.00',
            id: ''
          },
          {
            name: '商户充值下限额度',
            num: '0.1',
            id: ''
          },
          {
            name: '商户充值手续费率',
            num: '0.1',
            id: ''
          }
        ],
        whiteList: '',
        form: {
          num: ''
        },
        rules: {
          num: [
            { validator: num, trigger: 'blur' }
          ]
        },
        text: '',
        moneyId: ''
      }
    },
    created() {
      this.getAllMoney()
    },
    methods: {
      //获取各种费率
      getAllMoney() {
        var data = {}
        sxMoney(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.feiLvList[0].num = res.data.list[0].configValue
            this.feiLvList[1].num = res.data.list[1].configValue
            this.feiLvList[2].num = res.data.list[2].configValue

            this.feiLvList[0].id = res.data.list[0].id
            this.feiLvList[1].id = res.data.list[1].id
            this.feiLvList[2].id = res.data.list[2].id
          }
        })
      },

      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },

      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.form.username)
            var data = {
              id:this.moneyId,
              configValue:this.form.num
            }
            changeAllMoney(data).then(res=>{
              if(res.code==10000){
                this.dialogFormVisible = false
                this.getAllMoney()
                this.$message.success(res.message)
              }else {
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
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },
      removeTab(index, row) {
        console.log(row)
        this.text = row.name
        this.dialogFormVisible = true
        this.moneyId = row.id
        this.form.num = row.num
      }
    }
  }
</script>

<style lang="less">
  #safe {
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
