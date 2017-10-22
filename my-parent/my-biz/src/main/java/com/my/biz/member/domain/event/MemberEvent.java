package com.my.biz.member.domain.event;


import com.google.common.eventbus.EventBus;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 15:22 2017/7/4
 */
public class MemberEvent extends EventBus {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
