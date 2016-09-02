package com.ChrisSun.service;

import com.ChrisSun.model.LoginTicket;
import com.ChrisSun.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 2016/8/28.
 */
public interface UserService {
    void addUser(User user);
    User getUserById(int id);
    void updatePassword(User user);
    List<User> selectByUserIdAndOffset(int id, int offset, int limit);
    Map<String,String> register(String userName, String password);
    User getUserByName(String name);
    Map<String,String> login(String userName, String password);

    String addTicket(int userId);
    LoginTicket selectByTicket(String ticket);
    void logout(String ticket);
}
