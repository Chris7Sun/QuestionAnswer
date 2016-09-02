package com.ChrisSun.service;

import com.ChrisSun.model.Message;

import java.util.List;

/**
 * Created by Chris on 2016/8/30.
 */
public interface MessageService {
    int addMessage(Message message);
    List<Message> getConversationDetail(String conversationId, int offset, int limit);
    List<Message> getConversationList(int userId, int offset, int limit);
    int getUnreadMsgCount(int userId, String conversationId);
    void updateHasReadStatus(int id, int hasRead);
}
