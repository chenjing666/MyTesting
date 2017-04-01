package com.biaoke.bklive.utils;

import java.util.Random;

/**
 * Created by hasee on 2017/3/30
 *
 * String操作类
 */

public class StringUtils {

    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 判断是不是空白
     *
     * @param str
     * @return
     */
    private static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断是不是为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) && isBlank(str);
    }

    /**
     * 获取String长度
     *
     * @param str
     * @return
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 随机一个自定长度的字符串
     *
     * @param length 随机字符串长度，小于等于11则生成的全为数字
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(length)));
        }
        return sb.toString();
    }
    /**
     * 判断是否相等
     *
     * @param str
     * @param end
     * @return
     */
    public static boolean isEqual(String str, String end) {
        if (isBlank(str) || isBlank(end)) {
            return false;
        }
        if (str.equals(end)) {
            return true;
        }
        return false;
    }
}
