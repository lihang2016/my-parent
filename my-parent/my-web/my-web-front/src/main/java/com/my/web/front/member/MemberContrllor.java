package com.my.web.front.member;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.member.app.dto.LoginDto;
import com.my.biz.member.app.dto.MemberWriteQuestionDto;
import com.my.biz.member.app.service.MemberAppService;
import com.my.common.enumer.Code;
import com.my.common.exception.CPBusinessException;
import com.my.common.session.MyContext;
import com.my.common.utils.MyDES;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/28 20:51
 */
@RestController
public class MemberContrllor  extends FrontContrllor{

    @Autowired
    private MemberAppService memberAppService;

    @RequestMapping("/login.json")
    public ViewInfo login(LoginDto loginDto){
        if(loginDto.getPassword()==null || "".equals(loginDto.getPassword())){
            CPBusinessException.throwIt("密码不能为空", Code.ACCOUNTORPASSERROR.getCode());
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getPhone(), MyDES.encryptBasedDes(loginDto.getPhone()+loginDto.getPassword()));
//       token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
        ViewInfo viewInfo=new ViewInfo();
        viewInfo.setCode("100");
        viewInfo.setSuccess(SecurityUtils.getSubject().isAuthenticated());
        return viewInfo;
    }

    /**
     * 会员考试
     * @param memberWriteQuestionDto
     * @return
     */
    @PostMapping("/member/memberTest.json")
    public ViewInfo memberTest(@RequestBody  MemberWriteQuestionDto memberWriteQuestionDto){
        return memberAppService.memberTest(memberWriteQuestionDto).convertTo();
    }

    /**
     * 会员考试详情
     * @param modelId
     * @return
     */
    @RequestMapping("/member/findMemberDesById.json")
    public ViewInfo findMemberDesById(ModelId modelId){
        System.out.println(MyContext.get().getId());
        return memberAppService.findMemberTestById(modelId.getId()).convertTo();
    }
}
