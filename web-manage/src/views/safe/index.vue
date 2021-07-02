<template>
    <div id="safe"  class="app-container">
      <div class="app-box">
        <div class="app-box-tab">
          <div class="app-box-title">安全设置</div>
          <div class="app-box-changeBox">
            <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增</el-button>
            <div class="app-box-input app-marginR">
              <div class="app-box-input-txt">IP地址：</div>
              <el-input placeholder="请输入IP地址" v-model="input1"></el-input>
            </div>
            <div class="app-btn-box">
              <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
              <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
            </div>
          </div>
          <div class="app-tab-box">
            <el-table
              :data="whiteList.list"
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

                  <span v-if="item.prop==='ipNumber'"  @click="handleCopy(scope.row.ipNumber)">
                             {{scope.row.ipNumber}}
                            </span>

                  <!-- 正常的其他列 -->
                  <span v-else>{{scope.row[item.prop]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="180">
                <template slot-scope="scope">
                  <el-button-group>
                    <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeTab(scope.$index, scope.row)">删除</el-button>
                  </el-button-group>
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
                  :total=whiteList.total>
                </el-pagination>
              </div>
              <div class="refresh">
                <el-button icon="el-icon-refresh-left" @click="refresh">刷新</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
                 :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>新增IP</span>
            </span>
        <div class="payNameDiaBox">
          <el-form ref="form" :model="form" label-width="130px" :rules="rules">
            <el-form-item label="IP地址：" prop="username">
              <el-input v-model="form.username"></el-input>
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
  import {getsafeList,addIp,removeIp} from '@/api/safe'
  export default {
    name: 'safe',
    data(){
      var username = (rule, value, callback) => {
        const reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        if (value === '') {
          callback(new Error('请输入IP地址'));
        }else if(!reg.test(value)){
          callback(new Error('请输入正确IP地址'));
        }else {
          callback();
        }
      };
      return{
        currentPage:1,
        input1: '',
        dialogFormVisible:false,
        tabHead: [
          {
            label: 'IP地址',
            prop: 'ipNumber'
          },
          {
            label: '归属地',
            prop: 'ipAddress'
          },

        ],
        form: {
          username: '',
        },
        rules:{
          username:[
            { validator: username, trigger: 'blur' }
          ]
        },
        whiteList:''
      }
    },
    created() {
      this.getlist(this.currentPage);
    },
    methods: {
      //复制
      handleCopy(e){
        this.copy(e)
      },
      seeOther(){
        this.currentPage = 1;
        var data={
          ipNumber:this.input1,
          pageNum: this.currentPage
        }
        getsafeList(data).then(res=>{
          if(res.code==10000){
            this.whiteList = res.data;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      seeAll(){
        this.input1="";
        this.currentPage = 1;
        this.getlist(this.currentPage);
      },
      //获取白名单列表
      getlist(page){
        var data={
          pageNum:page
        }
        getsafeList(data).then(res=>{
          console.log(res)
          if(res.code==10000){
            this.whiteList = res.data;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //分页事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
        this.getlist(val);
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },

      refresh() {

      },
      openDia(){
        this.dialogFormVisible=true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.form.username)
            var data={
              ipNumber:this.form.username
            }
            addIp(data).then(res=>{
              if(res.code==10000){
                this.$message.success(res.message);
                this.$refs[formName].resetFields();
                this.dialogFormVisible= false;
                this.getlist(this.currentPage);
              }else {
                this.$message.error(res.message)
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.dialogFormVisible= false;
      },
      ai_dialog_close(){
        this.dialogFormVisible=false;
        this.$refs[`form`].resetFields();
      },
      removeTab(index,row){
        console.log(row)

        this.$confirm('删除IP对应的账号将无法登录，是否删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          removeIp({ whitelistId:row.whitelistId}).then(res=>{
            if(res.code==10000){
              this.$message.success(res.message);
              this.getlist(this.currentPage);
            }else {
              this.$message.error(res.message);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
    }
  }
</script>

<style lang="less">
  #safe{
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
    .el-dialog{
      width: 460px;
    }
    .payNameDiaBox {
      width: 80%;
      margin: 0 auto;
      .el-select{
        width: 100% !important;
      }
      .payNameBnt1{
        width:60px;
        height:28px;
        background:rgba(246,232,255,1);
        opacity:1;
        line-height: 28px;
        padding: 0;
        color: #3B3269;
        font-size: 13px;
        border-color: rgba(246,232,255,1);
        float: left;
      }
      .payNameBnt2{
        width:60px;
        height:28px;
        background:rgba(92,56,130,1);
        opacity:1;
        line-height: 28px;
        padding: 0;
        color: #FFFFFF;
        font-size: 13px;
        border-color: rgba(92,56,130,1);
        float: right;
      }
      .payNameBnt2:focus, .payNameBnt2:hover{
        color: #FFFFFF;
        border-color: rgba(92,56,130,1);
        background:rgba(92,56,130,1);
      }
      .payNameBnt1:focus, .payNameBnt1:hover{
        color: #3B3269;
        border-color: rgba(246,232,255,1);
        background:rgba(246,232,255,1);
      }
    }
  }
</style>
