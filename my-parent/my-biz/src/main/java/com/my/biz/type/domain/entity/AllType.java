package com.my.biz.type.domain.entity;

import com.my.biz.common.entity.AggEntity;
import com.my.biz.common.udc.UDC;
import com.my.biz.common.udc.UDC.EnumTypeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:16
 */
@Entity
@Table(name="all_type")
public class AllType extends AggEntity {

    @Column(name="name",columnDefinition = "varchar(50) comment '类型名称'")
    private String name;

    @Column(name="type_id",columnDefinition = "int(11) comment '类型id 。二级联动'")
    private Long typeId;

    @Column(name="type",columnDefinition = "tinyint(3) comment '类型'")
    @EnumTypeCode("allType")
    private UDC type;


    public UDC getType() {
        return type;
    }

    public void setType(UDC type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
