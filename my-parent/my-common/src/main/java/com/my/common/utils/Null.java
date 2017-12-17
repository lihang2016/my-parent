package com.my.common.utils;

import java.io.Serializable;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/10/28 12:55
 */
public class Null implements Serializable {
    private static final long serialVersionUID = 1L;
    public static Null INSTANCE = new Null();

    private Null() {
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
