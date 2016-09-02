package com.ChrisSun.dao;

import com.ChrisSun.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */
@Mapper
@Repository
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "content,user_id,created_date,entity_id,entity_type,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME , " (", INSERT_FIELDS, ") values(#{content},#{userId},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME , " where entity_id=#{entityId} and entity_type=#{entityType}"})
    List<Comment> getByEntityIdAndType(@Param("entityId") int entityId,
                                       @Param("entityType") String entityType);

    @Update({"update ",TABLE_NAME, " set status=#{status} where id=#{id}"})
    void updateStatusById(@Param("id") int id,
                          @Param("status") int status);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") String entityType);
}
