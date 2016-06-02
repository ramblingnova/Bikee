package com.bigtion.bikee.etc.utils;

/**
 * Created by User on 2016-03-09.
 */
public class CheckUtil {
    // TODO : 유효성 체크
    public static final String REGEX_HANGUL = "^[가-힣]{2,30}$";
    public static final String REGEX_ADDRESS = "[[가-힣]*[0-9]*[가-힣]*[0-9]*\\s?]$";
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_PASSWORD = "^.{8,20}$";
    public static final String REGEX_PHONE = "^01(0|1|[6-9])(\\d{3}|\\d{4})\\d{4}$";
}
