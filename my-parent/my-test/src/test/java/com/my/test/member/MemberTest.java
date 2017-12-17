package com.my.test.member;

import com.google.common.collect.Lists;
import com.my.biz.member.app.dto.MemberAnswerDto;
import com.my.biz.member.app.dto.MemberWriteQuestionDto;
import com.my.biz.member.app.service.MemberAppService;
import com.my.common.session.MemberUser;
import com.my.common.session.MyContext;
import com.my.test.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 22:09
 */
@Slf4j
public class MemberTest extends TestBase {

    static {
        MemberUser memberUser=new MemberUser();
        memberUser.setId(1L);
        MyContext.set(memberUser);
    }
    @Autowired
    private MemberAppService memberAppService;

    @Test
    public void testMemberTest(){
        List<MemberAnswerDto> memberAnswerDtoList= Lists.newArrayList();
        MemberWriteQuestionDto memberWriteQuestionDto=new MemberWriteQuestionDto();
        MemberAnswerDto memberAnswerDto=new MemberAnswerDto();
        MemberAnswerDto memberAnswerDto1=new MemberAnswerDto();
        memberAnswerDto.setAnswer("A");
        memberAnswerDto.setQuestionId(1L);
        memberAnswerDtoList.add(memberAnswerDto);
        memberAnswerDto1.setAnswer("");
        memberAnswerDto1.setQuestionId(2L);
        memberAnswerDtoList.add(memberAnswerDto1);
        memberWriteQuestionDto.setMemberAnswerDtoList(memberAnswerDtoList);
    }

    @Test
    public void testMemberTestDesById(){
    }
}
