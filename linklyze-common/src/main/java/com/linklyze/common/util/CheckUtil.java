package com.linklyze.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author novo
 * @since 2023-02-26 12:07
 */
public class CheckUtil {
    /**
     * 邮箱正则
     */
    private static final Pattern MAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 手机号正则
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");

    /**
     * 手机号正则
     */
    private static final Pattern LETTER_DIGIT_PATTERN = Pattern.compile("^[0-9a-zA-z]+$");

    /**
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        Matcher m = MAIL_PATTERN.matcher(email);
        return m.matches();
    }

    /**
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (null == phone || "".equals(phone)) {
            return false;
        }
        Matcher m = PHONE_PATTERN.matcher(phone);
        boolean result = m.matches();
        return result;

    }

    /**
     * 用来判断传输的短链是否合法
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return LETTER_DIGIT_PATTERN.matcher(str).matches();
    }
}
