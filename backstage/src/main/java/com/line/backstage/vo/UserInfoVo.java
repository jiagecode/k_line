package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.line.backstage.entity.SysRoleInfo;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVo {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户角色
     */
    private SysRoleInfo Role;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户真实姓名
     */
    private String userRealName;

    /**
     * 性别 1-男 0-女 -1未知
     */
    private Integer userGender;

    /**
     * 登录密码
     */
    private String userPassword;

    /**
     * 头像
     */
    private String userHeadImg;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 推荐人ID
     */
    private Integer userRefereeId;

    /**
     * 用户类别 1-普通用户 2-代理用户
     */
    private Integer userType;

    /**
     * 用户等级 1-普通级
     */
    private Integer userLevel;

    /**
     * 用户身份证号
     */
    private String userCardNo;

    /**
     * 用户注册时间
     */
    private Date userRegisterDate;

    /**
     * 是否禁用 0-未禁用 1-已禁用
     */
    private Integer userForbidFlag;

    /**
     * 推荐码
     */
    private String userRefereeCode;

    /**
     * 是否有推荐资格 1-有 0-无
     */
    private Integer userRefereeAble;

    /**
     * 创建时间
     */
    private Date addDate;

    /**
     * 创建人
     */
    private Integer addUserId;

    /**
     * 更新时间
     */
    private Date editDate;

    /**
     * 修改人
     */
    private Integer editUserId;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 注销状态 0-未注销 1-主动注销 2-强制注销
     */
    private Integer delStatus;

    /**
     * 数据状态
     */
    private Integer del;
    /**
     * 是否为DIY用户 默认0 不是
     */
    private Integer diyFlag = 0;
    /**
     * DIY用户类型 默认未1 普通用户
     */
    private Integer diyUserType = 1;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 所属代理id
     */
    private Integer agentId;
    /**
     * 所属代理名称
     */
    private String agentName;
    /**
     * 所属代理名称
     */
    private Date agentTime;
    /**
     * 所属代理名称
     */
    private Double bonusRate;
    /**
     * 所属代理名称
     */
    private Double commissionRate;
    @Transient
    private Double userMoney;
    private String diyUserName;
}