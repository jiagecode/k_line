<template>
  <div id="transaction" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">用户管理</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增
          </el-button>

          <div class="app-box-input">
            <div class="app-box-input-txt">用户名称：</div>
            <el-input placeholder="请输入用户名称" v-model="input1"></el-input>
          </div>
          <div class="app-box-select app-marginR" style="text-align: right">
            <div class="app-box-input-txt">状态：</div>
            <el-select v-model="region1" placeholder="请选择">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="2"></el-option>
            </el-select>
          </div>


          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeRole">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" class="app-tab-btn2" @click="seeRoleAll">全部</el-button>
          </div>
        </div>
        <div class="app-tab-box">
          <el-table
            :data="userList.list"
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
                <span v-if="item.prop==='locked'">
                             {{scope.row.locked | locked}}
                </span>
                <span v-else-if="item.prop==='sysUserName'"  @click="handleCopy(scope.row.sysUserName)">
                             {{scope.row.sysUserName}}
                            </span>
                <span v-else-if="item.prop==='office.officeName'">
                             {{scope.row.office['officeName']}}
                            </span>
                <span v-else-if="item.prop==='role.roleName'">
                             {{scope.row.role['roleName']}}
                            </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="300">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjUser(scope.$index, scope.row)">编辑
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="changePass(scope.$index, scope.row)">重置密码</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">启用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.locked==1"
                           @click="changeUser(scope.$index, scope.row)">禁用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeThis(scope.$index, scope.row)">
                  删除
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
                :total=userList.total>
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
          <!--            <el-form-item label="归属机构：" prop="region">-->
          <!--              <el-cascader-->
          <!--                v-model="form.region"-->
          <!--                :show-all-levels="false"-->
          <!--                :options="options"-->
          <!--                @change="handleChange"></el-cascader>-->

          <!--            </el-form-item>-->
          <!--          <el-form-item label="登录账号：" prop="username">-->
          <!--            <el-input v-model="form.username"></el-input>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="登陆密码：" prop="userpass">-->
          <!--            <el-input v-model="form.userpass"></el-input>-->
          <!--          </el-form-item>-->
          <el-form-item label="选择角色：" prop="region1">
            <el-select v-model="form.region1" placeholder="请选择">
              <el-option v-for="(item,index) in roleArr" :label="item.roleName" :value="item.roleId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户昵称：" prop="niname">
            <el-input v-model="form.niname"></el-input>
          </el-form-item>
          <el-form-item label="联系方式：" prop="tel">
            <el-input v-model.number="form.tel" type="number"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-dialog>
    <!--编辑-->
    <el-dialog :visible.sync="dialogFormVisiblebj" :close-on-click-modal="false"
               :before-close="ai_dialog_closebj">
            <span slot="title" class="dialog-footer">
                <span>修改用户</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="formbj" :model="formbj" label-width="130px" :rules="rules">
          <el-form-item label="登录账号：" prop="username">
            <el-input v-model="formbj.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="选择角色：" prop="region1">
            <el-select v-model="formbj.region1" placeholder="请选择" @change="changeSele">
              <el-option v-for="(item,index) in roleArr" :label="item.roleName" :value="item.roleId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户昵称：" prop="niname">
            <el-input v-model="formbj.niname"></el-input>
          </el-form-item>
          <el-form-item label="联系方式：" prop="tel">
            <el-input v-model.number="formbj.tel" type="number"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormbj('formbj')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormbj('formbj')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <!--重置密码-->
    <el-dialog :visible.sync="dialogFormVisiblepass" :close-on-click-modal="false"
               :before-close="ai_dialog_closepass">
            <span slot="title" class="dialog-footer">
                <span>重置密码</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="formPass" :model="formPass" label-width="130px" :rules="rules">
          <el-form-item label="新密码：" prop="newpass">
            <el-input v-model="formPass.newpass"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormpass('formPass')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormpass('formPass')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listUser, listPeople, addUser, removeUser, changeUser,guanliUser } from '@/api/adminUser'

  export default {
    name: 'transaction',
    data() {
      var tel = (rule, value, callback) => {
        var reg = /^1[3456789]\d{9}$/
        if (value === '') {
          callback(new Error('请输入手机号码'))
        } else if (!reg.test(value)) {
          callback(new Error('请输入正确手机号'))
        } else {
          callback()
        }
      }
      var oldpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入原密码'))
        } else {
          callback()
        }
      }
      var newpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'))
        } else {
          callback()
        }
      }
      return {
        roleArr: [],
        currentPage: 1,
        input1: '',
        region1: '',
        tabHead: [
          {
            label: '归属机构',
            prop: 'office.officeName'
          },
          {
            label: '用户名称',
            prop: 'sysUserName'
          },
          {
            label: '用户账号',
            prop: 'sysUserId'
          },
          {
            label: '角色',
            prop: 'role.roleName'
          },
          {
            label: '手机',
            prop: 'phone'
          },
          {
            label: '状态',
            prop: 'locked'
          },
          {
            label: '创建时间',
            prop: 'createTime'
          }
        ],
        dialogFormVisible: false,
        dialogFormVisiblebj: false,
        dialogFormVisiblepass:false,
        form: {
          region: '',
          username: '',
          userpass: '',
          region1: '',
          niname: '',
          tel: ''
        },
        formbj: {
          username: '',
          region1: '',
          niname: '',
          tel: ''
        },
        bjroleId:"",
        formPass:{
          oldpass:"",
          newpass:""
        },
        passId:"",
        rules: {
          region: [
            { required: true, message: '请选择归属机构', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '请输入登录账号', trigger: 'blur' }
          ],
          userpass: [
            { required: true, message: '请输入登陆密码', trigger: 'blur' }
          ],
          region1: [
            { required: true, message: '请选择角色', trigger: 'blur' }
          ],
          niname: [
            { required: true, message: '请输入用户昵称', trigger: 'blur' }
          ],
          tel: [
            { validator: tel, trigger: 'blur' }
          ],
          oldpass: [
            { validator: oldpass, trigger: 'blur' }
          ],
          newpass: [
            { validator: newpass, trigger: 'blur' }
          ],
        },
        userList: '',
        select: '',
      }
    },
    created() {
      this.getuserList()
    },
    methods: {
      //获取用户信息列表
      getuserList() {
        var data = {
          pageNum: this.currentPage
        }
        listUser(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.userList = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },
      ai_dialog_closebj() {
        this.dialogFormVisiblebj = false
        this.$refs[`formbj`].resetFields()
      },
      openDia() {
        this.dialogFormVisible = true
        var data = {}
        listPeople(data).then(res => {  //获取角色
          console.log(res)
          if (res.code == 10000) {
            var arr = []
            let roleArr = res.data.list
            for (var i = 0; i < roleArr.length; i++) {
              var obj = {}
              obj.roleId = roleArr[i].roleId
              obj.roleName = roleArr[i].roleName
              arr.push(obj)
            }
            console.log(arr)
            this.roleArr = arr
          }
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data = {
              officeId: 1,
              roleId: this.form.region1,
              sysUserName: this.form.niname,
              phone: this.form.tel
            }
            addUser(data).then(res => {
              console.log(res)
              if (res.code == 10000) {
                this.$message.success(res.message)
                this.dialogFormVisible = false
                this.$refs[formName].resetFields()
                this.getuserList()
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      //禁用启用
      changeUser(index, row) {
        console.log(row)
        let locked = row.locked
        var userlocked = ''
        if (locked == 1) {
          userlocked = 2
        } else {
          userlocked = 1
        }
        var data = {
          locked: userlocked,
          sysUserId: row.sysUserId
        }
        guanliUser(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            this.getuserList()
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //重置密码
      changePass(index,row){
        console.log(row)
        this.dialogFormVisiblepass = true;
        this.passId = row.sysUserId;

      },
      resetFormpass(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisiblepass = false
      },
      submitFormpass(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data={
              password:this.formPass.newpass,
              sysUserId:this.passId
            }
            guanliUser(data).then(res => {
              console.log(res)
              if (res.code == 10000) {
                this.dialogFormVisiblepass=false;
                this.$refs[formName].resetFields()
                this.getuserList()
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
      ai_dialog_closepass(){
        this.dialogFormVisiblepass = false;
        this.$refs[`formPass`].resetFields()
      },

      resetForm(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisible = false
      },
      //点击编辑
      bjUser(index, row) {
        console.log(row)
        this.bjroleId = row.sysUserId;
        this.dialogFormVisiblebj = true
        var data = {}
        var arr = []
        listPeople(data).then(res => {  //获取角色
          console.log(res)
          if (res.code == 10000) {

            let roleArrs = res.data.list
            for (var i = 0; i < roleArrs.length; i++) {
              var obj = {}
              obj.roleId = roleArrs[i].roleId
              obj.roleName = roleArrs[i].roleName
              arr.push(obj)
            }
            console.log(arr)


            this.formbj = {
              username: row.sysUserId,
              niname: row.sysUserName,
              tel: row.phone
            }

            let roleId = row.roleId
            console.log(arr)
            for (var j = 0; j < arr.length; j++) {
              if (roleId == arr[j].roleId) {
                // this.formbj.region1 = arr[j].roleName
                this.select = arr[j].roleId
              }
            }
            console.log(this.formbj)
            this.roleArr = arr
          }
        })


      },
      changeSele(value){
        console.log(value)
        this.select = value;
      },
      resetFormbj(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisiblebj = false
      },
      submitFormbj(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.formbj)
            var data = {
              sysUserName: this.formbj.niname,
              phone: this.formbj.tel,
              roleId: this.select,
              officeId: 1,
              sysUserId:this.bjroleId
            }
            addUser(data).then(res => {
              console.log(res)
              if (res.code == 10000) {
                this.$message.success(res.message)
                this.dialogFormVisiblebj = false
                this.$refs[formName].resetFields()
                this.getuserList()
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      handleChange(val) {
        console.log(val)
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },
      removeThis(index, row) {
        console.log(row)
        var data = {
          id: row.sysUserId,
          flag: 1
        }

        this.$confirm('您确定删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          removeUser(data).then(res => {
            if (res.code == 10000) {
              this.$message.success(res.message)
              this.getuserList()
            } else {
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
        this.getuserList()
      },
      refresh() {
        this.currentPage = 1;
        this.getuserList()
      },
      //复制
      handleCopy(e){
        this.copy(e)
      },
      //查询
      seeRole(){
        console.log(this.input1)
        this.currentPage = 1;
        var data={
          sysUserName:this.input1,
          locked:this.region1,
          pageNum:this.currentPage
        }
        listUser(data).then(res => {
          if(res.code==10000){
            this.userList = res.data
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //全部
      seeRoleAll(){
        this.input1="";
        this.region1='';
        this.getuserList();
      },
    },
    filters: {
      locked(val) {
        return val == 1 ? '启用' : '禁用'
      }
    }
  }
</script>

<style lang="less">
  #transaction {
    height: 800px;
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;

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
  }
</style>
