package com.ChrisSun.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 2016/9/6.
 */
public class EventModel {//事件记录的载体
    private EventType type;//事件类型
    private int actorId;//触发者
    private String entityType;//触发的记录类型
    private int entityId;//触发的记录id
    private int entityOwnerId;//触发的记录的创建者

    private Map<String, String> exts = new HashMap<>();//扩展字段

    public EventModel(){}

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(String key, String value) {
        exts.put(key, value);
        return this;
    }
    public String getExts(String key) {
        return exts.get(key);
    }

    public void EventModel(Map<String, String> exts) {
        this.exts = exts;
    }
}
