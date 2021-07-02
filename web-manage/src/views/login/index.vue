<template>
  <div id="sign">
    <div class="signBox signBox1">
      <div class="signBox1Img">
        <img src="../../img/bg1.png" alt="bg">
      </div>
    </div>
    <div class="signBox signBox2">
      <div class="tabSign">
        <div class="tabTitle">欢迎使用币安合约管理系统!</div>
        <el-form :model="ruleForm" :label-position="labelPosition" :rules="rules" ref="ruleForm">
          <el-form-item label="账户" prop="username">
            <el-input v-model="ruleForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          </el-form-item>
        </el-form>
        <div class="tabTxt1">
          <div class="tabTxt1_1" @click="resetForm">忘记密码？</div>
        </div>
        <div class="tabTxt2">客服TG:juhao技术 QQ:99807999</div>
      </div>
    </div>
  </div>
</template>

<script>
import {getIp} from '@/api/user'
  export default {
    name: 'Login',
    data() {
      var username = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账户'))
        }else if(value.length>10){
          callback(new Error('账户限制10位以下'))
        } else {
          callback()
        }
      }
      var password = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'))
        } else {
          callback()
        }
      }
      return {
        labelPosition: 'top',
        ruleForm: {
          username: '',
          password: ''
        },
        rules: {
          username: [
            { validator: username, trigger: 'blur' }
          ],
          password: [
            { validator: password, trigger: 'blur' }
          ]
        },
        redirect: undefined
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // console.log(this.$store);
            // 写法：this.$store.dispatch(‘action方法名’,值) 含有异步操作，例如向后台提交数据
            // this.$store.dispatch('user/login', this.loginForm)来调取store里的user.js的login方法，从而要更新。
            // 命名空间为true 则'action方法名' = 路径文件名/actions下方法名
            this.$store.dispatch('user/login', this.ruleForm).then(() => {
              this.$router.push({ path: this.redirect || '/' })  //登录进入为上次浏览页面
              this.loading = false
            /*  getIp().then(res=>{
                console.log(res)
                if(res.code==10000){
                  if(res.data!=true){
                    this.$confirm(res.data, '提示', {
                      confirmButtonText: '已知悉',
                      showCancelButton: false,
                      type: 'warning',
                      center: true
                    }).then(() => {
                      this.$router.push({ path: '/' })//每次登录进入都为首页
                    })
                  }else {
                    this.$router.push({ path: '/' })//每次登录进入都为首页
                  }

                }else {
                  this.$message.error(res.message)
                }
              })*/

            }).catch(() => {
              this.loading = false
            })

          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      resetForm() {
        console.log('跳转页面')
        // this.$router.push({name:'newpass'})
        this.$router.push('/newpass')
      }
    },
    created() {
      var _this = this
      document.onkeydown = function(event) {
        var key = window.event.keyCode
        if (key == 13) {
          _this.submitForm(`ruleForm`)
        }
      }
    }
  }
</script>

<style lang="scss">
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  #sign {
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

      .tabSign {
        width: 54%;
        margin: 0 auto;
        margin-top: 220px;
        text-align: left;

        .tabTitle {
          color: #FAFAFA;
          font-size: 20px;
          font-weight: 500;
        }

        .el-form-item__label {
          color: #FFFFFF;
          font-size: 13px !important;
          padding: 0;
        }

        .el-input__inner {
          background-color: #E9D8FC;
          color: #3B3269;
          font-size: 13px;
          height: 50px;
        }

        .el-button {
          width: 100%;
          background: rgba(32, 222, 255, 1);
          opacity: 1;
          border-radius: 4px;
          height: 50px;
          line-height: 50px;
          padding: 0;
          color: #FFFFFF;
          font-size: 13px;
          margin-top: 50px;
          border-color: #20DEFF;
        }

        .el-form {
          margin-top: 50px;
        }

        .tabTxt1 {
          color: #20DEFF;
          font-size: 13px;
          width: 100%;

          .tabTxt1_1 {
            float: right;
            cursor: pointer;
          }
        }

        .tabTxt2 {
          width: 100%;
          text-align: center;
          font-size: 12px;
          color: #FAFAFA;
          margin-top: 100px;
        }
      }
    }
  }
</style>
