<template>
  <div id="recharge" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">系统用户</div>
        <div class="app-box-changeBox">
          <div class="app-box-input app-marginR">
            <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增</el-button>
            <div class="app-box-input app-marginR">
              <div class="app-box-input-txt">角色：</div>
              <el-select v-model="rid"  placeholder="请选择角色">
                <el-option
                  v-for="item in roleOpt"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </div>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" class="app-tab-btn2" @click="seeRole">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" class="app-tab-btn2" @click="seeRoleAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="peopleList.list"
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
                <span v-if="item.prop==='sysUserId'">
                             {{scope.row.sysUserId}}
                            </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="250">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn1" @click="bjopen(scope.$index, scope.row)">编辑</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeThis(scope.$index, scope.row)">删除</el-button>
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
                :total=peopleList.total>
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
                <span>新增用户</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="130px" :rules="rules">
        <el-form-item label="登录账号：" prop="sysUserCode">
          <el-input v-model="form.sysUserCode" ></el-input>
        </el-form-item>
        <el-form-item label="用户名称：" prop="sysUserName">
          <el-input v-model="form.sysUserName"></el-input>
        </el-form-item>
          <el-form-item label="电话号码：" prop="sysUserPhone">
            <el-input v-model="form.sysUserPhone"></el-input>
          </el-form-item>
        <el-form-item label="用户角色：" prop="roleId">
          <el-select v-model="form.sysRoleId"  placeholder="请选择角色">
            <el-option
              v-for="item in roleOpt"
              :label="item.label"
              :value="item.value"
            ></el-option></el-select>
        </el-form-item>
        <el-form-item label="登录密码：" prop="sysUserPassword">
          <el-input v-model="form.sysUserPassword"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <!--修改-->
    <el-dialog :visible.sync="dialogFormVisiblebj" :close-on-click-modal="false"
               :before-close="ai_dialog_closebj">
            <span slot="title" class="dialog-footer">
                <span>修改信息</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="formbj" :model="formbj" label-width="130px" :rules="rules">
          <el-form-item label="用户编号：" prop="sysUserId">
            <el-input v-model="formbj.sysUserId" disabled></el-input>
          </el-form-item>
          <el-form-item label="登录账号：" prop="sysUserCode">
            <el-input v-model="formbj.sysUserCode" disabled></el-input>
          </el-form-item>
          <el-form-item label="用户名称：" prop="sysUserName">
            <el-input v-model="formbj.sysUserName"></el-input>
          </el-form-item>
          <el-form-item label="电话号码：" prop="sysUserPhone">
            <el-input v-model="formbj.sysUserPhone"></el-input>
          </el-form-item>
          <el-form-item label="用户角色：" prop="roleId">
            <el-select v-model="formbj.sysRoleId"  placeholder="请选择角色">
              <el-option
                v-for="item in roleOpt"
                :label="item.label"
                :value="item.value"
              ></el-option></el-select>
          </el-form-item>
          <el-form-item label="新密码：" prop="sysUserPassword2">
            <el-input v-model="formbj.sysUserPassword2"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormbj('formbj')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormbj('formbj')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  import {querySysUserList,saveSysUser,queryOptData2} from '@/api/adminUser'
  export default {
    name: 'sysuser',
    data() {
      return {
        roleOpt:[],
        value1: '',
        value2: '',
        currentPage: 1,
        input1: '',
        rid:undefined,
        tabHead: [
          {
            label: '用户ID',
            prop: 'sysUserId'
          },
          {
            label: '登录账号',
            prop: 'sysUserCode'
          }, {
            label: '用户名',
            prop: 'sysUserName'
          }, {
            label: '电话号码',
            prop: 'sysUserPhone'
          }, {
            label: '角色名称',
            prop: 'roleName'
          }, {
            label: '创建时间',
            prop: 'addDate'
          }, {
            label: '修改时间',
            prop: 'editDate'
          },
        ],
        dialogFormVisible:false,
        dialogFormVisiblebj:false,
        form:{
          sysUserName:"",
          sysRoleId:'',
          sysUserCode:'',
          roleName:'',
          sysUserPhone:'',
          sysUserPassword:''
        },
        formbj:{
          sysUserName:"",
          sysUserId:'',
          sysRoleId:'',
          sysUserCode:'',
          roleName:'',
          sysUserPhone:'',
          sysUserPassword2:''
        },
        rules:{
          sysUserName:[
            { required: true, message: '请填写角色名称', trigger: 'blur' }
          ] ,
          sysRoleId:[
            { required: true, message: '请选择角色', trigger: 'blur' }
          ]
        },
        peopleList:'',
      }
    },
    created() {
      this.getpeopleList();
      this.queryRoleOpt();
    },
    methods: {
      queryRoleOpt(){
        queryOptData2().then(res =>{
          if (res.code == 10000) {
            this.roleOpt = res.data;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      ai_dialog_closesq(){
        this.dialogFormVisiblesq = false;
      },
      handleCheckChange(data, checked, indeterminate) {
        console.log(data, checked, indeterminate);
      },
      //查询
      seeRole(){
      //  console.log(this.input1)
        this.currentPage = 1;
        var data={
          sysRoleId:this.rid,
          pageNum:this.currentPage
        }
        querySysUserList(data).then(res => {
          console.log(res)
          if(res.code==10000){
            this.peopleList = res.data
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //全部
      seeRoleAll(){
        this.rid=undefined;
        this.getpeopleList();
      },
      //复制
      handleCopy(e){
        this.copy(e)
      },
      //获取角色列表list
      getpeopleList(){
        var data={
          pageNum:this.currentPage
        }
        querySysUserList(data).then(res => {
          if(res.code==10000){
            this.peopleList = res.data
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //新增
      openDia(){
        this.dialogFormVisible=true;
        this.form.sysRoleId = null;
        this.form.roleName = null;
        this.form.sysUserCode = null;
        this.form.sysUserPassword = null;
        this.form.sysUserPhone= null;
        this.form.sysUserName = null;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
          //  console.log(this.form.username)
            var data={
              sysRoleId:this.form.sysRoleId,
              sysUserCode:this.form.sysUserCode,
              sysUserPassword:this.form.sysUserPassword,
              sysUserName:this.form.sysUserName,
              sysUserPhone:this.form.sysUserPhone,
              del :1
            }
            saveSysUser(data).then(res => {
              console.log(res)
              if(res.code==10000){
                this.$message.success(res.message);
                this.dialogFormVisible=false;
                this.$refs[formName].resetFields();
                this.getpeopleList();
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
      //编辑
      bjopen(index,row){
        this.dialogFormVisiblebj=true;
        this.formbj.sysUserName = row.sysUserName;
        this.formbj.sysRoleId = row.sysRoleId;
        this.formbj.sysUserId = row.sysUserId;
        this.formbj.roleName = row.roleName;
        this.formbj.sysUserCode = row.sysUserCode;
        this.formbj.sysUserPhone = row.sysUserPhone;
        this.formbj.sysUserPassword2 = null;
      },
      resetFormbj(formName) {
        this.$refs[formName].resetFields();
        this.dialogFormVisiblebj=false;
      },
      submitFormbj(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data={
              sysUserId:this.formbj.sysUserId,
              sysRoleId:this.formbj.sysRoleId,
              sysUserPassword2:this.formbj.sysUserPassword2,
              sysUserName:this.formbj.sysUserName,
              sysUserPhone:this.formbj.sysUserPhone,
              sysUserCode:this.formbj.sysUserCode,
              del:1
            }
            saveSysUser(data).then(res => {
              console.log(res)
              if(res.code==10000){
                this.$message.success(res.message);
                this.dialogFormVisiblebj=false;
                this.$refs[formName].resetFields();
                this.getpeopleList();
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
      ai_dialog_close(){
        this.dialogFormVisible=false;
        this.$refs[`form`].resetFields();
      },
      ai_dialog_closebj(){
        this.dialogFormVisiblebj=false;
        this.$refs[`formbj`].resetFields();
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },
      removeThis(index,row) {
      //  console.log(row)
        var data={
          sysUserId:row.sysUserId,
          del:0
        }
        this.$confirm('您确定删除这个用户吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          saveSysUser(data).then(res=>{
            if(res.code==10000){
              this.$message.success(res.message);
              this.getpeopleList();
            }else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //分页事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`)

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`)
      },
      refresh() {

      },
    }
  }
</script>

<style lang="less">
  #recharge{
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



    .app-box-date, .el-date-picker, .el-date-editor {
      width: 140px !important;
    }

    .app-box-date > .el-input__inner {
      width: 140px !important;
    }
  }
</style>
