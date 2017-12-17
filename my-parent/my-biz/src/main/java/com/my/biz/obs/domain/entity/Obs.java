package com.my.biz.obs.domain.entity;

import com.my.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author:lihang
 * @Description:文件
 * @Date Create in 17:04 2017/12/13
 */

@Entity
@Table(name="obs")
@Getter
@Setter
public class Obs extends AggEntity {

    @Column(name="file_name",columnDefinition = "varchar(200) not null comment '文件名称'")
    private String fileName;

    @Column(name="file_size",columnDefinition = "int(11) not null comment '文件大小'")
    private Long fileSize;

    @Column(name="provider_id",columnDefinition = "varchar(500) not null comment '文件路径'")
    private String providerId;
}
