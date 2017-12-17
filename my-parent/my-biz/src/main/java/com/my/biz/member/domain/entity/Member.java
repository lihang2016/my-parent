package com.my.biz.member.domain.entity;


import com.my.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 96230 on 2017/6/10.
 */
@Entity
@Table(name="member")
@Getter
@Setter
public class Member extends AggEntity {

    @Column(name="account",columnDefinition = "varchar(100) comment '账号'")
    private String account;

    @Column(name="passWord",columnDefinition = "varchar(40) comment '密码'")
    private String passWord;

    @Column(name="memberType",columnDefinition = "varchar(40) comment '用户类型'")
    private String memberType;
}
