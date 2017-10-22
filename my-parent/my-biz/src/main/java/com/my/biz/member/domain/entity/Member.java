package com.my.biz.member.domain.entity;


import com.my.biz.common.entity.AggEntity;
import com.my.biz.common.udc.UDC;
import com.my.biz.common.udc.UDC.EnumTypeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 96230 on 2017/6/10.
 */
@Entity
@Table(name="member")
public class Member extends AggEntity {

    @Column(name="member_image_url",columnDefinition = "varchar(500) comment '会员头像'")
    private String memberImageUrl;

    @Column(name="sex",columnDefinition = "tinyint(3) comment '性别'")
    @EnumTypeCode("sex")
    private UDC sex;

    @Column(name="nickname",columnDefinition = "varchar(20) comment '会员昵称'")
    private String nickname;

    @Column(name="name",columnDefinition = "varchar(20) comment '会员名称'")
    private String name;

    @Column(name="phone",columnDefinition = "varchar(20) comment '出生日期'")
    private String phone;

    @Column(name="address",columnDefinition = "varchar(500) comment '联系地址'")
    private String address;

    @Column(name="email",columnDefinition = "varchar(100) comment '邮箱'")
    private String email;

    @Column(name="pass_word",columnDefinition = "varchar(40) comment '密码'")
    private String passWord;

    @Column(name="member_type",columnDefinition = "varchar(40) comment '密码'")
    private String memberType;

    public String getMemberImageUrl() {
        return memberImageUrl;
    }

    public void setMemberImageUrl(String memberImageUrl) {
        this.memberImageUrl = memberImageUrl;
    }

    public UDC getSex() {
        return sex;
    }

    public void setSex(UDC sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
