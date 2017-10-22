package com.my.biz.advertisement.domain.entity;

import com.my.biz.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/28 23:36
 */
@Entity
@Table(name="position")
public class Position  extends BaseEntity{

    @Column(name="position",columnDefinition = "varchar(20) comment '广告位置'")
    private String position;



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
