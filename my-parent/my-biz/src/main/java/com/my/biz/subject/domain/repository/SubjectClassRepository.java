package com.my.biz.subject.domain.repository;

import com.my.biz.common.entity.BaseRepository;
import com.my.biz.subject.domain.entity.SubjectClass;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 11:57
 */
public interface SubjectClassRepository  extends BaseRepository<SubjectClass,Long>{

    /**
     * 统计总课时
     * @param subjectId
     * @return
     */
    Integer countBySubjectId(Long subjectId);

    /**
     * 查询课节列表
     * @param subjectId
     * @return
     */
    List<SubjectClass> findBySubjectId(Long subjectId);
}
