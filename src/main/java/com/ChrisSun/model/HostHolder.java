package com.ChrisSun.model;

import org.springframework.stereotype.Component;

/**
 * Created by Chris on 2016/8/6.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }
    public void clear(){
        users.remove();
    }
}
