package com.my.biz.commodity.domain.entity;

import com.my.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:42
 */
@Getter
@Setter
@Entity
@Table(name="cd_commodity_type")
public class CommodityType extends BaseEntity {

    @Column(name="name",columnDefinition = "varchar(500) comment '类型名称'")
    private String name;

    @Column(name="parent_id",columnDefinition = "varchar(500) comment '父级类型'")
    private Long parentId;


}
