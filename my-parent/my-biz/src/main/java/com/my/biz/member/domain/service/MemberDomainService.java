package com.my.biz.member.domain.service;


import com.my.biz.member.app.dto.LoginDto;
import com.my.biz.member.app.dto.MemberTestDto;
import com.my.biz.member.app.dto.MemberWriteQuestionDto;
import com.my.biz.member.domain.entity.Member;
import com.my.biz.member.domain.entity.MemberTest;
import com.my.biz.member.domain.entity.MemberTestDes;
import com.my.biz.member.domain.repository.MemberRepository;
import com.my.biz.member.domain.repository.MemberTestDesRepository;
import com.my.biz.member.domain.repository.MemberTestRepository;
import com.my.biz.questions.app.dto.PaperCheckingDesDto;
import com.my.biz.questions.app.dto.PaperCheckingDto;
import com.my.biz.questions.app.service.QuestionsAppService;
import com.my.common.annotation.DomainService;
import com.my.common.enumer.Code;
import com.my.common.exception.CPBusinessException;
import com.my.common.session.MyContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员领域服务
 * Created by 96230 on 2017/6/10.
 */
@DomainService
public class MemberDomainService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuestionsAppService questionsAppService;

    @Autowired
    private MemberTestRepository memberTestRepository;

    @Autowired
    private MemberTestDesRepository memberTestDesRepository;

    /**
     * 登录
     *
     * @param loginDto
     * @return
     */
    public Member findByPhoneAndPassword(LoginDto loginDto) {
        Member member = memberRepository.findByPhoneAndPassWord(loginDto.getPhone(), loginDto.getPassword());
        if (member == null) {
            CPBusinessException.throwIt(Code.ACCOUNTORPASSERROR.getMessage(), Code.ACCOUNTORPASSERROR.getCode());
        }
        return member;
    }

    /**
     * 会员考试
     * @param list
     * @return
     */
    public MemberTestDto memberTest(MemberWriteQuestionDto list) {
        if(list==null)CPBusinessException.throwIt("为提交任何答案");
        MemberTestDto memberTestDto = new MemberTestDto();
        if (list == null) CPBusinessException.throwIt("并未作答");
        //会员作答
        PaperCheckingDto paperCheckingDto = questionsAppService.paperCheckingDto(list.getMemberAnswerDtoList()).getData();
        //页面返回值
        memberTestDto.setResults(paperCheckingDto.getResults());
        memberTestDto.setPaperId(paperCheckingDto.getPaperId());
        memberTestDto.setWriteCount(paperCheckingDto.getCorrect()+paperCheckingDto.getError());
        memberTestDto.setPassingMark(paperCheckingDto.getPassingMark());
        memberTestDto.setTime(list.getTime());

        MemberTest memberTest=new MemberTest();
        memberTest.from(paperCheckingDto);
        memberTest.setMemberId(MyContext.get().getId());
        memberTest.setTime(list.getTime());
        memberTest=memberTestRepository.save(memberTest);
        memberTestDto.setId(memberTest.getId());
        for(PaperCheckingDesDto paperCheckingDesDto:paperCheckingDto.getPaperCheckingDesDtoList()){
            MemberTestDes memberTestDes=new MemberTestDes();
            memberTestDes.from(paperCheckingDesDto);
            memberTestDes.setMemberTestId(memberTest.getId());
            memberTestDesRepository.save(memberTestDes);
        }

        return memberTestDto;
    }

    public MemberTest findMemberTestById(Long id){
        if(id==null)CPBusinessException.throwIt("该考试id不存在");
        MemberTest memberTest= memberTestRepository.findOne(id);
        if(memberTest==null)CPBusinessException.throwIt("该id记录不存在");
        return memberTest;
    }
}
