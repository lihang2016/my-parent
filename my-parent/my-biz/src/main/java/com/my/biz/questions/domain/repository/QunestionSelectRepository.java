package com.my.biz.questions.domain.repository;

import com.my.biz.common.entity.BaseRepository;
import com.my.biz.questions.domain.entity.QunestionSelect;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 23:24
 */
public interface QunestionSelectRepository extends BaseRepository<QunestionSelect,Long> {

    List<QunestionSelect> findByQuestionId(Long questionId);
}
