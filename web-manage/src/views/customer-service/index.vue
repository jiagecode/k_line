<template>
  <!--  1680 -->
  <el-row type="flex" style="height: 835px;">
    <el-row style="width: 280px; border-right: #392525 solid 1px;">
      <el-row type="flex" style="height: 50px;">
        <el-row type="flex" style="width: 50%; height: 100%;">
          <el-col class="d-flex a-center j-center"><img class="kf-img" :src="require('@/assets/im_server_avatar.png')"/>
          </el-col>
          <el-col class="d-flex a-center" style="font-size: small; height: 100%;">客服在线</el-col>
        </el-row>
        <el-col class="d-flex a-center" style="width: 50%; height: 100%;"><img class="ry-img" :src="require('@/assets/im_client_avatar.png')"/>
          <div style="margin-left: 8px;">0 人</div>
        </el-col>
      </el-row>
      <el-col :style="setBackgroundImg"></el-col>
    </el-row>
    <el-row style="width: 1400px;">
      <el-col style="height: 78%;">
        <div class="d-flex a-center" style="font-size: 22px; height: 51px; border-bottom: #392525 solid 1px; ">
          <div style="margin-left: 10px; ">
            浮生若梦
          </div>
          <div style="margin-left: 10px; ">
            2020-02-02 02:02:02
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
          <div v-for="(item, index) in msgInfo" :key="index" >
              <div class="d-flex a-center" style="margin-top: 20px; flex: 1; justify-content: flex-end;" v-show="item.type === 'sys'">
                <div class="d-flex" style="justify-content: flex-end; width: 80%; height: auto; margin-right: 20px; color:#5586d3;">
                  {{item.msg}}
                </div>
                <img class="kf-img" style="width: 30px; height: 30px;" :src="require('@/assets/im_server_avatar.png')"/>
              </div>
            <div class="d-flex a-center" style="margin-top: 20px;" v-show="item.type === 'user'">
              <img class="kf-img" style="width: 30px; height: 30px;" :src="require('@/assets/im_client_avatar.png')"/>
              <div style="width: 80%; height:auto; margin-left: 20px;">
                {{item.msg}}
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col class="d-flex" style="height: 22%; border-top: #392525 solid 1px;">
        <div  style="width: 95%; height: 100%;">
          <el-input type="textarea" :autosize="{ minRows: 8, maxRows: 8}" v-model="msg" 请输入机构名称="客户就是上帝"></el-input>
        </div>
        <div class="d-flex" style="width: 5%; align-content: flex-end;">
          <el-button style="" @click="sendDataToServer" >发送</el-button>
        </div>
      </el-col>
    </el-row>
  </el-row>
</template>

<script>

import store from "@/store";

export default {
  name: "index",
  data() {
    return {
      msg:'',
      msgInfo:[{
        type:'',
        msg:'',
        createDate: new Date()
      }],
      setBackgroundImg: {
        // backgroundColor: '#e0b191',
        // backgroundImage: 'url(' + require('@/assets/im_server_avatar.png') + ')',
        height: '94%',
        borderTop: '#392525 solid 1px'
      },
      // ws是否启动
      wsIsRun: false,
      // 定义ws对象
      webSocket: null,
      // ws请求链接（类似于ws后台地址）
      ws: '',
      // ws定时器
      wsTimer: null,
      socketMsg:{}
    }
  },
    async mounted() {
      this.wsIsRun = true;
      this.wsInit();
    },
  watch: {
  },
  methods: {

    sendDataToServer() {
      if (this.socketMsg.msdToSid === undefined) {
        this.$message.error("当前没有会话。");
        this.msg = '';
        return;
      }
      if (this.msg === '') {
        return;
      }
      if (this.webSocket.readyState === 1) {
        this.msgInfo.push({'type': 'sys', 'msg': this.msg, 'createDate': new Date()})
        let sid = this.socketMsg.msdToSid !== undefined ? this.socketMsg.msdToSid : null;
        console.log(this.socketMsg);
        this.socketMsg.content = this.msg;
        console.log(this.socketMsg);
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
      // const wsuri = 'ws://10.229.36.158:7777/websocket/badao'
      console.log(store.getters.account);
      const wsUrl = 'ws://192.168.1.9:1686/study/websocket/' + store.getters.account + '/sys';
      this.ws = wsUrl
      if (!this.wsIsRun) return
      // 销毁ws
      this.wsDestroy()
      // 初始化ws
      this.webSocket = new WebSocket(this.ws)
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
      console.log('ws建立连接成功')
    },
    // 发送消息
    wsMessageHandler(e) {
      // console.log('wsMessageHandler')
      // console.log(e)
      let str = e.data
      let jsonStr = str.replace(" ", "");
      if (typeof jsonStr != 'object') {
        jsonStr = jsonStr.replace(/\ufeff/g, "");//重点
        let jj = JSON.parse(jsonStr);
        str = jj;
      }
      this.socketMsg = str;
      console.log(str);
      this.msgInfo.push({'type': 'user', 'msg': str.content, 'createDate': null})
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
