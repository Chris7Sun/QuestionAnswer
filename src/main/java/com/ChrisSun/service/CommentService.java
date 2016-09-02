package com.ChrisSun.service;

import com.ChrisSun.model.Comment;

import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */

public interface CommentService {
    int addComment(Comment comment);
    int getCommentCount(int entityId, String entityType);
    List<Comment> getCommentByEntity(int entityId,String entityType);
    void deleteComment(int id);
}
