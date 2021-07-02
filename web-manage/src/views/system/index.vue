<template>
  <div id="system" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">系统账户设置</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增
          </el-button>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">账户名称：</div>
            <el-input placeholder="请输入账户名称" v-model="input1"></el-input>
          </div>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">账户账号：</div>
            <el-input placeholder="请输入账户账号" v-model="input2"></el-input>
          </div>
          <div class="app-box-input">
            <div class="app-box-input-txt">账户手机：</div>
            <el-input placeholder="请输入账户手机" v-model="input3"></el-input>
          </div>
          <div class="app-box-select app-marginR">
            <div class="app-box-input-txt">状态：</div>
            <el-select v-model="region" placeholder="状态">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="2"></el-option>
            </el-select>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" @click="seeOther">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
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
                <span v-if="item.prop==='sysUserName'" @click="handleCopy(scope.row.sysUserName)">
                             {{scope.row.sysUserName}}
                </span>
                <span v-else-if="item.prop==='sysUserId'" @click="handleCopy(scope.row.sysUserId)">
                             {{scope.row.sysUserId}}
                            </span>
                <span v-else-if="item.prop==='phone'" @click="handleCopy(scope.row.phone)">
                             {{scope.row.phone}}
                            </span>
                <span v-else-if="item.prop==='locked'">
                             {{scope.row.locked | locked}}
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
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="openBj(scope.$index, scope.row)">编辑
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="openPass(scope.$index, scope.row)">
                  重置密码
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" v-if="scope.row.locked==1"
                           @click="changeUser(scope.$index, scope.row)">禁用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" v-if="scope.row.locked==2"
                           @click="changeUser(scope.$index, scope.row)">启用
                </el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeTab(scope.$index, scope.row)">
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
                <span>新增账户</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="130px" :rules="rules">
          <el-form-item label="账户名称：" prop="username">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
<!--          <el-form-item label="选择角色：" prop="region">-->
<!--            <el-select v-model="form.region" placeholder="角色类型">-->
<!--              <el-option v-for="(item,index) in peopleList" :label="item.roleName" :value="item.roleId"></el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
          <el-form-item label="手机号码：" prop="tel">
            <el-input v-model.number="form.tel" type="number"></el-input>
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
                <span>修改账户</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="formbj" :model="formbj" label-width="130px" :rules="rules">
          <el-form-item label="账户账号：">
            <el-input v-model="formbj.usercode" disabled></el-input>
          </el-form-item>
          <el-form-item label="账户名称：" prop="username">
            <el-input v-model="formbj.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="选择角色：" prop="region">
            <el-select v-model="formbj.region" disabled placeholder="角色类型" @change="changebjroleId">
              <el-option v-for="(item,index) in peopleList" :label="item.roleName" :value="item.roleId"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号码：" prop="tel">
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
        <el-form ref="formpass" :model="formpass" label-width="130px" :rules="rules">
          <el-form-item label="重置密码：" prop="oldpass">
            <el-input v-model="formpass.oldpass" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="newpass">
            <el-input v-model="formpass.newpass" type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormpass('formpass')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormpass('formpass')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listUser, listPeople, addUser, removeUser, changeUser, guanliUser } from '@/api/adminUser'

  export default {
    name: 'system',
    data() {
      var usercode = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账户账号'))
        } else {
          callback()
        }
      }
      var userpass = (rule, value, callback) => {
        var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
        if (value === '') {
          callback(new Error('请输入密码'))
        } else if (!reg.test(value)) {
          callback(new Error('密码必须是字母和数字组成的六位以上'))
        } else {
          callback()
        }
      }
      var oldpass = (rule, value, callback) => {
        var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
        if (value === '') {
          callback(new Error('请输入密码'))
        } else if (!reg.test(value)) {
          callback(new Error('密码必须是字母和数字组成的六位以上'))
        } else {
          callback()
        }
      }
      var username = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账户名称'))
        }else if(value.length<3||value.length>11){
          callback(new Error('长度在3到11个字符'));
        } else {
          callback()
        }
      }
      var region = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择角色类型'))
        } else {
          callback()
        }
      }
      var tel = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入手机号'))
        } else if (!(/^1[3456789]\d{9}$/.test(value))) {
          callback(new Error('请输入正确手机号号'))
        } else {
          callback()
        }
      }
      var newpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value != this.formpass.oldpass) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      }

      return {
        input1: '',
        input2: '',
        input3: '',
        region: '',
        dialogFormVisible: false,
        dialogFormVisiblebj: false,
        dialogFormVisiblepass: false,
        tabHead: [
          {
            label: '账户名称',
            prop: 'sysUserName'
          },
          {
            label: '账户账号',
            prop: 'sysUserId'
          },
          {
            label: '角色',
            prop: 'role.roleName'
          },
          {
            label: '手机号码',
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
        form: {
          username: '',
          userpass: '',
          usercode: '',
          region: '',
          tel: ''
        },
        formbj: {
          usercode: '',
          username: '',
          region: '非超级管理员',
          tel: ''
        },
        formpass: {
          oldpass: '',
          newpass: ''
        },
        rules: {
          usercode: [
            { validator: usercode, trigger: 'blur' }
          ],
          userpass: [
            { validator: userpass, trigger: 'blur' }
          ],
          username: [
            { validator: username, trigger: 'blur' }
          ],
          region: [
            { validator: region, trigger: 'change' }
          ],
          tel: [
            { validator: tel, trigger: 'blur' }
          ],
          newpass: [
            { validator: newpass, trigger: 'blur' }
          ],
          oldpass:[
            { validator: oldpass, trigger: 'blur' }
          ]
        },
        userList: '',
        currentPage: 1,
        peopleList: [],
        bjroleId: '',
        passId:""
      }
    },
    created() {
      this.getUserList()
      this.getpeopleList()
    },
    methods: {
      //查询
      seeOther() {
        this.currentPage = 1
        var data = {
          sysUserName: this.input1,
          sysUserId: this.input2,
          phone: this.input3,
          locked: this.region,
          userType: 2,
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
      seeAll() {
        this.input1 = ''
        this.input2 = ''
        this.input3 = ''
        this.region = ''
        this.currentPage = 1
        this.getUserList()
      },

      //获取角色列表
      getpeopleList() {
        var data = {
          pageNum: this.currentPage,
          userType: 2
        }
        listPeople(data).then(res => {
          console.log(res)
          if (res.code == 10000) {
            let list = res.data.list
            var arr = []
            for (var i = 0; i < list.length; i++) {
              var obj = {}
              obj.roleId = list[i].roleId
              obj.roleName = list[i].roleName
              arr.push(obj)
            }
            this.peopleList = arr
          } else {
            this.$message.error(res.message)
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
            this.getUserList()
          } else {
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
        this.getUserList()
      },
      //获取账户列表
      getUserList() {
        var data = {
          pageNum: this.currentPage,
          userType: 2
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
      changebjroleId(val) {
        this.bjroleId = val
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },

      refresh() {
        this.currentPage = 1
        this.getUserList()
      },
      openDia() {
        this.dialogFormVisible = true
      },
      openBj(index, row) {
        console.log(row)
        this.dialogFormVisiblebj = true
        this.bjroleId = row.roleId
        this.formbj = {
          usercode: row.sysUserId,
          username: row.sysUserName,
          region: row.role.roleName,
          tel: row.phone
        }
      },
      openPass(index, row) {
        this.dialogFormVisiblepass = true;
        this.passId=row.sysUserId
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data = {
              sysUserName: this.form.username,
              phone: this.form.tel,
              officeId:1,
              userType:2,
              // roleId:this.form.region
              // role: {
              //   roleName: this.form.region
              // }
            }
            addUser(data).then(res => {
              if (res.code == 10000) {
                this.$message.success(res.message);
                this.getUserList();
                this.$refs[formName].resetFields()
                this.dialogFormVisible = false
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
      resetForm(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisible = false
      },
      ai_dialog_close() {
        this.dialogFormVisible = false
        this.$refs[`form`].resetFields()
      },
      ai_dialog_closebj() {
        this.dialogFormVisiblebj = false
        this.$refs[`formbj`].resetFields()
      },
      ai_dialog_closepass() {
        this.dialogFormVisiblepass = false
        this.$refs[`formpass`].resetFields()
      },
      submitFormbj(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data = {
              sysUserName: this.formbj.username,
              roleId: this.bjroleId,
              phone: this.formbj.tel,
              sysUserId: this.formbj.usercode,
              officeId:1
            }
            addUser(data).then(res => {
              console.log(res)
              if (res.code == 10000) {
                this.$message.success(res.message)
                this.$refs[formName].resetFields()
                this.dialogFormVisiblebj = false
                this.getUserList()
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
      resetFormbj(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisiblebj = false
      },
      submitFormpass(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data={
              password:this.formpass.newpass,
              sysUserId:this.passId
            }
            guanliUser(data).then(res=>{
              if(res.code==10000){
                this.$message.success(res.message);
                this.getUserList();
                this.$refs[formName].resetFields();
                this.dialogFormVisiblepass = false;
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
      resetFormpass(formName) {
        this.$refs[formName].resetFields()
        this.dialogFormVisiblepass = false
      },
      //复制
      handleCopy(e) {
        this.copy(e)
      },
      removeTab(index, row) {
        this.$confirm('此操作将永久删除该账号, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          var data = {
            id: row.sysUserId,
            flag: 1
          }
          removeUser(data).then(res => {
            if (res.code == 10000) {
              this.$message.success(res.message)
              this.getUserList()
            } else {
              this.$message.error(res.message)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
    },
    filters: {
      locked(val) {
        return val == 1 ? '启用' : '禁用'
      }
    }
  }
</script>

<style lang="less">
  #system {
    height: 800px;
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;

    .el-select {
      width: 100px !important;
    }

    .app-box-input-txt {
      text-align: right;
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
