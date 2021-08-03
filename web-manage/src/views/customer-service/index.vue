<template>
  <div class="app-container">
    <div class="app-box">
      <!--  1680 -->
      <el-row type="flex" style="height: 835px;">
        <el-row style="width: 280px; border-right: #392525 solid 1px;">
          <el-row type="flex" style="height: 50px;">
            <el-row type="flex" style="width: 50%; height: 100%;">
              <el-col class="d-flex a-center j-center"><img class="kf-img"
                                                            :src="require('@/assets/im_server_avatar.png')"/>
              </el-col>
              <el-col class="d-flex a-center" style="font-size: small; height: 100%;">客服在线</el-col>
            </el-row>
            <el-col class="d-flex a-center" style="width: 50%; height: 100%;"><img class="ry-img"
                                                                                   :src="require('@/assets/im_client_avatar.png')"/>
              <div style="margin-left: 8px;">{{this.userList.length}} 人</div>
            </el-col>
          </el-row>
          <el-col :style="setBackgroundImg">
            <div v-for="(item, index) in userList" :key="index">
              <!-- 用户聊天列表 -->
              <div class="d-flex" :style="nowUserInfoStyle" :ref="`box${item.id}`" @click="findUser(item)">
                <!-- 用户头像简称 -->
                <div class="d-flex flex1 a-center j-center">
                  <div class="d-flex a-center j-center" style="width: 40px; height: 40px; borderRadius: 50%; color:#ffffff;" :style="randomRgb(item)">{{item.userName.substr(0, 1)}}</div>
                </div>
                <!-- 用户名称 -->
                <div class="d-flex flex2 a-center" style="flex-wrap:wrap; padding: 5px 0px;">
                  <div style="font-size: 14px; width: 100%; font-weight: bolder;">{{item.userName}}</div>
                  <div style="font-size: 12px; color: #8d8d8d;">客户在线</div>
                </div>
                <!-- 用户消息数 -->
                <div class="d-flex flex1 a-center" style="flex-wrap:wrap; padding: 5px 0px;">
                  <div style="width: 100%; font-size: 12px; color: #97a8be; margin-top: 5px;">{{item.createDate.substr(11, 12)}}</div>
                  <div class="d-flex" v-if="item.isShow" style="justify-content: flex-end; width: 60%;">
                    <div class="d-flex a-center j-center" style="height: 25px; width: 25px; border-radius: 50%; background-color: #f56c6c; font-size: 12px; color: #FFFFFF;">{{ item.msgSize }}</div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row style="width: 1400px;">
          <el-col style="height: 78%;">
            <div class="d-flex a-center" style="font-size: 22px; height: 51px; border-bottom: #392525 solid 1px; ">
              <div style="margin-left: 10px; ">
                {{ nowUserInfo.userName }}
              </div>
              <div style="margin-left: 10px; ">
                {{ new Date().toLocaleTimeString() }}
              </div>
              <div v-show="wsIsRun" style="margin-left: 10px; color: springgreen;">
                在线
              </div>
              <div v-show="!wsIsRun" style="margin-left: 10px; color: #6e6e6e;">
                离线
              </div>
            </div>
            <!-- 聊天框 -->
            <div ref="chat" style="width:100%; height: 600px; overflow-y: scroll">
              <!-- 聊天记录 -->
<!--              <div v-for="(item, index) in msgInfo" :key="index">-->
              <div v-for="(item, index) in nowUserInfo.userListMsg" :key="index">
                <div class="d-flex a-center" style="margin-top: 20px; flex: 1; justify-content: flex-end;"
                     v-show="item.type === 'sys'">
                  <div class="d-flex"
                       style="justify-content: flex-end; width: 80%; height: auto; margin-right: 20px; color:#5586d3;">
                    {{ item.msg }}
                  </div>
                  <img class="kf-img" style="width: 30px; height: 30px;"
                       :src="require('@/assets/im_server_avatar.png')"/>
                </div>
                <div class="d-flex a-center" style="margin-top: 20px;" v-show="item.type === 'user'">
                  <img class="kf-img" style="width: 30px; height: 30px;"
                       :src="require('@/assets/im_client_avatar.png')"/>
                  <div v-show="item.msgType === 'img'" style="width: 80%; height:auto; margin-left: 20px;">
                    <img :src="item.msg"/>
                  </div>
                  <div v-show="item.msgType === 'text'" style="width: 80%; height:auto; margin-left: 20px;">
                    {{ item.msg }}
                  </div>
                </div>
              </div>
            </div>
          </el-col>
          <el-col class="d-flex" style="height: 22%; border-top: #392525 solid 1px;">
            <div style="width: 95%; height: 100%;">
              <el-input type="textarea" :autosize="{ minRows: 8, maxRows: 8}" v-model="msg" 请输入机构名称="客户就是上帝"></el-input>
            </div>
            <div class="d-flex" style="width: 5%; align-content: flex-end;">
              <el-button style="" @click="sendDataToServer">发送</el-button>
            </div>
          </el-col>
        </el-row>
      </el-row>
    </div>
  </div>
</template>

<script>

import store from "@/store";

export default {
  name: "index",
  data() {
    return {
      // 默认消息列表第一个
      nowId:undefined,
      msg: '',
      // msgInfo: [{
      //   type: '',
      //   msg: '',
      //   createDate: new Date()
      // }],
      setBackgroundImg: {
        // backgroundColor: '#e0b191',
        // backgroundImage: 'url(' + require('@/assets/im_server_avatar.png') + ')',
        height: '94%',
        borderTop: '#392525 solid 1px'
      },
      nowUserInfoStyle:{
        height: '65px',
        borderBottom: '#392525 solid 1px'
      },
      // ws是否启动
      wsIsRun: false,
      // 定义ws对象
      webSocket: null,
      // ws请求链接（类似于ws后台地址）
      ws: '',
      // ws定时器
      wsTimer: null,
      socketMsg: {},
      // 用户会话列表
      userList:[],
      // 当前用户信息
      nowUserInfo:{}
    }
  },
  async mounted() {
    this.wsIsRun = true;
    this.wsInit();
  },
  watch: {},
  methods: {

    randomRgb(e){
      if(e.isSaveColor === undefined){
        // console.log('随机颜色');
        let a = {backgroundColor: '#' + (Math.random() * 0xffffff << 0).toString(16)};
        e.isSaveColor = a;
        return a;
      }else{
        return  e.isSaveColor;
      }
    },
    findUser(e){
      // 判断是否点击当前的用户列表
      if(e.id === this.nowId){
        return;
      }
      let name = 'box' + e.id;
      let dom = this.$refs[name][0];
      let nowUser;
      // 设置选中颜色
      for(let u of this.userList){
        if(u.id === e.id){
          // 更新背景色
          dom.style.backgroundColor = '#0095ff';
          //   给子元素字体颜色
          for (const n of dom.childNodes[2].childNodes) {
            if(n.nodeName === 'DIV'){
              n.style.color = '#ffffff';
            }
          }
          // 判断是否第一次点击
          if(this.nowId !== undefined){
            let oldName = 'box' + this.nowId;
            // console.log(oldName);
            let oldDom = this.$refs[oldName][0];
            oldDom.style.backgroundColor = '';
            //   给old子元素字体颜色
            oldDom.childNodes[2].childNodes[0].style.color = '';
            oldDom.childNodes[2].childNodes[2].style.color = '#8d8d8d';
          }

          // 更新当前id
          this.nowId = e.id;

          // 更新未读消息状态
          u.isShow = false
          // 初始化未读消息数
          u.msgSize = 0
          // user对象赋值
          nowUser = u;

          // 找到旧的对象
          for(let subU of this.userList) {
            if (this.nowUserInfo.id === subU.id) {
              // 设置显示msgSize
              this.nowUserInfo.msgSize = 0;
              subU = this.nowUserInfo;
            }
          }

          // 找到选中的 跳出循环
          break;
        }
      }

      // 将选中的聊天对象赋值
      this.nowUserInfo = nowUser;
    },
    sendDataToServer() {
      if (this.nowUserInfo.id === undefined) {
        this.$message.error("当前没有会话。");
        this.msg = '';
        return;
      }
      if (this.msg === '') {
        return;
      }
      if (this.webSocket.readyState === 1) {
        // 本地渲染数组对象
        this.nowUserInfo.userListMsg.push({'type': 'sys', 'msg': this.msg, 'createDate': new Date()})
        // // 消息接收对象是否存在
        // let sid = this.socketMsg.msdToSid !== undefined ? this.socketMsg.msdToSid : null;
        this.socketMsg.content = this.msg;
        this.webSocket.send(JSON.stringify(this.socketMsg));
        this.msg = '';
        this.$nextTick(() => {
          this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight;
        })
      } else {
        throw Error('服务未连接')
      }
    },

    /**
     * 初始化ws
     */
    wsInit() {
      const wsUrl = 'ws://192.168.1.9:1686/study/websocket/' + store.getters.account + ',' + store.getters.name + '/sys';
      // const wsUrl = 'ws://108.160.143.167:1686/study/websocket/' + store.getters.account + ',' + store.getters.name + '/sys';
      this.ws = wsUrl
      if (!this.wsIsRun) return
      // 销毁ws
      this.wsDestroy()
      // 初始化ws
      this.webSocket = new WebSocket(this.ws)
      // console.log(this.webSocket);
      // ws连接建立时触发
      this.webSocket.addEventListener('open', this.wsOpenHandler)
      // ws服务端给客户端推送消息
      this.webSocket.addEventListener('message', this.wsMessageHandler)
      // ws通信发生错误时触发
      this.webSocket.addEventListener('error', this.wsErrorHandler)
      // ws关闭时触发
      this.webSocket.addEventListener('close', this.wsCloseHandler)

      // 检查ws连接状态,readyState值为0表示尚未连接，1表示建立连接，2正在关闭连接，3已经关闭或无法打开
      clearInterval(this.wsTimer)
      this.wsTimer = setInterval(() => {
        if (this.webSocket.readyState === 1) {
          clearInterval(this.wsTimer)
        } else {
          console.log('ws建立连接失败')
          this.wsInit()
        }
      }, 3000)
    },
    wsOpenHandler(event) {
      console.log('ws建立连接成功' + event);
    },
    // 接收消息
    wsMessageHandler(e) {
      // console.log('接收消息');
      let str = e.data
      let jsonStr = str.replace(" ", "");
      if (typeof jsonStr != 'object') {
        jsonStr = jsonStr.replace(/\ufeff/g, ""); // 重点
        let jj = JSON.parse(jsonStr);
        str = jj;
      }
      this.socketMsg = str;
      console.log(this.socketMsg);
      // 拆分用户名和id
      let info = str.msdToSid.split(",");

      // 更新消息列表
      if (this.userList.length === 0) {
        // 用户列表新增一个用户聊天信息
        this.userList.push({
          id : this.userList.length + 1,
          userId : info[0],
          userName : info[1],
          msgSize : 1,
          userListMsg:[{'type': 'user', "msgType": str.msgType, 'msg': str.content, 'createDate': null}],
          isShow:true,
          createDate:new Date(str.createDate).toLocaleString()
        })
      } else {
        // 先判断是否存在，存在追加一个用户聊天信息
        let isNotSave = true;
        for(let u of this.userList){
          // 判断聊天列表是否存在
          if(u.userId === info[0]){
            // 消息数自增
            u.msgSize = u.msgSize + 1;
            // 新消息追加
            u.userListMsg.push({'type': 'user', "msgType": str.msgType, 'msg': str.content, 'createDate': null});
            // 更新最新消息时间
            u.createDate = new Date(str.createDate).toLocaleString()
            // 显示消息数
            if(this.nowUserInfo.id !== u.id){
              u.isShow = true;
            }
            // 存在
            isNotSave = false;
          }
        }
        // 不存在
        if(isNotSave){
          let size = this.userList.length + 1;
          let u = {
            id : size,
            userId : info[0],
            userName : info[1],
            msgSize : 1,
            userListMsg:[{'type': 'user', "msgType": str.msgType, 'msg': str.content, 'createDate': null}],
            isShow:true,
            createDate:new Date(str.createDate).toLocaleString()
          }
          // 追加
          this.userList.push(u);
        }
      }
      // 滚动条
      this.$nextTick(() => {
        this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight;
      })
    },
    /**
     * ws通信发生错误
     */
    wsErrorHandler(event) {
      console.log(event, '通信发生错误')
      this.wsInit()
    },
    /**
     * ws关闭
     */
    wsCloseHandler(event) {
      console.log(event, 'ws关闭')
      this.wsInit()
    },
    /**
     * 销毁ws
     */
    wsDestroy() {
      if (this.webSocket !== null) {
        this.webSocket.removeEventListener('open', this.wsOpenHandler)
        this.webSocket.removeEventListener('message', this.wsMessageHandler)
        this.webSocket.removeEventListener('error', this.wsErrorHandler)
        this.webSocket.removeEventListener('close', this.wsCloseHandler)
        this.webSocket.close()
        this.webSocket = null
        clearInterval(this.wsTimer)
      }
    }
  }
}
</script>

<style scoped>
.d-flex {
  display: flex;
}

.flex1{
  flex: 1;
}

.flex2{
  flex: 2;
}

/*垂直【找上下】居中*/
.a-center {
  align-items: center;
}

/*水平【找左右】居中*/
.j-center {
  justify-content: center;
}

.kf-img {
  width: 50px;
  height: 50px;
}

.ry-img {
  width: 25px;
  height: 25px;
  margin-left: 20px;
}
</style>
