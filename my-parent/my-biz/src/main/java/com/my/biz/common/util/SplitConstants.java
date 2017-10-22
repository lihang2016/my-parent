package com.my.biz.common.util;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 13:30 2017/7/4
 */
public class SplitConstants {
    public static final String SEPARATOR_CHAR_COMMA = ",";
    public static final String SEPARATOR_CHAR_SLASH = "/";
    public static final String SEPARATOR_CHAR_HYPHEN = "-";
    public static final String SEPARATOR_CHAR_PERIOD = ".";
    public static final String SEPARATOR_CHAR_UNDERLINE = "_";
    public static final String SEPARATOR_CHAR_ASTERISK = "*";
    public static final String SEPARATOR_CHAR_COLON = ":";
    public static final String SEPARATOR_NEWLINE_WIN = "\r\n";
    public static final String SEPARATOR_NEWLINE_LINUX = "\n";

    public SplitConstants() {
    }

    public static String getNewLineSymbol() {
        return System.getProperty("line.separator", "\n");
    }
}
