package com.taoerxue.util;

public class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean lengthRangeIn(String str, int min, int max) {
        int length = str.length();
        return length <= max && length >= min;
    }
}
