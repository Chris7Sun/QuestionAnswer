package com.ChrisSun.async.handler;

import com.ChrisSun.async.EventHandler;
import com.ChrisSun.async.EventModel;
import com.ChrisSun.async.EventType;
import com.ChrisSun.model.Message;
import com.ChrisSun.model.User;
import com.ChrisSun.service.MessageService;
import com.ChrisSun.service.UserService;
import com.ChrisSun.util.QaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/9/7.
 */
@Component
public class LikeHandler implements EventHandler {
    private Logger logger = LoggerFactory.getLogger(LikeHandler.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {

        int eventOwnerId = model.getEntityOwnerId();
        int actorId = model.getActorId();
        if (eventOwnerId == actorId)
            return;

        User actorUser = userService.getUserById(actorId);

        Message message = new Message();
        message.setFromId(QaUtils.SYSTEM_USERID);
        message.setToId(eventOwnerId);
        message.setCreatedDate(new Date());
        message.setContent("用户" + actorUser.getName() + "赞了你的评论：" +
                "http://127.0.0.1:8080/question/" + model.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
