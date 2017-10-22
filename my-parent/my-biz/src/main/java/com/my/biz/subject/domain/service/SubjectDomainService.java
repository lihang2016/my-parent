package com.my.biz.subject.domain.service;

import com.google.common.collect.Lists;
import com.my.biz.subject.app.dto.SubjectSearchDto;
import com.my.biz.subject.domain.entity.Subject;
import com.my.biz.subject.domain.repository.SubjectRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:课程服务
 * @Date 2017/7/29 11:58
 */
@DomainService
public class SubjectDomainService {

    @Autowired
    private SubjectRepository subjectRepository; //课程领域服务


    /**
     * 查询课程列表
     *
     * @param subjectSearchDto
     * @return
     */

    public Page<Subject> findSubjectList(SubjectSearchDto subjectSearchDto) {
        List<Sort.Order> orders = Lists.newArrayList();
        if (subjectSearchDto.getNewSubject()) orders.add(new Sort.Order(Sort.Direction.DESC, "uploadTime"));
        if (subjectSearchDto.getRecommend()) orders.add(new Sort.Order(Sort.Direction.DESC, "viewingTimes"));

        subjectSearchDto.setOrders(orders);
        Specification<Subject> apply =
                (Root<Subject> root, CriteriaQuery<?> query,
                 CriteriaBuilder cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (!Objects.isNull(subjectSearchDto.getSubjectType())) {
                        predicates.add(cb.equal(root.get("subjectType"), subjectSearchDto.getSubjectType()));
                    }
                    if (!Objects.isNull(subjectSearchDto.getSubjectChildrenType())) {
                        predicates.add(cb.equal(root.get("subjectChildrenType"), subjectSearchDto.getSubjectChildrenType()));
                    }
                    query.where(cb.and(predicates.toArray(new Predicate[0])));
                    return query.getRestriction();
                };
        return subjectRepository.findAll(apply, subjectSearchDto.toPage());
    }

    /**
     * 查询课程详情
     * @param id
     * @return
     */
    public Subject findById(Long id){
        if(id==null) CPBusinessException.throwIt("id 不能为空");
        Subject subject=subjectRepository.findOne(id);
        if(subject==null){
            CPBusinessException.throwIt("id不存在");
        }
        return subject;
    }

}
