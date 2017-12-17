package com.my.biz.member.app.service;

import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.member.app.dto.*;
import com.my.biz.member.domain.service.MemberDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created by 96230 on 2017/6/10.
 */
@AppService
@Transactional
public class MemberAppService {

    @Autowired
    private MemberDomainService memberDomainService;

    /**tggyyyyyyyyyyyyyyy
     * 登录
     * @param loginDto
     * @return
     */
    public SingleResponse<MemberDto> findByPhoneAndPassword(LoginDto loginDto){
        return SingleResponse.from(memberDomainService.findByPhoneAndPassword(loginDto).to(MemberDto.class));
    }
}
