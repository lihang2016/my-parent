package com.my.biz.member.domain.repository;

import com.my.biz.common.entity.BaseMapper;
import com.my.biz.member.domain.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/24 19:36
 */
public interface MemberRepository extends BaseMapper<Member>{

    @Select("select * from member where account=#{account} and passWord=#{password}")
    Member login(@Param("account") String account, @Param("password") String password);
}
