package com.my.biz.order.domain.entity;

import com.my.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/25 21:39
 */
@Entity
@Table(name = "or_order_details",indexes = {@Index(unique = true, name = "unique_order_details_idx",
        columnList = "bar_code,order_id")})
@Getter
@Setter
public class OrderDetails extends AggEntity {

    @Column(name="order_id",columnDefinition ="int(11) comment '订单id'" )
    private Long orderId;
    @Column(name="bar_code",columnDefinition ="int(11) comment '商品条码'" )
    private Long barCode;

    @Column(name="name",columnDefinition ="varchar(200) comment '商品名称'" )
    private String name;

    @Column(name="number",columnDefinition ="int(9) comment '商品数量'" )
    private Integer number;

    @Column(name="supplier_price",columnDefinition ="decimal(10,2) comment '供应商进价'" )
    private BigDecimal supplierPrice;

    @Column(name="shop_price",columnDefinition ="decimal(10,2) comment '店铺进价'" )
    private  BigDecimal shopPrice;
}
