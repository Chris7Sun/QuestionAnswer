package com.ChrisSun.async;

import java.util.List;

/**
 * Created by Chris on 2016/9/7.
 */
public interface EventHandler {
    /**
     handle this EventModel
    */
    void doHandle(EventModel model);

    /**
     let others know what EventType I support to handle
     */
    List<EventType> getSupportEventTypes();
}

