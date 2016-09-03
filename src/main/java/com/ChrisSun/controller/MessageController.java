package com.ChrisSun.controller;

import com.ChrisSun.model.HostHolder;
import com.ChrisSun.model.Message;
import com.ChrisSun.model.User;
import com.ChrisSun.model.ViewObject;
import com.ChrisSun.service.MessageService;
import com.ChrisSun.service.UserService;
import com.ChrisSun.service.impl.SensitiveService;
import com.ChrisSun.util.QaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/8/30.
 */
@Controller
@RequestMapping("/msg")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private static final int OFFSET = 0;
    private static final int LIMIT = 10;

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toUserName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null)
                return QaUtils.getJSONString(999,"用户未登录！");
            User user = userService.getUserByName(toUserName);
            if (user == null)
                return QaUtils.getJSONString(1, "接收用户不存在！");

            Message message = new Message();
            //TODO add 敏感词过滤
            content = HtmlUtils.htmlEscape(content);
            message.setContent(sensitiveService.filter(content));
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setCreatedDate(new Date());
//            message.setConversationId();
            messageService.addMessage(message);
            return QaUtils.getJSONString(0);
        }catch (Exception e){
            logger.error("failed to add message!",e);
            return QaUtils.getJSONString(1, "发信失败！");
        }
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String getCoversationList(Model model){
        try {
            if (hostHolder.getUser() == null)
                return "redirect:/reglogin";

            List<Message> messageList = messageService.getConversationList(hostHolder.getUser().getId(),OFFSET, LIMIT);
            List<ViewObject> vos = new ArrayList<>();
            for (Message m:messageList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation",m);
                int targetId = m.getFromId() == hostHolder.getUser().getId() ? m.getToId() : m.getFromId();
                vo.set("user",userService.getUserById(targetId));
                vo.set("unread",messageService.getUnreadMsgCount(hostHolder.getUser().getId(),m.getConversationId()));
                vos.add(vo);
            }
            model.addAttribute("conversations", vos);
            return "letter";
        }catch (Exception e){
            logger.error("failed to get conversation list!" + e);
            return "/";
        }
    }

    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId){
        try {
            if (hostHolder.getUser() == null)
                return "/";

            List<Message> messageList = messageService.getConversationDetail(conversationId,OFFSET,LIMIT);
            List<ViewObject> vos = new ArrayList<>();
            for (Message m:messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", m);
                User user = userService.getUserById(m.getFromId());
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                vos.add(vo);
                messageService.updateHasReadStatus(m.getId(),1);
            }
            model.addAttribute("messages",vos);
            return "letterDetail";
        }catch (Exception e){
            logger.error("failed to get conversation detail!" + e);
            return "/";
        }
    }
}
