package com.line.backstage.config;

/**
 * @Author DWanBean
 * @Date 2021/8/6 9:53
 * @Version 1.0
 * @Desc
 */

public class KeyConfig {
    /**
     * 用户订单key+订单id
     */
    public static String ORDER_KEY = "order:ids:";
    /**
     * 用户登录key+用户id  value:token
     */
    public static String USER_LOGIN_KEY = "users:login:";
    /**
     * 管理员登录key+用户id  value:token
     */
    public static String SYS_LOGIN_KEY = "sys:login:";
    /**
     * 用户token过期时间
     */
    public static int USER_LOGIN_TIME =604800;
    /**
     * 管理员token过期时间
     */
    public static int SYS_LOGIN_TIME =604800;
}
