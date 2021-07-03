<template>
    <div id="role"  class="app-container">
      <div class="app-box">
        <div class="app-box-tab">
          <div class="app-box-title">菜单管理</div>
          <div class="app-box-changeBox">
            <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增</el-button>
          </div>
          <div class="tableData">

            <el-table  :header-cell-style="tabheaderFn" v-loading="listLoading" element-loading-text="数据加载中..." :data="menuList" style="width: 100%;margin-bottom: 20px;"
                      row-key="menu.menuId" border lazy :tree-props="{children: 'children'}">
              <el-table-column prop="menuName" label="菜单名称" width="200px">
                <template slot-scope="scope">
                  {{scope.row.menu.menuName}}
                </template>
              </el-table-column>
              <el-table-column prop="menuHref" label="路径">
                <template slot-scope="scope">
                  {{scope.row.menu.menuHref}}
                </template>
              </el-table-column>
              <el-table-column prop="menuComponent" label="组件名称">
                <template slot-scope="scope">
                  {{scope.row.menu.menuComponent}}
                </template>
              </el-table-column>
              <el-table-column prop="permission" label="权限标识">
                <template slot-scope="scope">
                  {{scope.row.menu.permission}}
                </template>
              </el-table-column>
              <el-table-column prop="menuIcon" label="菜单图标">
                <template slot-scope="scope">
                  {{scope.row.menu.menuIcon}}
                </template>
              </el-table-column>
              <el-table-column prop="menuType" align="center" label="类型">

                <template slot-scope="scope">
                  {{scope.row.menu.menuType | menuType}}
                </template>
              </el-table-column>
              <el-table-column prop="treeSort" label="排序">
                <template slot-scope="scope">
                  {{scope.row.menu.treeSort}}
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" width="300">
                <template slot-scope="scope">
                  <el-button type="primary" class="app-tab-btn app-tab-btn1" v-if="scope.row.menu.menuType!=3" @click="addchild(scope.$index, scope.row)">添加下级</el-button>
                  <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="changeRow(scope.$index, scope.row)">编辑</el-button>
                  <el-button type="primary" class="app-tab-btn app-tab-btn3" @click="removeThis(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false"
                 :before-close="ai_dialog_close">
            <span slot="title" class="dialog-footer">
                <span>新增菜单</span>
            </span>
        <div class="payNameDiaBox">
          <el-form ref="form" :model="form" label-width="130px" :rules="rules">
            <el-form-item label="菜单名称：" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="上级菜单：" v-if="form.menuType!=1">
              <el-select v-model="form.parentId" :disabled="addchildBtn" style="width: 100%;" ref="treeSelect" popper-class="select-tree"
                         placeholder="请选择" @clear="clearSelect">
                <el-option :value="optionValue" :label="optionLabel" style="height: auto">
                  <el-tree default-expand-all :expand-on-click-node="false" :data="treeList" node-key="id" ref="menuTree"
                           highlight-current :props="treeProps" @node-click="handleNodeClick" />
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="访问路径：" prop="userhref"  v-if="form.menuType!=3">
              <el-input v-model="form.userhref"></el-input>
            </el-form-item>
            <el-form-item label="组件名称："  v-if="form.menuType==2">
              <el-input v-model="form.zjname"></el-input>
            </el-form-item>
            <el-form-item label="权限标识：" v-if="form.menuType==3">
              <el-input v-model="form.qxbs"></el-input>
            </el-form-item>
            <el-form-item label="菜单图标：" >
              <el-input v-model="form.iconname"></el-input>
            </el-form-item>
            <el-form-item label="排序：">
              <el-input v-model.number="form.number" type="number"></el-input>
            </el-form-item>
            <el-form-item label="类型：">
              <el-radio-group v-model="form.menuType" :disabled="addBtn" @change="changeMenuType">
                <el-radio-button label="1">目录</el-radio-button>
                <el-radio-button label="2">菜单</el-radio-button>
                <el-radio-button label="3">按钮</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button class="payNameBnt1" @click="resetForm('form')">取消</el-button>
              <el-button class="payNameBnt2" @click="submitForm('form')">确定</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
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
  import {getmenu,addmenu,removemenu} from '@/api/user'
  export default {
    name: 'roleIndex',
    data(){
      return{
        input1: '',
        dialogFormVisible:false,
        dialogFormVisiblebj:false,
        form: {
          username: '',
          parentId:'',
          userhref:"",
          zjname:"",
          qxbs:"",
          iconname:"",
          number:"",
          menuId:'',
          menuType:2
        },
        formbj: {
          username: '',
        },
        rules:{
          username:[
            { required: true, message: '请填写菜单名称', trigger: 'blur' }
          ],
          parentId:[
            { required: true, message: '请选择上级菜单', trigger: 'blur' }
          ],
          userhref:[
            { required: true, message: '请填写访问路径', trigger: 'blur' }
          ]
        },
        menuList:[],
        treeList:[],
        listLoading: true,
        treeProps: {
          children: 'children',
          label: function(data, node) {
            return data.menu.menuName
          }
        },
        optionValue: 0,
        optionLabel: '根节点',
        addBtn:false,
        addchildBtn:false,
      }
    },
    created() {
      this.getmenuList();
    },
    methods: {
      //点击添加下级
      addchild(index,row){
        this.dialogFormVisible = true;
        this.form={
          username: '',
          parentId:'',
          userhref:"",
          zjname:"",
          qxbs:"",
          iconname:"",
          number:"",
          menuId:'',
          menuType:2
        }
        console.log(row);
        console.log(row.menu.menuType)
        let menuType = row.menu.menuType;
        this.addBtn = true;
        this.addchildBtn = true;

        this.form.menuType = menuType+1;
        this.optionValue = row.menu.menuId
        this.optionLabel = row.menu.menuName
        this.form.parentId = row.menu.menuId
      },
      //点击编辑
      changeRow(index,row){
        console.log(row)
        console.log(row.parentId)
        console.log(this.treeList)

        let menuType = row.menu;
        this.addBtn = true;
        this.dialogFormVisible = true;
        if(menuType.menuType==3){
          this.addchildBtn = true;
        }else {
          this.addchildBtn = false;
        }
        this.optionValue = menuType.menuId
        this.optionLabel = menuType.menuName
        console.log(menuType.menuName)
        this.form={
          username:menuType.menuName,
          menuId:menuType.menuId,
          userhref:menuType.menuHref,
          zjname:menuType.menuComponent,
          qxbs:menuType.permission,
          iconname:menuType.menuIcon,
          number:menuType.treeSort,
          menuType: menuType.menuType
        }
      },
      //点击删除
      removeThis(index,row) {
        console.log(row.menu.menuId)
        var data={
          menuId:row.menu.menuId,
          flag:1
        }

        this.$confirm('您确定删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          removemenu(data).then(res => {
            console.log(res)
            if(res.code==10000){
              this.$message.success(res.message);
              this.getmenuList();
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
      //清空上级机构
      clearSelect() {
        this.optionValue = 0
        this.optionLabel = '根节点'
      },
      handleNodeClick(data, node, e) {
        this.optionValue = data.menu.id
        this.optionLabel = data.menu.menuName
        this.form.parentId = data.menu.id
        this.$refs.treeSelect.blur()
      },
      //切换选项
      changeMenuType(){
        this.$refs[`form`].resetFields();
        this.optionValue = 0;
      },
      //获取菜单列表
      getmenuList(){
        var data={}
        this.listLoading = true
        getmenu(data).then(res => {
          if(res.code==10000){
            this.menuList = res.data.menuList
            this.treeList = res.data.treeList
            this.listLoading = false
          }

        })
      },
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },


      refresh() {

      },
      openDia(){
        this.dialogFormVisible=true;
        this.form={
          username: '',
          parentId:'',
          userhref:"",
          zjname:"",
          qxbs:"",
          iconname:"",
          number:"",
          menuId:'',
          menuType:2
        }
      },
      openBj(index,row){
        console.log(row)
        this.dialogFormVisiblebj=true;
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var data={
              menuName:this.form.username,  //菜单名称
              menuHref:this.form.userhref,   //路径
              menuComponent:this.form.zjname,  //组件名称
              parentId:this.optionValue,  //上级菜单ID
              permission:this.form.qxbs,  //权限标识
              menuIcon:this.form.iconname,  //菜单图标
              treeSort:this.form.number,//排序
              menuType:this.form.menuType,  //类型
              menuId:this.form.menuId
            }
            console.log(data)
            addmenu(data).then(res => {
              console.log(res)
              if(res.code==10000){
                this.$message.success(res.message);
                this.dialogFormVisible=false;

                this.addBtn = false;
                this.addchildBtn = false;
                this.form.menuType = 2;
                this.optionValue = 0;
                this.form.parentId = "";
                this.getmenuList();
                this.$refs[`form`].resetFields();
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

        this.dialogFormVisible= false;
        this.addBtn = false;
        this.addchildBtn = false;
        this.form.menuType = 2;
        this.form.parentId = "";
        this.optionValue = 0;
        this.$refs[formName].resetFields();
      },
      ai_dialog_close(){
        this.dialogFormVisible=false;

        this.addBtn = false;
        this.addchildBtn = false;
        this.form.menuType = 2;
        this.optionValue = 0;
        this.form.parentId = "";
        this.$refs[`form`].resetFields();
      },
      ai_dialog_closebj(){
        this.dialogFormVisiblebj=false;
        this.$refs[`formbj`].resetFields();
      },
      submitFormbj(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetFormbj(formName) {
        this.$refs[formName].resetFields();
        this.dialogFormVisiblebj= false;
      },
      removeTab(index,row){
        this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
    },
    filters:{
      menuType(val){
        return val == 1 ? "目录" : val == 2 ? "菜单" : '按钮';
      },
    }
  }
</script>

<style lang="less">
  #role{
    height: 800px;
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;
    .tableData{

      .el-table{
        height: 600px;
        overflow-y: auto;
      }
    }
    .select-tree .el-select-dropdown__item:hover {
      background-color: #FFF;
    }
    .el-select-dropdown__item:hover, .el-select-dropdown__item:hover{
      background-color: #ffffff!important;
    }
    .el-scrollbar{
      background-color: #ffffff!important;
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
    .el-dialog{
      width: 460px;
      .el-radio-button{
        height: 28px;
        line-height: 28px;
        padding: 0;
        width: 60px;
      }
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
