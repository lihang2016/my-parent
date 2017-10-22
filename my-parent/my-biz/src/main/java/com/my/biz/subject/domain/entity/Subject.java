package com.my.biz.subject.domain.entity;

import com.my.biz.common.entity.AggEntity;
import com.my.biz.subject.domain.repository.SubjectClassRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 0:28
 */
@Entity
@Table(name="subject")
public class Subject extends AggEntity {

    @Autowired
    private transient SubjectClassRepository subjectClassRepository;

    @Column(name = "img_Url", columnDefinition = "varchar(500)  comment '课程图片'")
    private String imgUrl;

    @Column(name = "name", columnDefinition = "varchar(50)  comment '课程名称'")
    private String name;

    @Column(name = "author", columnDefinition = "varchar(50)  comment '作者'")
    private String author;

    @Column(name = "viewing_Times", columnDefinition = "int(5)  comment '观看次数'")
    private Integer viewingTimes;

    @Column(name = "fee", columnDefinition = "int(1)  comment '是否免费'")
    private Boolean fee=Boolean.FALSE;//false 0 不免费。true 1 免费

    @Column(name = "money", columnDefinition = "decimal(10,2)  comment '多少钱'")
    private BigDecimal money;

    @Column(name = "des", columnDefinition = "varchar(500)  comment '描述'")
    private String des;

    @Column(name = "upload_time", columnDefinition = "datetime  comment '上传时间'")
    private Date uploadTime;

    @Column(name = "subject_type", columnDefinition = "int(5)  comment '课程大类型'")
    private Integer subjectType;

    @Column(name = "subject_children_type", columnDefinition = "int(5)  comment '课程小类型'")
    private Integer subjectChildrenType;


    private transient Integer classCount;//总课时

    private transient List<SubjectClass> classList;

    public List<SubjectClass> getClassList() {
        if(id!=null&&classList==null){
            classList=subjectClassRepository.findBySubjectId(id);
        }
        return classList;
    }

    public void setClassList(List<SubjectClass> classList) {
        this.classList = classList;
    }

    public Integer getClassCount() {
        if(this.id!=null){
            this.classCount=subjectClassRepository.countBySubjectId(this.id);
        }
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getSubjectChildrenType() {
        return subjectChildrenType;
    }

    public void setSubjectChildrenType(Integer subjectChildrenType) {
        this.subjectChildrenType = subjectChildrenType;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getViewingTimes() {
        return viewingTimes;
    }

    public void setViewingTimes(Integer viewingTimes) {
        this.viewingTimes = viewingTimes;
    }

    public Boolean getFee() {
        return fee;
    }

    public void setFee(Boolean fee) {
        this.fee = fee;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
