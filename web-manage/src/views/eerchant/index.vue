<template>
  <div id="eerchant" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">组织机构</div>
        <div class="app-box-changeBox">
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-circle-plus-outline" @click="openDia">新增</el-button>
          <div class="app-box-input app-marginR">
            <div class="app-box-input-txt">机构名称：</div>
            <el-input placeholder="请输入机构名称" v-model="input1"></el-input>
          </div>
          <div class="app-btn-box">
            <el-button type="primary" icon="el-icon-search" class="app-tab-btn2" @click="queryname">查找</el-button>
            <el-button type="primary" icon="el-icon-menu" @click="seeAll">全部</el-button>
          </div>
        </div>

        <div class="app-tab-box">
          <el-table
            :data="officeLsit.list"
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
                <span v-if="item.prop==='officeName'" @click="handleCopy(scope.row.officeName)">
                             {{scope.row.officeName}}
                            </span>
                <!-- 正常的其他列 -->
                <span v-else>{{scope.row[item.prop]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="250">
              <template slot-scope="scope">
                <el-button type="primary" class="app-tab-btn app-tab-btn1" @click="addChild(scope.$index, scope.row)">添加下级</el-button>
                <el-button type="primary" class="app-tab-btn app-tab-btn2" @click="bjThis(scope.$index, scope.row)">编辑</el-button>
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
                :total=officeLsit.total>
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
                <span>新增机构</span>
            </span>
      <div class="payNameDiaBox">
        <el-form ref="form" :model="form" label-width="130px" :rules="rules">
          <el-form-item label="机构名称：" prop="username">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="上级机构：" prop="region">
            <el-cascader
              :disabled="isDisabled"
              v-model="form.region"
              :show-all-levels="false"
              :options="options"
              @change="handleChange"></el-cascader>
          </el-form-item>
          <el-form-item label="负责人：" prop="ptname">
            <el-input v-model="form.ptname"></el-input>
          </el-form-item>
          <el-form-item label="联系方式：" prop="tel">
            <el-input v-model.number="form.tel" type="number"></el-input>
          </el-form-item>
          <el-form-item label="排序：" prop="txt">
            <el-input v-model="form.txt"></el-input>
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
  import {getoffice} from '@/api/user'
  export default {
    name: 'eerchant',
    data() {
      var username = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入机构名称'));
        } else {
          callback();
        }
      };
      var ptname = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入负责人'));
        } else {
          callback();
        }
      };
      var gettel = (rule, value, callback) => {
        var reg = /^1[3456789]\d{9}$/;
        if (value === '') {
          callback(new Error('请输入手机号码'));
        }else if(!reg.test(value)){
          callback(new Error('请输入正确手机号'));
        } else {
          callback();
        }
      };
      var region = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择上级机构'));
        } else {
          callback();
        }
      };
      var txt = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入排序'));
        } else {
          callback();
        }
      };
      return {
        isDisabled:false,
        dialogFormVisible:false,
        currentPage:1,
        input1: '',
        value: [],
        options: [{
          value: 'zhinan',
          label: '指南',
          children: [{
            value: 'shejiyuanze',
            label: '设计原则',
            children: [{
              value: 'yizhi',
              label: '一致'
            }, {
              value: 'fankui',
              label: '反馈'
            }, {
              value: 'xiaolv',
              label: '效率'
            }, {
              value: 'kekong',
              label: 'OS平台第二版'
            }]
          }, {
            value: 'daohang',
            label: '导航',
            children: [{
              value: 'cexiangdaohang',
              label: '侧向导航'
            }, {
              value: 'dingbudaohang',
              label: '顶部导航'
            }]
          }]
        }],
        tabHead: [
          {
            label: '机构名称',
            prop: 'officeName'
          },
          {
            label: '负责人',
            prop: 'leader'
          },
          {
            label: '联系方式',
            prop: 'phone'
          },
          {
            label: '排序',
            prop: 'treeSort'
          },
        ],
        form: {
          username: '',
          ptname:"",
          tel:"",
          region:"",
          txt: ""
        },
        rules:{
          username: [
            { validator: username, trigger: 'blur' }
          ],
          ptname: [
            { validator: ptname, trigger: 'blur' }
          ],
          tel: [
            { validator: gettel, trigger: 'blur' }
          ],
          region: [
            { validator: region, trigger: 'blur' }
          ],
          txt: [
            { validator: txt, trigger: 'blur' }
          ],
        },
        officeLsit:''
      }
    },
    created() {
      this.getzzlist()
    },
    methods: {
      //查询
      queryname(){
        console.log(this.input1)
        this.currentPage = 1;
        var data={
          officeName:this.input1,
          pageNum:this.currentPage
        }
        getoffice(data).then(res => {
          console.log(res)
          if(res.code==10000){
            this.officeLsit = res.data
          }else {
            this.$message.error(res.message)
          }
        })
      },
      seeAll(){
        this.currentPage = 1;
        this.getzzlist()
      },
      //复制
      handleCopy(e){
        this.copy(e)
      },
      //添加下级
      addChild(index,row){
        console.log(row);
        this.dialogFormVisible = true;
        this.isDisabled = true;
        this.form={
          username: '',
          ptname:"",
          tel:"",
          region:"kekong",
          txt: ""
        }
      },
      //编辑
      bjThis(index,row){
        console.log(row);
        this.dialogFormVisible = true;
        this.isDisabled = true;
        this.form={
          username: row.officeName,
          ptname:row.leader,
          tel:row.phone,
          region:"kekong",
          txt: row.treeSort
        }
      },
      //获取组织结构列表
       getzzlist(){
         var data={
           "address": undefined,
           "fullName": undefined,
           "leader": undefined,
           "officeCode": undefined,
           "officeId": undefined,
           "officeName": undefined,
           "pageNum": this.currentPage,
           "pageSize": undefined,
           "parentId": undefined,
         }
         getoffice(data).then(res => {
           console.log(res)
           if(res.code==10000){
              this.officeLsit = res.data
           }else {
             this.$message.error(res.message)
           }
         })
      },
      handleChange(value) {
        console.log(value);
      },
      //删除
      removeThis(index,row){
        console.log(row);
        this.$confirm('您确定删除该机构吗？', '提示', {
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
      tabheaderFn() {
        return 'background:#F6F7FB;color:#3B3269'
      },
      //分页事件
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.getzzlist()
      },
      refresh(){

      },
      ai_dialog_close(){
        this.dialogFormVisible=false;
        this.$refs[`form`].resetFields();
      },
      openDia(){
        this.isDisabled = false;
        this.dialogFormVisible=true;
        this.form={
          username: '',
          ptname:"",
          tel:"",
          region:"",
          txt: ""
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
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
    }
  }
</script>

<style lang="less">
  #eerchant {
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
  }
</style>
