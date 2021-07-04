package com.line.backstage.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.line.backstage.vo.SocketMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * websocket连接后端服务
 * @author scxfsc
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {

    /**
     * 用来存放每个客户端对应的Session对象,键是用户唯一标识sid
     */
    private static Map<String, Session> socketMap = Maps.newHashMap();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        socketMap.put(sid, session);
        log.info("有新客户端加入进来[{}]", sid);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        // 从Map中删除
        socketMap.remove(sid);
        log.info("[{}]客户端socket连接关闭！", sid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("sid") String sid) {
        log.info("收到来自客户端[{}]的信息：{}", sid, message);
        sendToUser(message, sid);
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
    public void sendMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    /**
     * 给指定客户端发送自定义消息
     *
     * @param message 消息内容
     * @param sid     用户唯一标识，小程序中请传openid
     * @return
     */
    public boolean sendToUser(String message, String sid) {
        log.info("推送消息到客户端[{}]，推送内容：{}", sid, message);
        Session session = socketMap.get(sid);
        if (session != null) {
            try {
                sendMessage(message, session);
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
     * @return
     */
    public boolean sendObjectMsgToUser(SocketMsg socketMsg, String sid) {
        String message = null;
        try {
            message = new ObjectMapper().writeValueAsString(socketMsg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sendToUser(message, sid);
    }

}
