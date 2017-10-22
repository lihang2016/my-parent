package com.my.biz.common.util;

import org.springframework.stereotype.Component;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 21:28
 */
@Component
public class MemberContext {

    public static MemberUser memberUser;

    public static MemberUser getMemberUser() {
        return memberUser;
    }

    public void setMemberUser(MemberUser memberUser) {
        this.memberUser = memberUser;
    }

    public static class MemberUser{
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
