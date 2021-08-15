<template>
  <div class="app-container">
    <div class="app-box">
      <!--  1680 -->
      <el-row type="flex" style="height: 835px;">
        <el-row style="width: 280px; border-right: #392525 solid 1px;">
          <el-row type="flex" style="height: 50px;">
            <el-row type="flex" style="width: 50%; height: 100%;">
              <el-col class="d-flex a-center j-center"><img
                class="kf-img"
                :src="require('@/assets/im_server_avatar.png')"
              >
              </el-col>
              <el-col class="d-flex a-center" style="font-size: small; height: 100%;">客服在线</el-col>
            </el-row>
            <el-col class="d-flex a-center" style="width: 50%; height: 100%;"><img
                                                                                class="ry-img"
                                                                                :src="require('@/assets/im_client_avatar.png')"
                                                                              >
              <div style="margin-left: 8px;">{{ this.onlineUsers }} 人</div>
            </el-col>
          </el-row>
          <el-col :style="setBackgroundImg">
            <div v-for="(item, index) in userList" :key="index">
              <!-- 用户聊天列表 -->
              <div :ref="`box${item.id}`" class="d-flex" :style="nowUserInfoStyle" @click="findUser(item)">
                <!-- 用户头像简称 -->
                <div class="d-flex flex1 a-center j-center">
                  <div class="d-flex a-center j-center" style="width: 40px; height: 40px; borderRadius: 50%; color:#ffffff;" :style="randomRgb(item)">{{ item.userName.substr(0, 1) }}</div>
                </div>
                <!-- 用户名称 -->
                <div class="d-flex flex2 a-center" style="flex-wrap:wrap; padding: 5px 0px;">
                  <div style="font-size: 14px; width: 100%; font-weight: bolder;">{{ item.userName }}</div>
                  <div style="font-size: 12px; color: #8d8d8d;">{{ item.isOnline ? '在线' : '离线' }}</div>
                </div>
                <!-- 用户消息数 -->
                <div class="d-flex flex1 a-center" style="flex-wrap:wrap; padding: 5px 0px;">
                  <div style="width: 100%; font-size: 12px; color: #97a8be; margin-top: 5px;">{{ item.createDate.substr(11, 12) }}</div>
                  <div v-if="item.isShow" class="d-flex" style="justify-content: flex-end; width: 60%;">
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
              <div v-for="(item, index) in nowUserInfo.userListMsg" :key="index">
                <div v-show="item.type === 'sys'" class="d-flex a-center" style="margin-top: 20px; flex: 1; justify-content: flex-end;">
                  <div v-show="item.msgType === 'text'" class="d-flex" style="justify-content: flex-end; width: 80%; height: auto; margin-right: 20px; color:#5586d3;">
                    {{ item.msg }}
                  </div>
                  <div v-show="item.msgType === 'img'" class="d-flex" style="justify-content: flex-end; width: 30%; height: auto; margin-right: 20px; color:#5586d3;">
                    <el-image :src="item.msg" :preview-src-list="[item.msg]" />
                  </div>
                  <img class="kf-img" style="width: 30px; height: 30px;" :src="require('@/assets/im_server_avatar.png')">
                </div>
                <div v-show="item.type === 'user'" class="d-flex a-center" style="margin-top: 20px;">
                  <img class="kf-img" style="width: 30px; height: 30px;" :src="require('@/assets/im_client_avatar.png')">
                  <div v-show="item.msgType === 'img'" style="width: 30%; height:auto; margin-left: 20px;">
                    <el-image :src="item.msg" :preview-src-list="[item.msg]" />
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
              <el-input v-model="msg" type="textarea" :autosize="{ minRows: 8, maxRows: 8}" 请输入机构名称="客户就是上帝" />
            </div>
            <div class="d-flex" style="align-content: flex-end; width: 5%;">
              <el-button v-if="msg !== ''" size="mini" style="background-color: #409EFF;" @click="sendDataToServer('text')">发送</el-button>
              <div v-if="msg === ''" style="width:100%;">
                <div class="d-flex a-center j-center" style="height: 50%;">
                  <div>
                    <el-tooltip class="item" effect="dark" content="常用语" placement="top-start">
                      <el-button type="info" style="background-color: #409EFF;" icon="el-icon-message" circle @click="centerDialogVisible = true" />
                    </el-tooltip>
                  </div>
                </div>
                <div class="d-flex a-center j-center" style="height: 50%;">
                  <el-upload ref="upload" :action="uploadUrl" :before-upload="checkImgFile" :limit="1" :show-file-list="false" :headers="setHeaders" :on-success="handleAvatarSuccess">
                    <el-tooltip class="item" effect="dark" content="发送图片" placement="top-start">
                      <el-button type="info" icon="el-icon-folder-add" circle style="background-color: #409EFF;" />
                    </el-tooltip>
                  </el-upload>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-row>

      <!--常用语弹窗-->
      <el-dialog title="常用语列表" :visible.sync="centerDialogVisible" center>
        <!--        <span>亲，请问有什么能够帮助您的吗？</span>-->
        <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName" @current-change="handleCurrentMsgChange">
          <el-table-column prop="address" label="内容" :min-width="120" />
          <el-table-column prop="date" label="时间" :min-width="40" />
          <el-table-column prop="name" label="姓名" :min-width="40" />
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--        <span slot="footer" class="dialog-footer">-->
        <!--          <el-button @click="centerDialogVisible = false">取 消</el-button>-->
        <!--          <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>-->
        <!--        </span>-->
      </el-dialog>
    </div>
  </div>
</template>

<script>

import store from '@/store'
import { getMessageList } from '@/api/user'

export default {
  name: 'Index',
  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '亲，请问有什么能够帮助您的吗？'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '亲，请问有什么能够帮助您的吗？'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '亲，请问有什么能够帮助您的吗？'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '亲，请问有什么能够帮助您的吗？'
      }],
      centerDialogVisible: false,
      imgUrl: '',
      // 默认消息列表第一个
      nowId: undefined,
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
      nowUserInfoStyle: {
        height: '65px',
        borderBottom: '#392525 solid 1px'
      },
      setHeaders: {
        'Authorization': store.getters.token
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
      userList: [],
      // 当前用户信息
      nowUserInfo: {},
      // 在线人数
      onlineUsers: 0,
      // 在线人数
      onlineUsersList: undefined,
      uploadUrl: 'http://localhost:1686/study/upload'
    }
  },
  watch: {},
  created() {
  },
  async mounted() {
    this.wsIsRun = true
    this.wsInit()
    this.getMessageInfoList()
  },
  methods: {
    // 获取在线人数
    getOnlineUsers() {
      if (this.onlineUsers === 0) {
        this.webSocket.send(JSON.stringify({ 'msgType': 'getOnlineUsers', 'content': 'getOnlineUsers' }))
      }
    },
    // 根据消息生产聊天列表
    createMsgList(obj) {
      this.socketMsg = obj

      // 拆分用户名和id
      const info = obj.msdToSid.split(',')

      // 更新消息列表
      if (this.userList.length === 0) {
        // 用户列表新增一个用户聊天信息
        this.userList.push({
          id: this.userList.length + 1,
          userId: parseInt(info[0]),
          userName: info[1],
          msgSize: 1,
          userListMsg: [{ 'type': 'user', 'msgType': obj.msgType, 'msg': obj.content, 'createDate': null }],
          isShow: true,
          createDate: new Date(obj.createDate).toLocaleString()
        })
      } else {
        // 先判断是否存在，存在追加一个用户聊天信息
        let isNotSave = true
        for (const u of this.userList) {
          // 判断聊天列表是否存在
          if (u.userId === parseInt(info[0])) {
            // 消息数自增
            u.msgSize = u.msgSize + 1
            // 新消息追加
            u.userListMsg.push({ 'type': 'user', 'msgType': obj.msgType, 'msg': obj.content, 'createDate': null })
            // 更新最新消息时间
            u.createDate = new Date(obj.createDate).toLocaleString()
            // 显示消息数
            if (this.nowUserInfo.id !== u.id) {
              u.isShow = true
            }
            // 存在
            isNotSave = false
          }
        }
        // 不存在
        if (isNotSave) {
          const size = this.userList.length + 1
          const u = {
            id: size,
            userId: parseInt(info[0]),
            userName: info[1],
            msgSize: 1,
            userListMsg: [{ 'type': 'user', 'msgType': obj.msgType, 'msg': obj.content, 'createDate': null }],
            isShow: true,
            createDate: new Date(obj.createDate).toLocaleString()
          }
          // 追加
          this.userList.push(u)
        }
      }
    },
    createUserListInfo(obj, userType, sendId) {
      if (this.onlineUsersList === undefined) {
        this.onlineUsersList = ''
      }
      // 拆分用户名和id
      this.userList.push({
        id: this.userList.length + 1,
        userId: parseInt(sendId),
        userName: obj.msdToSid.split(',')[1],
        msgSize: 1,
        userListMsg: [{ 'type': userType, 'msgType': obj.msgType, 'msg': obj.content, 'createDate': null }],
        createDate: new Date(obj.createDate).toLocaleString(),
        // isOnline: this.onlineUsersList.includes(obj.msdToSid)
        isOnline: this.onlineUsersList.includes(obj.msdToSid)
      })
    },
    createSaveMsgList(sendId, userType, obj) {
      if (this.userList.length === 0) {
        this.createUserListInfo(obj, userType, sendId)
      } else {
        let isNotSave = true
        for (const u of this.userList) {
          if (parseInt(u.userId) === sendId) {
            u.userListMsg.push({ 'type': userType, 'msgType': obj.msgType, 'msg': obj.content, 'createDate': null })
            u.createDate = new Date(obj.createDate).toLocaleString()
            u.isShow = false
            // 存在
            isNotSave = false
          }
        }
        // 不存在
        if (isNotSave) {
          this.createUserListInfo(obj, userType, sendId)
        }
      }
    },
    // 字符串转对象
    findObject(str) {
      let jsonStr = str.replace(' ', '')
      jsonStr = jsonStr.replace(/\ufeff/g, '') // 重点
      return JSON.parse(jsonStr)
    },
    getMessageInfoList() {
      getMessageList({}).then(response => {
        for (const d of response.data.list.reverse()) {
          // 判断接收方和发送方
          if (d.send === store.getters.account) {
            this.createSaveMsgList(d.receive, 'sys', this.findObject(d.content))
          } else {
            this.createSaveMsgList(d.send, 'user', this.findObject(d.content))
          }
        }
      }).catch(error => {
        console.log(error)
      })
    },
    handleCurrentMsgChange(val) {
      console.log(val)
      this.msg = val.address
      this.centerDialogVisible = false
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        console.log(row)
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    handleEdit(index, row) {
      console.log(index, row)
      this.$message.info('升级中，敬请期待')
    },
    handleDelete(index, row) {
      console.log(index, row)
      this.$message.info('升级中，敬请期待')
    },
    handleAvatarSuccess(res, file) {
      this.msg = URL.createObjectURL(file.raw)
      this.imgUrl = res.data
      this.sendDataToServer('img')
      // 上传成功之后清除历史记录
      this.$refs.upload.clearFiles()
    },

    randomRgb(e) {
      if (e.isSaveColor === undefined) {
        // console.log('随机颜色');
        const a = { backgroundColor: '#' + (Math.random() * 0xffffff << 0).toString(16) }
        e.isSaveColor = a
        return a
      } else {
        return e.isSaveColor
      }
    },
    findUser(e) {
      // 判断是否点击当前的用户列表
      if (e.id === this.nowId) {
        return
      }
      const name = 'box' + e.id
      const dom = this.$refs[name][0]
      let nowUser
      // 设置选中颜色
      for (const u of this.userList) {
        if (u.id === e.id) {
          // 更新背景色
          dom.style.backgroundColor = '#0095ff'
          //   给子元素字体颜色
          for (const n of dom.childNodes[2].childNodes) {
            if (n.nodeName === 'DIV') {
              n.style.color = '#ffffff'
            }
          }
          // 判断是否第一次点击
          if (this.nowId !== undefined) {
            const oldName = 'box' + this.nowId
            // console.log(oldName);
            const oldDom = this.$refs[oldName][0]
            oldDom.style.backgroundColor = ''
            //   给old子元素字体颜色
            oldDom.childNodes[2].childNodes[0].style.color = ''
            oldDom.childNodes[2].childNodes[2].style.color = '#8d8d8d'
          }

          // 更新当前id
          this.nowId = e.id

          // 更新未读消息状态
          u.isShow = false
          // 初始化未读消息数
          u.msgSize = 0
          // user对象赋值
          nowUser = u

          // 找到旧的对象
          for (let subU of this.userList) {
            if (this.nowUserInfo.id === subU.id) {
              // 设置显示msgSize
              this.nowUserInfo.msgSize = 0
              subU = this.nowUserInfo
            }
          }

          // 找到选中的 跳出循环
          break
        }
      }

      // 将选中的聊天对象赋值
      this.nowUserInfo = nowUser

      // 对话框滚动条
      this.$nextTick(() => {
        this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight
      })
    },
    checkImgFile(file) {
      if (this.nowUserInfo.id === undefined) {
        this.$message.error('当前没有会话。')
        this.msg = ''
        return false
      }
      console.log(file.type)
      const isJPG = file.type === 'image/jpeg' ? true : file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 4

      if (!isJPG) {
        this.$message.error('上传图片只能是image/jpeg/jpg/png格式!')
      }

      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    sendDataToServer(e) {
      if (this.nowUserInfo.id === undefined) {
        this.$message.error('当前没有会话。')
        this.msg = ''
        return
      }
      if (this.msg === '') {
        return
      }
      if (this.webSocket.readyState === 1) {
        // 本地渲染数组对象
        this.nowUserInfo.userListMsg.push({ 'type': 'sys', 'msgType': e, 'msg': this.msg, 'createDate': new Date() })
        // // 消息接收对象是否存在
        this.socketMsg.content = e === 'img' ? this.imgUrl : this.msg
        this.socketMsg.msgType = e === 'img' ? 'img' : 'text'
        this.webSocket.send(JSON.stringify(this.socketMsg))
        this.msg = ''
        this.$nextTick(() => {
          this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight
        })
      } else {
        throw Error('服务未连接')
      }
    },

    /**
     * 初始化ws
     */
    wsInit() {
      // 108.160.143.167
      const wsUrl = 'ws://localhost:1686/study/websocket/' + store.getters.account + ',' + store.getters.name + '/sys'
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
      console.log('ws建立连接成功' + event)
      this.getOnlineUsers()
    },
    // 接收消息
    wsMessageHandler(e) {
      // console.log('接收消息');
      console.log('接收消息')
      console.log(e.data)
      // let str = e.data
      // let jsonStr = str.replace(' ', '')
      // if (typeof jsonStr !== 'object') {
      //   jsonStr = jsonStr.replace(/\ufeff/g, '') // 重点
      //   const jj = JSON.parse(jsonStr)
      //   str = jj
      // }
      const obj = this.findObject(e.data)
      if (obj.msgType === 'getOnlineUsers') {
        // 在线人数处理
        this.onlineUsers = parseInt(obj.content === '{}' ? 0 : obj.content.split(',').length / 2)
        // 在线人数列表
        this.onlineUsersList = obj.content
      } else {
        this.createMsgList(obj)
        // 滚动条
        this.$nextTick(() => {
          this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight
        })
      }
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

.flex3{
  flex: 3;
}

/*垂直【找上下】居中*/
.a-center {
  align-items: center;
}

/*水平【找左右】居中*/
.j-center {
  justify-content: center;
}

/*块模块改成行模块*/
.f-wrap {
  flex-wrap: wrap;
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

.el-table .warning-row {
   background: oldlace;
 }

.el-table .success-row {
  background: #f0f9eb;
}
</style>
