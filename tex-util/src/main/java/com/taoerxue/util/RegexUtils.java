package com.taoerxue.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {


    private RegexUtils(){}
    public static Boolean isPhone(String phone) {
        return Pattern.compile("^1[3|5|7|8][0-9]\\d{8}$").matcher(phone).matches();
    }

    public static Boolean checkRegex(CharSequence checkStr, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkStr);
        return matcher.matches();
    }
}
