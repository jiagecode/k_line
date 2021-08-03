package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author jack
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketMsg {

    /**
     * 发给什么类型用户
     */
    private String msdToUserType;

    /**
     * 用户id
     */
    private String msdToSid;

    /**
     * 用户名称
     */
    private String sidName;

    /**
     * msgType
     */
    private String msgType;

    /**
     * 类容
     */
    private String content;

    /**
     * 类容
     */
    private Date createDate;
}
