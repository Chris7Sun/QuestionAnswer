package com.ChrisSun.controller;

import com.ChrisSun.model.Comment;
import com.ChrisSun.model.EntityType;
import com.ChrisSun.model.HostHolder;
import com.ChrisSun.service.CommentService;
import com.ChrisSun.service.QuestionService;
import com.ChrisSun.util.QaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Chris on 2016/8/28.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        try {
            Comment comment = new Comment();
            //TODO content过滤
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(String.valueOf(EntityType.QUESTION_TYPE));
            comment.setCreatedDate(new Date());
            if (hostHolder.getUser() != null){
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                comment.setUserId(QaUtils.ANONYMOUS_USERID);
                // return "redirect:/reglogin";
            }
            comment.setStatus(0);

            commentService.addComment(comment);
            //更新Question中的评论数量
            int commentCount = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(questionId,commentCount);
        }catch (Exception e){
            logger.error("add comment error",e);
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }

}
