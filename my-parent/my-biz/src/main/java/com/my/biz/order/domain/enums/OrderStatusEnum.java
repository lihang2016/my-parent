package com.my.biz.order.domain.enums;

import com.my.common.enumer.Messageable;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/24 20:24
 */
public enum OrderStatusEnum implements Messageable {
    PendingShipment("PendingShipment", "待发货"),
    Shipped("Shipped", "已发货"),
    AlreadyServed("AlreadyServed", "已送达");

    private String code;

    private String message;

    OrderStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
