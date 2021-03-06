package com.ChrisSun.controller;

import com.ChrisSun.async.EventModel;
import com.ChrisSun.async.EventProducer;
import com.ChrisSun.async.EventType;
import com.ChrisSun.model.Comment;
import com.ChrisSun.model.EntityType;
import com.ChrisSun.model.HostHolder;
import com.ChrisSun.service.CommentService;
import com.ChrisSun.service.LikeService;
import com.ChrisSun.util.QaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Chris on 2016/8/31.
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        try {
            if (hostHolder.getUser() == null)
                return QaUtils.getJSONString(999);

            Comment comment = commentService.getCommentById(commentId);
            eventProducer.fireEvent(new EventModel(EventType.LIKE)
                    .setEntityType(String.valueOf(EntityType.COMMENT_TYPE))
                    .setEntityId(commentId)
                    .setActorId(hostHolder.getUser().getId())
                    .setEntityOwnerId(comment.getUserId())
                    .setExts("questionId", String.valueOf(comment.getEntityId())));

            long likeCount = likeService.like(hostHolder.getUser().getId(),String.valueOf(EntityType.COMMENT_TYPE) ,commentId);
            return QaUtils.getJSONString(0, String.valueOf(likeCount));
        }catch (Exception e) {
            logger.error("failed to like" + e);
        }
        return "1";
    }
    @RequestMapping(path = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        try {
            if (hostHolder.getUser() == null)
                return QaUtils.getJSONString(999);
            long likeCount = likeService.dislike(hostHolder.getUser().getId(),String.valueOf(EntityType.COMMENT_TYPE) ,commentId);
            return QaUtils.getJSONString(0, String.valueOf(likeCount));
        }catch (Exception e) {
            logger.error("failed to like" + e);
        }
        return "1";
    }
}
