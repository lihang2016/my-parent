package com.my.biz.questions.domain.service;

import com.google.common.collect.Lists;
import com.my.biz.member.app.dto.MemberAnswerDto;
import com.my.biz.questions.app.dto.PaperCheckingDesDto;
import com.my.biz.questions.app.dto.PaperCheckingDto;
import com.my.biz.questions.app.dto.PaperSearchDto;
import com.my.biz.questions.app.dto.QuestionsSearchDto;
import com.my.biz.questions.domain.entity.Paper;
import com.my.biz.questions.domain.entity.Question;
import com.my.biz.questions.domain.entity.Questions;
import com.my.biz.questions.domain.repository.PaperRepository;
import com.my.biz.questions.domain.repository.QuestionRepository;
import com.my.biz.questions.domain.repository.QuestionsRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:题库领域服务
 * @Date 2017/7/31 20:26
 */
@DomainService
public class QuestionDomainService {


    @Autowired
    private QuestionsRepository questionsRepository;//题库服务

    @Autowired
    private PaperRepository paperRepository;//试卷服务

    @Autowired
    private QuestionRepository questionRepository;//题服务

    /**
     * 题库查询
     *
     * @param questionsSearchDto
     * @return
     */
    public Page<Questions> find(QuestionsSearchDto questionsSearchDto) {
        Specification<Questions> questionsSpecification =
                (Root<Questions> root, CriteriaQuery<?> query,
                 CriteriaBuilder cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (!Objects.isNull(questionsSearchDto.getName())) {
                        predicates.add(cb.like(root.get("name"), "%" + questionsSearchDto.getName() + "%"));
                    }
                    query.where(cb.and(predicates.toArray(new Predicate[0])));
                    return query.getRestriction();
                };
        return questionsRepository.findAll(questionsSpecification, questionsSearchDto.toPage());
    }

    /**
     * 查询试卷
     *
     * @param paperSearchDto
     * @return
     */
    public Page<Paper> findPaper(PaperSearchDto paperSearchDto) {
        Specification<Paper> paperSpecification =
                (Root<Paper> root, CriteriaQuery<?> query,
                 CriteriaBuilder cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (!Objects.isNull(paperSearchDto.getQuestionsId())) {
                        predicates.add(cb.equal(root.get("questionsId"), paperSearchDto.getQuestionsId()));
                    }
                    if (!Objects.isNull(paperSearchDto.getMaxType())) {
                        predicates.add(cb.equal(root.get("maxType"), paperSearchDto.getMaxType()));
                    }
                    if (!Objects.isNull(paperSearchDto.getMinType())) {
                        predicates.add(cb.equal(root.get("minType"), paperSearchDto.getMinType()));
                    }
                    query.where(cb.and(predicates.toArray(new Predicate[0])));
                    return query.getRestriction();
                };
        return paperRepository.findAll(paperSpecification, paperSearchDto.toPage());

    }

    /**
     * 查询题
     *
     * @param paperId
     * @return
     */
    public List<Question> findByPaperId(Long paperId) {
        if (paperId == null) CPBusinessException.throwIt("试卷id 不能为空");
        List<Question> list = questionRepository.findByPaperIdOrderByWhatQuestionAsc(paperId);
        if (list == null || list.size() <= 0) {
            CPBusinessException.throwIt("该试卷中没有题");
        }
        return list;
    }

    /**
     * 检查试卷 计算得分
     *
     * @param memberAnswerDtos
     * @return
     */
    public PaperCheckingDto checkPaper(List<MemberAnswerDto> memberAnswerDtos) {
        PaperCheckingDto paperCheckingDto = new PaperCheckingDto();//做题情况
        List<PaperCheckingDesDto> paperCheckingDesDtoList=Lists.newArrayList();//做题详情
        Integer correct = 0;
        Integer error = 0;
        Integer unfinished = 0;
        Double results = 0d;
        for (MemberAnswerDto memberAnswerDto : memberAnswerDtos) {
            Boolean itCorrect=Boolean.FALSE;//是否作对
            PaperCheckingDesDto paperCheckingDesDto=new PaperCheckingDesDto();
            Question question = questionRepository.findOne(memberAnswerDto.getQuestionId());
            if (question == null) {
                CPBusinessException.throwIt(memberAnswerDto.getQuestionId() + "该题不存在，提交异常");
            }
            paperCheckingDto.setPaperId(question.getPaperId());
            paperCheckingDesDto.setQuestionId(question.getId());
            //如果作对了
            if (memberAnswerDto.getAnswer() != null && !"".equals(memberAnswerDto.getAnswer())) {
                if (question.getAnswer().equals(memberAnswerDto.getAnswer())) {
                    correct++;
                    results+=question.getNumbers();
                    itCorrect=Boolean.TRUE;
                } else error++;
            } else unfinished++;

            paperCheckingDesDto.setItCorrect(itCorrect);
            paperCheckingDesDto.setWhatQuestion(question.getWhatQuestion());
            paperCheckingDesDtoList.add(paperCheckingDesDto);
        }
        Paper paper=paperRepository.findOne(paperCheckingDto.getPaperId());
       paperCheckingDto.setPassingMark(paper.getPassingMark());
        paperCheckingDto.setCorrect(correct);
        paperCheckingDto.setError(error);
        paperCheckingDto.setResults(results);
        paperCheckingDto.setUnfinished(unfinished);
        paperCheckingDto.setPaperCheckingDesDtoList(paperCheckingDesDtoList);
        return paperCheckingDto;
    }
}
