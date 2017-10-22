package com.my.biz.member.domain.repository;


import com.my.biz.common.entity.BaseRepository;
import com.my.biz.member.domain.entity.MemberTestDes;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 21:25
 */
public interface MemberTestDesRepository extends BaseRepository<MemberTestDes,Long> {

    List<MemberTestDes> findByMemberTestId(Long id);
}
