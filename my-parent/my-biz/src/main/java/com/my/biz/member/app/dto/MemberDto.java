package com.my.biz.member.app.dto;

import com.my.biz.common.udc.UDC;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 14:39 2017/7/20
 */
@Getter
@Setter
public class MemberDto implements Serializable {

    private Long id;

    private UDC sex;

    private String phone;


    private String address;


    private String passWord;

    private String memberType;

}
