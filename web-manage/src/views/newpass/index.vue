<template>
  <div id="password">
    <div class="signBox signBox1">
      <div class="signBox1Img">
        <img src="../../img/bg1.png" alt="bg">
      </div>
    </div>
    <div class="signBox signBox2">
      <div class="tabSign">
        <div class="tabTitle">忘记密码</div>
        <el-form :model="ruleForm" :label-position="labelPosition" :rules="rules" ref="ruleForm">
          <el-form-item label="手机号" prop="name">
            <el-input v-model="ruleForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="algnPass">
            <el-input type="password" v-model="ruleForm.algnPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code" class="newInput">
            <el-input v-model.number="ruleForm.code"></el-input>
            <div class="getTime" @click="changTime">{{content}}</div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          </el-form-item>
        </el-form>
        <div class="tabTxt1">
          <div class="tabTxt1_1" @click="goBack">返回登录</div>
        </div>
        <div class="tabTxt2">客服TG:juhao技术    QQ:99807999</div>
      </div>
    </div>
  </div>
</template>

<script>
  import {getcode,findPass} from '@/api/getpass'
  export default {
    name: 'newpass',
    data(){
      var name = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入手机号'));
        }else {
          callback();
        }
      };
      var pass = (rule, value, callback) => {
        var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
        if (value === '') {
          callback(new Error('请输入新密码'));
        }else if(!reg.test(value)){
          callback(new Error('密码必须是字母和数字组成的六位以上'));
        } else {
          callback();
        }
      };
      var algnPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入新密码'));
        }else if(value!=this.ruleForm.pass){
          callback(new Error('两次输入的密码不一致'));
        }else {
          callback();
        }
      };
      var code = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入验证码'));
        }else {
          callback();
        }
      };
      return{
        labelPosition:"top",
        ruleForm: {
          name: '',
          pass: '',
          algnPass:"",
          code: ''
        },
        rules: {
          name: [
            { validator: name, trigger: 'blur'}
          ],
          pass: [
            { validator: pass, trigger: 'blur'}
          ],
          algnPass: [
            { validator: algnPass, trigger: 'blur'}
          ],
          code: [
            { validator: code, trigger: 'blur'}
          ]
        },
        content: '发送验证码',
        totalTime: 60,
        canClick: true, //添加canClick
      }
    },
    methods:{
      submitForm(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('提交');
            var _this = this;
            var data={
              phone:_this.ruleForm.name,
              password:_this.ruleForm.pass,
              code:_this.ruleForm.code,
              systemType:1
            }
            findPass(data).then(res=>{
              if(res.code==10000){
                _this.$message.success(res.message);
                _this.$router.push('/login')
              }else {
                _this.$message.error(res.message)
              }
            })


          } else {

            return false;
          }
        });
      },
      goBack(){
        this.$router.push('/login')
      },
      //获取验证码
      changTime(){
        var _this = this;
        var data ={
          phone:_this.ruleForm.name
        }
        getcode(data).then(res=>{
          if(res.code==10000){
            if (!_this.canClick) return;
            _this.canClick = false;
            _this.content = _this.totalTime + 's后重新发送';
            let clock = window.setInterval(() => {
              _this.totalTime--;
              _this.content = _this.totalTime + 's后重新发送';
              if (_this.totalTime < 0) {
                window.clearInterval(clock);
                _this.content = '重新发送验证码';
                _this.totalTime = 60;
                _this.canClick = true;
              }
            },1000)
          }else {
            _this.$message.error(res.message)
          }
        })
      },
    },
    created() {
      var _this=this;
      document.onkeydown = function(event) {
        var key = window.event.keyCode;
        if (key == 13) {
          _this.submitForm(`ruleForm`);
        }
      }
    }
  }
</script>

<style lang="less">
  #password{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;

    .signBox {
      width: 50%;
      height: 100%;
      margin: 0 auto;
    }

    .signBox1 {
      background-color: #fff;
      position: relative;

      .signBox1Img {
        width: 82%;
        height: 600px;
        margin: 0 auto;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -300px;
        margin-left: -395px;
        img{
          width: 100%;
          height: 100%;
        }
      }
    }

    .signBox2 {
      background-color: #5C3882;
      .tabSign{
        width: 54%;
        margin: 0 auto;
        margin-top: 120px;
        text-align: left;
        .tabTitle{
          color: #FAFAFA;
          font-size: 20px;
          font-weight: 500;
          text-align: center;
        }
        .el-form-item__label{
          color: #FFFFFF;
          font-size: 13px!important;
          padding: 0;
        }
        .el-input__inner{
          background-color: #E9D8FC;
          color: #3B3269;
          font-size: 13px;
          height: 50px;
        }
        .el-button{
          width: 100%;
          background:rgba(32,222,255,1);
          opacity:1;
          border-radius:4px;
          height:50px;
          line-height: 50px;
          padding: 0;
          color: #FFFFFF;
          font-size: 13px;
          margin-top: 50px;
          border-color: #20DEFF;
        }
        .el-form{
          margin-top: 50px;
        }
        .tabTxt1{
          color: #20DEFF;
          font-size: 13px;
          width: 100%;
          .tabTxt1_1{
            float: right;
            cursor: pointer;
          }
        }
        .tabTxt2{
          width: 100%;
          text-align: center;
          font-size: 12px;
          color: #FAFAFA;
          margin-top: 100px;
        }
      }
    }
    .newInput{
      .el-form-item__content{
        height: 50px;
        background-color: #E9D8FC;
        position: relative;
      }
      .el-input{
        width: 100%;
      }
      .getTime{
        position: absolute;
        top: 7px;
        right: 8px;
        height:36px!important;
        line-height: 36px!important;
        padding: 0 16px!important;
        background:rgba(32,222,255,1);
        opacity:1;
        margin-top: 0!important;
        font-size: 13px;
        color: #FFFFFF;
        border-radius:4px;
        cursor: pointer;
      }
    }
  }
</style>
