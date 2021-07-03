package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息(UserInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 18:51:52
 */
@Data
@Table(name = "t_user_info")
public class UserInfo extends QueryRequest implements Serializable {

    private static final long serialVersionUID = -53341652575253406L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 用户角色
     */
    @ApiModelProperty("用户角色")
    private SysRoleInfo Role;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String userNickName;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    private String userRealName;

    /**
     * 性别 1-男 0-女 -1未知
     */
    @ApiModelProperty("性别 1-男 0-女 -1未知")
    private Integer userGender;

    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")
    private String userPassword;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String userHeadImg;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String userPhone;

    /**
     * 推荐人ID
     */
    @ApiModelProperty("推荐人ID")
    private Integer userRefereeId;

    /**
     * 用户类别 1-普通用户 2-代理用户
     */
    @ApiModelProperty("用户类别 1-普通用户 2-代理用户")
    private Integer userType;

    /**
     * 用户等级 1-普通级
     */
    @ApiModelProperty("用户等级 1-普通级")
    private Integer userLevel;

    /**
     * 用户身份证号
     */
    @ApiModelProperty("用户身份证号")
    private String userCardNo;

    /**
     * 用户注册时间
     */
    @ApiModelProperty("用户注册时间")
    private Date userRegisterDate;

    /**
     * 是否禁用 0-未禁用 1-已禁用
     */
    @ApiModelProperty("是否禁用 0-未禁用 1-已禁用")
    private Integer userForbidFlag;

    /**
     * 推荐码
     */
    @ApiModelProperty("推荐码")
    private String userRefereeCode;

    /**
     * 是否有推荐资格 1-有 0-无
     */
    @ApiModelProperty("是否有推荐资格 1-有 0-无")
    private Integer userRefereeAble;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date addDate;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer addUserId;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date editDate;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer editUserId;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private Date lastLoginDate;

    /**
     * 注销状态 0-未注销 1-主动注销 2-强制注销
     */
    @ApiModelProperty("注销状态 0-未注销 1-主动注销 2-强制注销")
    private Integer delStatus;

    /**
     * 数据状态
     */
    @ApiModelProperty("数据状态")
    private Integer del;
    @Transient
    private Double userMoney;
}