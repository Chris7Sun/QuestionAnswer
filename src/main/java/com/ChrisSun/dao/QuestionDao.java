package com.ChrisSun.dao;

import com.ChrisSun.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Chris on 2016/7/24.
 */
@Mapper
@Repository
public interface QuestionDao {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title,content,user_id,created_time,comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELDS,") Values(#{title},#{content},#{userId},#{createdTime},#{commentCount})"})
    public int addQuestion(Question question);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where id=#{id}"})
    public Question getQuestionById(int id);

    @Update({"update ",TABLE_NAME," set content=#{content} where id=#{id}"})
    public void updateContent(Question question);

    @Update({"update ",TABLE_NAME," set comment_count=#{commentCount} where id=#{id}"})
    public int updateCommentCount(@Param("commentCount") int commentCount,
                                   @Param("id") int id);

    public List<Question> getLatestQuesList(@Param("userId") int id,
                                            @Param("offset") int offset,
                                            @Param("limit") int limit);


}
