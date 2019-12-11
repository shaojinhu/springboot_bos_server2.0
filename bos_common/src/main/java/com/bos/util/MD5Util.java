package com.bos.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具类
 */
public class MD5Util {

    protected MD5Util(){

    }

    //加密方式
    private static final String ALGORITH_NAME = "md5";

    //加密次数
    private static final int HASH_ITERATIONS = 2;

    /**
     * 加密方法一
     * 参数：1.加密方式，2.密码，3.盐，4.加密次数
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return new SimpleHash(ALGORITH_NAME, password,ByteSource.Util.bytes(password), HASH_ITERATIONS).toHex();
    }

    /**
     *加密方法二
     * 参数与方法一相同
     * @param username
     * @param password
     * @return
     */
    public static String encrypt(String username, String password) {
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username.toLowerCase() + password),
                HASH_ITERATIONS).toHex();
    }

    /**
     * 测试：生成加密后的密码
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(encrypt("123456"));
    }


}
