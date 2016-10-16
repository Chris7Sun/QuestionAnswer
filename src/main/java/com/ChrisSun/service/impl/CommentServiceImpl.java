package com.ChrisSun.service.impl;

import com.ChrisSun.dao.CommentDao;
import com.ChrisSun.model.Comment;
import com.ChrisSun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public int getCommentCount(int entityId, String entityType) {
        return commentDao.getCommentCount(entityId,entityType);
    }

    @Override
    public List<Comment> getCommentByEntity(int entityId, String entityType) {
        return commentDao.getByEntityIdAndType(entityId,entityType);
    }

    @Override
    public void deleteComment(int id) {
        commentDao.updateStatusById(id, 1);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentDao.getCommentById(id);
    }
}
