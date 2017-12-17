package com.my.biz.member.domain.entity;

import com.my.biz.common.entity.BaseEntity;

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

}
