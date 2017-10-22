package com.my.biz.questions.domain.entity;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.questions.domain.repository.QunestionSelectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description"试题
 * @Date 2017/8/5 15:32
 */
@Entity
@Table(name="question")
public class Question  extends BaseEntity{

    @Autowired
    private transient QunestionSelectRepository qunestionSelectRepository;

    @Column(name="question_name",columnDefinition = "varchar(50) comment '名称'")
    private String questionName;

    @Column(name="multiselect",columnDefinition = "int(1) comment '是否多选'")
    private Boolean multiselect; //1 false 不是 0 是 true

    @Column(name="what_question",columnDefinition = "int(5) comment '第几题'")
    private Integer whatQuestion;

    @Column(name="paper_Id",columnDefinition = "int(11) comment '试卷id'")
    private Long paperId;

    @Column(name="answer",columnDefinition = "varchar(10) comment '答案'")
    private String answer;

    @Column(name="answer_des",columnDefinition = "varchar(500) comment '答案详情'")
    private String answerDes;

    @Column(name="numbers",columnDefinition = "double comment '分数'")
    private Double numbers;

    private transient List<QunestionSelect> qunestionSelects;

    public List<QunestionSelect> getQunestionSelects() {
        if(id!=null&&qunestionSelects==null){
        qunestionSelects=qunestionSelectRepository.findByQuestionId(id);
        }
        return qunestionSelects;
    }

    public void setQunestionSelects(List<QunestionSelect> qunestionSelects) {
        this.qunestionSelects = qunestionSelects;
    }

    public Double getNumbers() {
        return numbers;
    }

    public void setNumbers(Double numbers) {
        this.numbers = numbers;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Boolean getMultiselect() {
        return multiselect;
    }

    public void setMultiselect(Boolean multiselect) {
        this.multiselect = multiselect;
    }


    public Integer getWhatQuestion() {
        return whatQuestion;
    }

    public void setWhatQuestion(Integer whatQuestion) {
        this.whatQuestion = whatQuestion;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDes() {
        return answerDes;
    }

    public void setAnswerDes(String answerDes) {
        this.answerDes = answerDes;
    }
}
