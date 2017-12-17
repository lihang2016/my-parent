package com.my.biz.member.domain.service;


import com.my.biz.member.app.dto.LoginDto;
import com.my.biz.member.domain.entity.Member;
import com.my.biz.member.domain.repository.MemberRepository;
import com.my.common.annotation.DomainService;
import com.my.common.enumer.Code;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员领域服务
 * Created by 96230 on 2017/6/10.
 */
@DomainService
public class MemberDomainService {

    @Autowired
    private MemberRepository memberRepository;


    /**
     * 登录
     *
     * @param loginDto
     * @return
     */
    public Member findByPhoneAndPassword(LoginDto loginDto) {
        Member member = memberRepository.login(loginDto.getAccount(), loginDto.getPassword());
        if (member == null) {
            CPBusinessException.throwIt(Code.ACCOUNTORPASSERROR.getMessage(), Code.ACCOUNTORPASSERROR.getCode());
        }
        return member;
    }
}
