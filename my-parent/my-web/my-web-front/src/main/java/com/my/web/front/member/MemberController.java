package com.my.web.front.member;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.member.app.dto.LoginDto;
import com.my.biz.member.app.service.MemberAppService;
import com.my.common.utils.MyDES;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/28 20:51
 */
@RestController
public class MemberController extends FrontContrllor{

    @Autowired
    private MemberAppService memberAppService;

    @RequestMapping(value = "/member/login.json")
    public ViewInfo login(LoginDto loginDto){
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getAccount(), MyDES.encryptBasedDes(loginDto.getAccount()+loginDto.getPassword()));
        SecurityUtils.getSubject().login(token);
        ViewInfo viewInfo=new ViewInfo();
        viewInfo.setCode("100");
        viewInfo.setSuccess(SecurityUtils.getSubject().isAuthenticated());
        return viewInfo;
    }

}
