package com.my.biz.questions.domain.repository;

import com.my.biz.common.entity.BaseRepository;
import com.my.biz.questions.domain.entity.Question;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 15:46
 */
public interface QuestionRepository extends BaseRepository<Question,Long> {
    /**
     * 根据试卷统计多少题
     * @param paperId
     * @return
     */
    Integer countByPaperId(Long paperId);

    List<Question> findByPaperIdOrderByWhatQuestionAsc(Long paperId);
}
