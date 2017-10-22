package com.my.common.session;


/**
 * @Author lihang 【962309372@qq.com】
 * @Description:共享线程类， 用于存储登录信息
 * @Date 2017/8/29 19:08
 */
public class MyContext {
    private static ThreadLocal<MemberUser> local = new ThreadLocal<MemberUser>();

    /**
     * 赋值
     *
     * @param session
     */
    public static void set(MemberUser session) {
        local.set(session);
    }

    /**
     * 取值
     *
     * @return
     */
    public static MemberUser get() {
        return local.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        local.remove();
    }
}
