package com.my.biz.questions.domain.entity;

import com.my.biz.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 23:21
 */
@Entity
@Table(name="qunestion_select")
public class QunestionSelect extends BaseEntity {

    @Column(name="options",columnDefinition = "varchar(5) comment '选项'")
    private String options;

    @Column(name="option_context",columnDefinition = "varchar(50) comment '选项内容'")
    private String optionContext;

    @Column(name="question_id",columnDefinition = "int(11) comment '题id'")
    private Long questionId;


    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptionContext() {
        return optionContext;
    }

    public void setOptionContext(String optionContext) {
        this.optionContext = optionContext;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
