package com.my.biz.member.domain.entity;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.member.domain.repository.MemberTestDesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 16:49
 */
@Entity
@Table(name="member_test")
public class MemberTest extends BaseEntity {

    @Autowired
    private transient MemberTestDesRepository memberTestDesRepository;

    @Column(name="paper_Id",columnDefinition = "int(11) comment '试卷id'")
    private Long paperId;

    @Column(name="member_Id",columnDefinition = "int(11) comment '会员id'")
    private Long memberId;

    @Column(name="correct",columnDefinition = "int(5) comment '正确多少道题'")
    private Integer correct;

    @Column(name="error",columnDefinition = "int(5) comment '错误多少道题'")
    private Integer error;

    @Column(name="unfinished",columnDefinition = "int(5) comment '未做多少题'")
    private Integer unfinished;

    @Column(name="results",columnDefinition = "double comment '得分'")
    private Double results;

    @Column(name="time",columnDefinition = "int(11) comment '得分'")
    private Long time;

    private transient List<MemberTestDes> memberTestDes;


    public MemberTestDesRepository getMemberTestDesRepository() {
        return memberTestDesRepository;
    }

    public void setMemberTestDesRepository(MemberTestDesRepository memberTestDesRepository) {
        this.memberTestDesRepository = memberTestDesRepository;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<MemberTestDes> getMemberTestDes() {
        if(id!=null && memberTestDes==null){
            memberTestDes= memberTestDesRepository.findByMemberTestId(id);
        }
        return memberTestDes;
    }

    public void setMemberTestDes(List<MemberTestDes> memberTestDes) {
        this.memberTestDes = memberTestDes;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(Integer unfinished) {
        this.unfinished = unfinished;
    }

    public Double getResults() {
        return results;
    }

    public void setResults(Double results) {
        this.results = results;
    }
}
