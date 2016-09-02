package com.ChrisSun.service.impl;

import com.ChrisSun.dao.MessageDao;
import com.ChrisSun.model.Message;
import com.ChrisSun.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chris on 2016/8/30.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public int addMessage(Message message) {
        return messageDao.addMessage(message);
    }

    @Override
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    @Override
    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.getConversationList(userId, offset, limit);
    }

    @Override
    public int getUnreadMsgCount(int userId, String conversationId) {
        return messageDao.getUnreadMsgCount(userId,conversationId);
    }

    @Override
    public void updateHasReadStatus(int id, int hasRead) {
        messageDao.updateHasReadStatus(id,hasRead);
    }
}
