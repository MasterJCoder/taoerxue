package com.taoerxue.util;

import java.util.Random;

/**
 * Created by lizhihui on 2017-03-09 15:04.
 */
public class RandomUtils {


    private RandomUtils() {
    }

    private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //  定义所有的小写字符组成的串（不包括数字）
    private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //  定义所有的数字字符组成的串
    private static final String numberChar = "0123456789";

    /**
     * 产生长度为length的随机字符串（包括字母和数字）
     *
     * @param length 长度
     * @return  返回指定长度的随机字符串,包括数字,大写字母,小写字母
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 产生长度为length的随机字符串（包括字母，不包括数字）
     *
     * @param length
     * @return
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }


    /**
     * 产生长度为length的随机小写字符串（包括字母，不包括数字）
     *
     * @param length
     * @return
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 产生长度为length的随机大写字符串（包括字母，不包括数字）
     *
     * @param length
     * @return
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 产生长度为length的'0'串
     *
     * @param length
     * @return
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    public static String generateNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }


}
