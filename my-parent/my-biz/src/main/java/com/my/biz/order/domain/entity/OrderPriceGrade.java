package com.my.biz.order.domain.entity;

import com.my.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 13:49
 */
@Getter
@Setter
@Entity
@Table(name="or_order_price_grade")
public class OrderPriceGrade extends BaseEntity {

    @Column(name = "grade",columnDefinition = "varchar(100) comment '价位档次'")
    private String grade;

    @Column(name = "money",columnDefinition = "decimal(10,2) comment '价格'")
    private BigDecimal money;

}
