package com.ChrisSun.async;

import com.ChrisSun.util.JedisAdapter;
import com.ChrisSun.util.RedisKeyUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chris on 2016/9/6.
 */
@Service
public class EventProducer {
    @Autowired
    private JedisAdapter jedisAdapter;

    //将事件推到队列中
    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
