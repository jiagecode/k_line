package com.line.backstage.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro
 * MD5盐值加密
 * @author jack
 */
public class PasswordHelper {

    private static final String algorithmName = "MD5";
    private static final int hashIterations = 1024;

    /**
     * 密码
     * 加密
     *
     * @param account
     * @param password
     * @return 返回加密后的密码
     */
    public static String encryptPassword(String account, String password) {

        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(account),
                hashIterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        System.out.println(PasswordHelper.encryptPassword("10000", "123456"));
    }
}
