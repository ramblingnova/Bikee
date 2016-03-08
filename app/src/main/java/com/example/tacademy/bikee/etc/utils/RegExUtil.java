package com.example.tacademy.bikee.etc.utils;

/**
 * Created by User on 2016-03-09.
 */
public class RegExUtil {
    // TODO : validation check
    public final static String REGEX_HANGUL = "^[가-힣]{2,30}$";
    public final static String REGEX_ADDRESS = "[[가-힣]*[0-9]*[가-힣]*[0-9]*\\s?]$";
}
