package com.line.backstage.redis;

import com.fasterxml.jackson.databind.JsonNode;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.SpringUtils;
import com.line.backstage.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.websocket.Session;
import java.io.IOException;

/**
 * redis消息订阅监听者
 */
@Slf4j
@Component
public class SubscribeListener implements MessageListener {

    private RedisUtil redisUtil;

    private String sid;
    private String code;
    private Session session;

    public Session getSession() {
        return session;
    }

    public String getSid() {
        return sid;
    }

    public String getCode() {
        return code;
    }

    public void setSession(Session session, String sid, String code) {
        this.session = session;
        this.sid = sid;
        this.code = code;
    }

    /**
     * 接收发布者的消息
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        if (null == session || !session.isOpen()) {
            return;
        }
        if (null == this.redisUtil) {
            this.redisUtil = SpringUtils.getBean(RedisUtil.class);
        }
        try {
            // 接受到推送时查询用户是否有消息，有的话优先用户消息
            String msg = new String(message.getBody());
            JsonNode msgNode = JsonUtils.toJsonNode(msg.substring(1, msg.length() - 1).replace("\\\"", "'"));
            String temp = null;
            if (msgNode.hasNonNull("timeStamp")) {
                temp = StrUtils.objToStr(redisUtil.get(sid + "_" + code + "_ss_" + msgNode.get("timeStamp").asText("")));
            }
            if (!ObjectUtils.isEmpty(temp)) {
                msgNode = JsonUtils.toJsonNode(temp);
                System.out.println("send" + temp);
            }
            session.getBasicRemote().sendText(msgNode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}