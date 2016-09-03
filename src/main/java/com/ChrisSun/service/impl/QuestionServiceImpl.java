package com.ChrisSun.service.impl;

import com.ChrisSun.dao.QuestionDao;
import com.ChrisSun.model.Question;
import com.ChrisSun.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by Chris on 2016/7/25.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private SensitiveService sensitiveService;

    public int addQuestion(Question question){
        //html标签过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        int result = questionDao.addQuestion(question);
        return result > 0 ? question.getId() : 0;
    }
    public void updateContent(Question question) {
        try {
            questionDao.updateContent(question);
        }catch (Exception e){
            logger.error("service failed to update question content",e.getMessage());
        }
    }
    public Question getQuesById(int id) throws Exception{
            return questionDao.getQuestionById(id);
    }
    public List<Question> getLatestQuesList(int id, int offset, int limit){
        return questionDao.getLatestQuesList(id,offset,limit);
    }

    @Override
    public int updateCommentCount(int id, int commentCount) throws Exception{
        return questionDao.updateCommentCount(commentCount,id);
    }
}
