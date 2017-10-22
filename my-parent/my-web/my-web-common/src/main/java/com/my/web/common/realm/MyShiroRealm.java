package com.my.web.common.realm;

import com.my.biz.member.app.dto.LoginDto;
import com.my.biz.member.app.dto.MemberDto;
import com.my.biz.member.app.service.MemberAppService;
import com.my.common.utils.MyDES;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 9:37 2017/7/20
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private MemberAppService memberAppService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "lock_";
    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();//账号
        String password =String.valueOf(token.getPassword());//密码
        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
        //计数大于5时，设置用户被锁定一小时
        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=5){
            opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
            throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
        }
        LoginDto loginDto=new LoginDto();
        loginDto.setPhone(name);
        loginDto.setPassword(password);
        MemberDto memberDto=memberAppService.findByPhoneAndPassword(loginDto).getData();
        if(memberDto==null){
            throw new  AuthenticationException("账号或者密码错误");
        }
        //存登录信息
//        MyContext.set(memberDto);
        //清空登录计数
        opsForValue.set(SHIRO_LOGIN_COUNT+name, "0");
        return new SimpleAuthenticationInfo(memberDto, password, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
        MemberDto memberDto = (MemberDto) SecurityUtils.getSubject().getPrincipal();
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()"+user);
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		List<SysRole> roleList = sysRoleService.selectByMap(map);
		Set<String> roleSet = new HashSet<String>();
		for(SysRole role : roleList){
			roleSet.add(role.getType());
		}*/
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);
        //根据用户ID查询权限（permission），放入到Authorization里。
		/*List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
		Set<String> permissionSet = new HashSet<String>();
		for(SysPermission Permission : permissionList){
			permissionSet.add(Permission.getName());
		}*/
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限添加");
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);
        return info;
    }

    public static void main(String[] args) {
        String pwd= MyDES.encryptBasedDes("13896995689"+"123456");
        System.out.println(pwd);
    }
}
