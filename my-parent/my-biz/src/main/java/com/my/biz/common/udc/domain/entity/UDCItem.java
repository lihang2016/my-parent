
package com.my.biz.common.udc.domain.entity;


import com.my.biz.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 *
 */
@Entity
@Table(name = "pf_udc_item", indexes = {@Index(unique = true, name = "unique_idx",
        columnList = "type_id,code")})
public class UDCItem extends BaseEntity {

    @Column(name = "name", columnDefinition = "varchar(60) NOT NULL COMMENT '名称'")
    private String name;

    @Column(name = "code", columnDefinition = "varchar(30) NOT NULL COMMENT '编码'")
    private String code;

    @Column(name = "type_id", columnDefinition = "int(11) unsigned  NOT NULL COMMENT '枚举类型'")
    private Long typeId;

    @Min(1)
    @Column(name = "value", columnDefinition = "tinyint(3) NOT NULL COMMENT '值,必须大于0'")
    private Integer value;

    @Column(name = "is_sys_init", columnDefinition = "tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否预置(0否1是)'")
    private boolean reserved;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
