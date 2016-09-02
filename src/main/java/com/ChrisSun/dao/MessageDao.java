package com.ChrisSun.dao;

import com.ChrisSun.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */
@Mapper
@Repository
public interface MessageDao {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = "from_id,to_id,content,has_read,conversation_id,created_date";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME , " (", INSERT_FIELDS, ") values(#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME , " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select ",INSERT_FIELDS,",count(id) as id from (select * from", TABLE_NAME , "order by created_date desc) m" +
            " where from_id=#{userId} or to_id=#{userId} group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where has_read=0 and to_id=#{userId} " +
            "and conversation_id=#{conversationId}"})
    int getUnreadMsgCount(@Param("userId") int userId,
                          @Param("conversationId") String conversationId);

    @Update({"update ",TABLE_NAME," set has_read=#{hasRead} where id=#{id}"})
    void updateHasReadStatus(@Param("id") int id,
                             @Param("hasRead") int hasRead);

}
