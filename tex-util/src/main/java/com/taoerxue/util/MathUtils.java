package com.taoerxue.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhihui on 2017-03-27 16:01.
 */
public class MathUtils {

    private MathUtils() {
    }


    /**
     * 将一个十进制的数分解成2的幂相加.返回 list
     *
     * @param number 十进制数字
     * @return 列表
     */
    public static List<Integer> convert(Integer number) {
        char[] chars = Integer.toBinaryString(number).toCharArray();
        List<Integer> typeList = new ArrayList<>();
        for (int i = chars.length; i > 0; i--) {
            int o = Integer.parseInt(String.valueOf(chars[i - 1])) * (1 << chars.length - i);
            if (o != 0)
                typeList.add(o);
        }
        return typeList;
    }
}
