package com.ChrisSun.service;

import com.ChrisSun.dao.QuestionDao;
import com.ChrisSun.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chris on 2016/7/25.
 */
@Service
public class QuestionService
{
    @Autowired
    private QuestionDao questionDao;

    public void addQuestion(Question question){
        questionDao.addQuestion(question);
    }
    public void updateContent(Question question){
        questionDao.updateContent(question);
    }
    public Question getQuesById(int id){
        return questionDao.getQuestionById(id);
    }
    public List<Question> getLatestQuesList(int id, int offset, int limit){
        return questionDao.getLatestQuesList(id,offset,limit);
    }
}
