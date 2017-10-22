package com.my.biz.member.domain.entity;

import com.my.biz.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 16:56
 */
@Entity
@Table(name="member_test_des")
public class MemberTestDes  extends BaseEntity{

    @Column(name="question_Id",columnDefinition = "double comment '题id'")
    private Long questionId;

    @Column(name="it_correct",columnDefinition = "int(1) comment '是否正确'")
    private Boolean itCorrect;

    @Column(name="whatQuestion",columnDefinition = "int(5) comment '第几题'")
    private Integer whatQuestion;

    @Column(name="member_test_id",columnDefinition = "int(11) comment '会员测试id'")
    private Long memberTestId;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Boolean getItCorrect() {
        return itCorrect;
    }

    public void setItCorrect(Boolean itCorrect) {
        this.itCorrect = itCorrect;
    }

    public Integer getWhatQuestion() {
        return whatQuestion;
    }

    public void setWhatQuestion(Integer whatQuestion) {
        this.whatQuestion = whatQuestion;
    }

    public Long getMemberTestId() {
        return memberTestId;
    }

    public void setMemberTestId(Long memberTestId) {
        this.memberTestId = memberTestId;
    }
}