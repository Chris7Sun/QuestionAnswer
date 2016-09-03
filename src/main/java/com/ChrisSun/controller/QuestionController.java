package com.ChrisSun.controller;

import com.ChrisSun.model.*;
import com.ChrisSun.service.CommentService;
import com.ChrisSun.service.LikeService;
import com.ChrisSun.service.QuestionService;
import com.ChrisSun.service.UserService;
import com.ChrisSun.util.QaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/8/28.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            if (hostHolder.getUser() != null){
                question.setUserId(hostHolder.getUser().getId());
            }else {
//                question.setUserId(QaUtils.ANONYMOUS_USERID);
                return QaUtils.getJSONString(999);
            }
            question.setCreatedTime(new Date());

            if (questionService.addQuestion(question) > 0){
                return QaUtils.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("failed to add question title:{}",title + e);
        }
        return QaUtils.getJSONString(1,"failed");
    }

    @RequestMapping("/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        try {
            Question question = questionService.getQuesById(qid);
            List<Comment> commentList = commentService.getCommentByEntity(qid, String.valueOf(EntityType.QUESTION_TYPE));
            List<ViewObject> vos = new ArrayList<>();
            for (Comment comment:commentList) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                if (hostHolder.getUser() == null){
                    vo.set("liked", 0);
                }else {
                    vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), String.valueOf(EntityType.COMMENT_TYPE), comment.getId()));
                }
                vo.set("user", userService.getUserById(comment.getUserId()));
                vo.set("likeCount", likeService.getLikeCount(String.valueOf(EntityType.COMMENT_TYPE), comment.getId()));
                vos.add(vo);
            }
            model.addAttribute("question", question);
            model.addAttribute("comments", vos);
        }catch (Exception e){
            logger.error("failed to get question detail!" + e);
        }
        return "detail";
    }
}
