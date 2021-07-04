package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author scxfsc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketMsg {
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息内容
     */
    private Object content;

    public static SocketMsg createWxMsg(String msg) {
        SocketMsg socketMsg = new SocketMsg();
        socketMsg.setMsgType("1");
        socketMsg.setContent(msg);
        return socketMsg;
    }

}
