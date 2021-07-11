<template>
  <div id="recharge" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">角色管理</div>
        <div class="app-box-changeBox">
          <div class="app-box-input app-marginR">
            <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增</el-button>
            <div class="app-box-input app-marginR">
              <div class="app-box-input-txt">角色名称：</div>
              <el-input placeholder="请输入角色名称" v-model="input1"></el-input>
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
                <span v-if="item.prop==='roleName'" @click="handleCopy(scope.row.roleName)">
                             {{scope.row.roleName}}
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
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="sqPeople(scope.$index, scope.row)">授权</el-button>
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
                <span>新增角色</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="130px" :rules="rules">
          <el-form-item label="角色名称：" prop="username">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="英文标志：" prop="roleEnglishName">
            <el-input v-model="form.roleEnglishName"></el-input>
          </el-form-item>
          <el-form-item label="备注：" prop="universal">
            <el-input v-model="form.universal"></el-input>
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
                <span>修改角色</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="formbj" :model="formbj" label-width="130px" :rules="rules">
          <el-form-item label="角色名称：" prop="username">
            <el-input v-model="formbj.username"></el-input>
          </el-form-item>
          <el-form-item label="英文标志：" prop="roleEnglishName">
            <el-input v-model="formbj.roleEnglishName"></el-input>
          </el-form-item>
          <el-form-item label="备注：" prop="universal">
            <el-input v-model="formbj.universal"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="payNameBnt1" @click="resetFormbj('formbj')">取消</el-button>
            <el-button class="payNameBnt2" @click="submitFormbj('formbj')">确定</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-dialog>
    <!--授权-->
    <el-dialog style="max-height: 650px;text-align: center" :visible.sync="dialogFormVisiblesq" :close-on-click-modal="false" :before-close="ai_dialog_closesq">
      <el-tree
        :data="roleSq"
        show-checkbox
        highlight-current
        ref="menuTree"
        node-key="id"
        :default-checked-keys="defaultCheckedKeys"
        @check-change="handleCheckChange"
        :props="defaultProps">
      </el-tree>
      <el-button type="success" class="dialogBtn" @click="dialogBtn">确定</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import {listPeople,addPeople,removePeople,getpeopleSq,sqtoPeople} from '@/api/people'
  export default {
    name: 'menus',
    data() {
      return {
        value1: '',
        value2: '',
        currentPage: 1,
        input1: '',
        tabHead: [
          {
            label: '角色ID',
            prop: 'roleId'
          },
          {
            label: '角色名称',
            prop: 'roleName'
          }, {
            label: '英文标志',
            prop: 'roleEnglishName'
          }, {
            label: '子账户组',
            prop: 'roleType'
          }, {
            label: '是否启用',
            prop: 'locked'
          }, {
            label: '备注',
            prop: 'universal'
          }, {
            label: '创建时间',
            prop: 'addTime'
          }, {
            label: '修改时间',
            prop: 'editTime'
          },
        ],
        dialogFormVisible:false,
        dialogFormVisiblebj:false,
        dialogFormVisiblesq:false,
        form:{
          username:"",
          roleEnglishName:"",
          universal:''
        },
        formbj:{
          username:"",
          roleId:'',
          roleEnglishName:'',
          universal:''
        },
        rules:{
          username:[
            { required: true, message: '请填写角色名称', trigger: 'blur' }
          ]
        },
        peopleList:'',
        roleSq:"",
        defaultCheckedKeys:[],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        sqroleId:''
      }
    },
    created() {
      this.getpeopleList();
    },
    methods: {
      //授权
      sqPeople(index,row){
        console.log(row)
        this.sqroleId = row.roleId
        var data={
          roleId:row.roleId,
          del :1
        }
        getpeopleSq(data).then(res=>{
          console.log(res)
          if(res.code==10000){
            var _this = this;
            _this.dialogFormVisiblesq = true;
            _this.roleSq = res.data.treeList;
            const checkedIds =  res.data.checkedIds;
            let menuIdArr = [];
            for (let j = 0; j < checkedIds.length; j++) {
              const menuId = checkedIds[j];
              // console.log(menuId);
              let key = _this.getNodeKeys(menuId, _this.roleSq);
              if (key != "") {
                menuIdArr.push(key)
              }
            }
            console.log(menuIdArr)
            _this.defaultCheckedKeys = menuIdArr
          }else {
            this.$message.error(res.message)
          }
        })
      },
      //获取选中的孩子节点
      getNodeKeys(menuId, children) {
        let key = ""
        for (let i = 0; i < children.length; i++) {
          const menu = children[i]
          let id = menu.id
          let childs = menu.children
          if (id == menuId) {
            if (childs && childs.length > 0) {
              key = this.getNodeKeys(menuId, childs)
            } else {
              return menuId
            }
          } else if (childs && childs.length > 0) {
            key = this.getNodeKeys(menuId, childs)
          }
          if (key != "") {
            return key
          }
        }
        return key
      },
      ai_dialog_closesq(){
        this.dialogFormVisiblesq = false;
      },
      handleCheckChange(data, checked, indeterminate) {
        console.log(data, checked, indeterminate);
      },
      dialogBtn(){
        let nodes = this.$refs.menuTree.getCheckedNodes(false, true)
        console.log(nodes);
        if(nodes.length==0){
          this.$message.error({
            message: "请勾选菜单权限！"
          })
        }else{
          let menuIds = []
          for (let i = 0; i < nodes.length; i++) {
            menuIds.push(nodes[i].id)
          }
          menuIds = menuIds.join(',');
          console.log(menuIds);
          var data={
            roleId:this.sqroleId,
            menuIdStr:menuIds
          }
          sqtoPeople(data).then(res=>{
            if(res.code==10000){
              this.$message.success(res.message);
              this.dialogFormVisiblesq = false;
            }else {
              this.$message.error(res.message)
            }
          })
        }
      },
      //查询
      seeRole(){
        console.log(this.input1)
        this.currentPage = 1;
        var data={
          roleName:this.input1,
          pageNum:this.currentPage
        }
        listPeople(data).then(res => {
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
        this.input1="";
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
        listPeople(data).then(res => {
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
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.form.username)
            var data={
              roleName:this.form.username,
              universal:this.form.universal,
              del :1
            }
            addPeople(data).then(res => {
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
        console.log(row);
        this.dialogFormVisiblebj=true;
        this.formbj.username = row.roleName;
        this.formbj.roleId = row.roleId;
        this.formbj.universal = row.universal;
        this.formbj.roleEnglishName = row.roleEnglishName;
      },
      resetFormbj(formName) {
        this.$refs[formName].resetFields();
        this.dialogFormVisiblebj=false;
      },
      submitFormbj(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data={
              roleName:this.formbj.username,
              roleId:this.formbj.roleId,
              universal:this.formbj.universal,
              roleEnglishName:this.formbj.roleEnglishName,
            }
            addPeople(data).then(res => {
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
        console.log(row)
        var data={
          roleId:row.roleId
        }

        this.$confirm('您确定删除角色组对应的用户信息吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          removePeople(data).then(res=>{
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
