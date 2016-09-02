package com.ChrisSun.service;

import com.ChrisSun.model.Question;

import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */
public interface QuestionService {

    int addQuestion(Question question);
    void updateContent(Question question);
    Question getQuesById(int id) throws Exception;
    List<Question> getLatestQuesList(int id, int offset, int limit);
    int updateCommentCount(int id,int commentCount) throws Exception;
}
