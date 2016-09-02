package com.ChrisSun.model;

import java.util.Date;

/**
 * Created by Chris on 2016/7/30.
 */
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private String conversationId;
    private int hasRead;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        if (fromId < toId){
            return fromId + "_" + toId;
        }else {
            return toId + "_" + fromId;
        }
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
