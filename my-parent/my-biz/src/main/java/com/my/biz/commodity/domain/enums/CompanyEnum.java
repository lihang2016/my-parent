package com.my.biz.commodity.domain.enums;

import com.my.common.enumer.Messageable;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:25
 */
public enum CompanyEnum implements Messageable {
    box("box","盒"),
    pot("pot","罐"),
    bottle("bottle","瓶"),
    individual("individual","个")
    ;


    private String code;

    private String message;

    CompanyEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
