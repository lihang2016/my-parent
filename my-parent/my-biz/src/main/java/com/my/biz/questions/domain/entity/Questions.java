package com.my.biz.questions.domain.entity;

import com.my.biz.common.entity.AggEntity;
import com.my.biz.questions.domain.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:20
 */
@Entity
@Table(name="questions")
public class Questions extends AggEntity {

    @Autowired
    private transient PaperRepository paperRepository;

    @Column(name="name",columnDefinition = "varchar(50) comment '题库名称'")
    private String name;

    @Column(name="img_url",columnDefinition = "varchar(500) comment '题库图片'")
    private String imgUrl;

    @Column(name="type",columnDefinition = "int(5) comment '题库类型'")
    private Integer type;



    private transient Integer paperCount;

    private transient Integer practiceCount;

    public Integer getPaperCount() {
        if(this.id!=null){
            paperCount=paperRepository.countByType(1);
        }
        return paperCount;
    }

    public void setPaperCount(Integer paperCount) {
        this.paperCount = paperCount;
    }

    public Integer getPracticeCount() {
        if(this.id!=null){
            practiceCount=paperRepository.countByType(2);
        }
        return practiceCount;
    }

    public void setPracticeCount(Integer practiceCount) {
        this.practiceCount = practiceCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
