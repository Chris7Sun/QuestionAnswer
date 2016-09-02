package com.ChrisSun.service;

/**
 * Created by Chris on 2016/8/31.
 */
public interface LikeService {
    long like(int userId, String entityType, int entityId);
    long dislike(int userId, String entityType, int entityId);
    int getLikeStatus(int userId, String entityType, int entityId);
    long getLikeCount(String entityType, int entityId);
}
