package com.ChrisSun.service.impl;

import com.ChrisSun.service.LikeService;
import com.ChrisSun.util.JedisAdapter;
import com.ChrisSun.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chris on 2016/8/31.
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public long like(int userId, String entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));//add the user to like set

        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));//remove the user from dislike set
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public long dislike(int userId, String entityType, int entityId) {
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));//add the user to dislike set

        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));//remove the user from like set
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public int getLikeStatus(int userId, String entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId)) == true)
            return 1;

        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        return jedisAdapter.sismember(dislikeKey, String.valueOf(userId)) == true ? -1 : 0;
    }

    @Override
    public long getLikeCount(String entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);

        return jedisAdapter.scard(likeKey);
    }
}
