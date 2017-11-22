package com.my.biz.advertisement.domain.entity;


import com.my.biz.common.entity.AggEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 96230 on 2017/6/10.
 */
@Entity
@Table(name="advertisement")
public class Advertisement extends AggEntity {

    @Column(name="image_url",columnDefinition = "varchar(500) comment '广告图片'")
    private String imageUrl;

    @Column(name="position_id",columnDefinition = "int(11) comment '广告位置'")
    private Long positionId;

//    @Column(name="sex",columnDefinition = "int(1) comment '性别'")
//    @UDC.EnumTypeCode("sex")
//    private UDC sex;

//
//    public UDC getSex() {
//        return sex;
//    }
//
//    public void setSex(UDC sex) {
//        this.sex = sex;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
}
