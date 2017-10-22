package com.my.biz.questions.domain.entity;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.questions.domain.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:49
 */
@Entity
@Table(name="paper")
public class Paper extends BaseEntity {

    @Autowired
    private transient QuestionRepository questionRepository;

    @Column(name="name",columnDefinition = "varchar(50) comment '试卷名称'")
    private String name;

    @Column(name="max_type",columnDefinition = "int(5) comment '试卷大类型'")
    private Integer maxType;

    @Column(name="min_type",columnDefinition = "int(5) comment '试卷小类型'")
    private Integer minType;

    @Column(name="type",columnDefinition = "int(5) comment '考试题|练习题'")
    private Integer type;

    @Column(name="questions_id",columnDefinition = "int(11) comment '题库名称'")
    private Long questionsId;

    @Column(name="examination_time",columnDefinition = "int(11) comment '考试时间'")
    private Long examinationTime;

    @Column(name="passing_mark",columnDefinition = "double comment '及格分数'")
    private Double passingMark;


    private  transient  Integer countQuestion;//统计多少题

    public Double getPassingMark() {
        return passingMark;
    }

    public void setPassingMark(Double passingMark) {
        this.passingMark = passingMark;
    }

    public Long getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(Long examinationTime) {
        this.examinationTime = examinationTime;
    }

    public Integer getCountQuestion() {
        if(id!=null){
            countQuestion=questionRepository.countByPaperId(id);
        }
        return countQuestion;
    }

    public void setCountQuestion(Integer countQuestion) {
        this.countQuestion = countQuestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxType() {
        return maxType;
    }

    public void setMaxType(Integer maxType) {
        this.maxType = maxType;
    }

    public Integer getMinType() {
        return minType;
    }

    public void setMinType(Integer minType) {
        this.minType = minType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Long questionsId) {
        this.questionsId = questionsId;
    }
}
