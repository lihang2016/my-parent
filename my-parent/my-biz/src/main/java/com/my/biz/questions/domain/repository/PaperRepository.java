package com.my.biz.questions.domain.repository;

import com.my.biz.common.entity.BaseRepository;
import com.my.biz.questions.domain.entity.Paper;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:54
 */
public interface PaperRepository extends BaseRepository<Paper,Long> {

    Integer countByType(Integer type);
}
