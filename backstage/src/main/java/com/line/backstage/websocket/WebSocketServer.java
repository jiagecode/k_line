package com.line.backstage.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.line.backstage.entity.SendMassage;
import com.line.backstage.service.SendMassageService;
import com.line.backstage.vo.SocketMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * websocket连接后端服务
 *
 * @author jack
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}/{userType}")
public class WebSocketServer {

    /**
     * 用来存放每个客户端对应的Session对象,键是用户唯一标识sid
     */
    private static Map<String, Session> userSocketMap = Maps.newHashMap();

    private static Map<String, Session> sysSocketMap = Maps.newHashMap();

    /**
     * 用户类型-区分消息
     */
    private static final String USER_TYPE = "sys";

    /**
     * 用户类型-区分消息
     */
    private static final String SERVICE_LIST = "serviceList";

    /**
     * 这里使用静态，让 service 属于类
     */
    private static SendMassageService sendMassageService;

    /**
     * 注入的时候，给类的 service 注入
     * @param sendMassageService
     */
    @Autowired
    public void setDeviceListenerService(SendMassageService sendMassageService) {
        WebSocketServer.sendMassageService = sendMassageService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid, @PathParam("userType") String userType) {
        if (Objects.equals(USER_TYPE, userType)) {
            sysSocketMap.put(sid, session);
        } else {
            userSocketMap.put(sid, session);
            sendMessageInfo(Integer.valueOf(sid.split(",")[0]), sysSocketMap, session);
        }
        log.info("有[{}]新客户端加入进来[{}]，sysMap大小为【{}】，userMap大小为【{}】", userType, sid, sysSocketMap.size(), userSocketMap.size());
    }

    /**
     * 给用户推荐当前在线客服列表
     * @param userSocketMap
     * @param session
     */
    private void sendMessageInfo(Integer sendId, Map<String, Session> userSocketMap, Session session) {
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.setMsdToSid(null);
        socketMsg.setMsdToUserType(SERVICE_LIST);
        socketMsg.setCreateDate(new Date());
        try {
            String str = "";
            for (String s : userSocketMap.keySet()) {
                str += s + "|";
            }
            socketMsg.setContent(str);
            // 0 默认第一次连接
            sendMessage(sendId, "0", socketMsg, session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid, @PathParam("userType") String userType) {
        // 从Map中删除
        if (Objects.equals(USER_TYPE, userType) && sysSocketMap.size() > 0) {
            sysSocketMap.remove(sid);
        } else if (userSocketMap.size() > 0) {
            userSocketMap.remove(sid);
        }
        log.info("[{}]客户端[{}]关闭socket连接！", userType, sid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("sid") String sid, @PathParam("userType") String userType) {
        log.info("收到来自[{}]客户端[{}]的信息：【{}】", userType, sid, message);
        // 字符串转json 确定是否存在客服id
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SocketMsg socketMsg = objectMapper.readValue(message, SocketMsg.class);

            // 判断是否是客户端消息，是否存在sid值
            if (socketMsg.getMsdToSid() == null && Objects.equals(USER_TYPE, socketMsg.getMsdToUserType())) {
                // 判断是否有客服存在
                if (sysSocketMap.size() > 0) {
                    String[] keys = sysSocketMap.keySet().toArray(new String[0]);
                    Random random = new Random();
                    String randomKey = keys[random.nextInt(keys.length)];
                    // 随机取出客服id沟通
                    socketMsg.setMsdToSid(randomKey);
                } else {
                    // 没有客服存在则，发送特定的消息
                    try {

                        socketMsg.setContent("当前在线客服繁忙，请稍后再试~");
                        socketMsg.setMsdToSid(null);
                        socketMsg.setMsdToUserType(USER_TYPE);
                        socketMsg.setCreateDate(new Date());
                        sendMessage(Integer.valueOf(sid), socketMsg.getMsdToSid().split(",")[0], socketMsg, session);
                        return;
                    } catch (IOException e) {
                        log.error("话术【当前在线客服繁忙，请稍后再试】异常，【{}】", e.getMessage());
                        return;
                    }
                }
            } else {
                // 是否管理端获取用户在线人数
                if(Objects.equals(socketMsg.getMsgType(), "getOnlineUsers")){
                    try {
                        List<String> list = Lists.newArrayList();
                        for (String s : userSocketMap.keySet()) {
                            list.add(s);
                        }
                        session.getBasicRemote()
                                .sendText("{\"msgType\":\"getOnlineUsers\", \"content\":\"" + list + "\"}");
                    } catch (IOException e) {
                        log.error("发送在线人数异常【{}】", e.getMessage());
                    }
                } else {
                    sendToUser(sid, socketMsg.getMsdToSid().split(",")[0], userType, socketMsg);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("websocket消息对象转换异常【{}】", e.getMessage());
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Integer sendId, String receiveId, SocketMsg message, Session session) throws IOException {

        ObjectMapper objectMapper;
        try {
            objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(message);
            log.info(content);
            // 新增插入聊天记录
            if(!Objects.equals(message.getMsdToUserType(), "serviceList")){
                SendMassage sendMassage = new SendMassage();
                sendMassage.setSend(sendId);
                sendMassage.setReceive(Integer.valueOf(receiveId));
                // 发送用户角色类型 1-管理员/ 0-用户
                sendMassage.setEditUserId(Objects.equals(message.getMsdToUserType(), "user") ? 0 : 1);
                sendMassage.setAddTime(new Date());
                sendMassage.setContent(content);
                // 插入
                sendMassageService.save(sendId, sendMassage);
            }
            session.getBasicRemote().sendText(content);
        } catch (Exception e) {
            log.error("sendMessage中对象转【{}】", e.getMessage());
        }
    }

    /**
     * 给指定客户端发送自定义消息
     *
     * @param socketMsg 消息内容上下文
     * @return
     */
    public boolean sendToUser(String sid, String receiveId, String userType, SocketMsg socketMsg) {
        log.info("用户类型[{}]推送消息[{}]到客户端[{}]", socketMsg.getMsdToUserType(), socketMsg.getContent(), socketMsg.getMsdToSid());
        Session session = null;
        if (Objects.equals(USER_TYPE, socketMsg.getMsdToUserType())) {
            session = sysSocketMap.get(socketMsg.getMsdToSid());
        } else {
            session = userSocketMap.get(socketMsg.getMsdToSid());
        }
        if (session != null) {
            try {
                // 转换用户类型和id
                socketMsg.setMsdToSid(sid);
                socketMsg.setMsdToUserType(userType);
                socketMsg.setCreateDate(new Date());

                sendMessage(Integer.valueOf(sid.split(",")[0]), receiveId, socketMsg, session);
                return true;
            } catch (IOException e) {
                log.error("给客户端[{sid}]发送消息失败", e);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 发送自定义消息给指定用户
     *
     * @param socketMsg
     * @param sid
     * @return public boolean sendObjectMsgToUser(SocketMsg socketMsg, String sid) {
    String message = null;
    try {
    message = new ObjectMapper().writeValueAsString(socketMsg);
    } catch (JsonProcessingException e) {
    e.printStackTrace();
    }
    return sendToUser(message, sid);
    }
     */

}
