package com.ChrisSun.util;

/**
 * Created by Chris on 2016/8/31.
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String BIZ_LIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";
    private static final String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    public static String getLikeKey(String entityType, int entityId){
        return BIZ_LIKE + SPLIT + entityType + SPLIT + String.valueOf(entityId);
    }
    public static String getDislikeKey(String entityType, int entityId){
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + String.valueOf(entityId);
    }
    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }
}
