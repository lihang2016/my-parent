package com.my.biz.subject.domain.entity;

import com.my.biz.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 11:40
 */
@Entity
@Table(name="subject_class")
public class SubjectClass extends BaseEntity {

    @Column(name = "periods", columnDefinition = "double  comment '节数'")
    private Double periods;

    @Column(name = "fee", columnDefinition = "int(1)  comment '节数'")
    private Boolean fee=Boolean.FALSE;

    @Column(name = "class_name", columnDefinition = "varchar(40)  comment '课程名称'")
    private String className;

    @Column(name = "subject_id", columnDefinition = "int(11)  comment '课程id'")
    private Long subjectId;

    public Double getPeriods() {
        return periods;
    }

    public void setPeriods(Double periods) {
        this.periods = periods;
    }

    public Boolean getFee() {
        return fee;
    }

    public void setFee(Boolean fee) {
        this.fee = fee;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
