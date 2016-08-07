package com.ChrisSun.service;

import com.ChrisSun.dao.LoginTicketDao;
import com.ChrisSun.dao.UserDao;
import com.ChrisSun.model.LoginTicket;
import com.ChrisSun.model.User;
import com.ChrisSun.util.QaUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Chris on 2016/7/25.
 */
@Service
public class UserService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    public void addUser(User user){
        userDao.addUser(user);
    }
    public User getUserById(int id){
        return userDao.getUserById(id);
    }
    public void updatePassword(User user){
        userDao.updatePassword(user);
    }
    public List<User> selectByUserIdAndOffset(int id, int offset, int limit){
        return userDao.selectByUserIdAndOffset(id,offset,limit);
    }
    public Map<String,String> register(String userName,String password){
        Map<String,String> map = new HashMap<String,String>();
        if (StringUtils.isBlank(userName)){
            map.put("msg","用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空!");
            return map;
        }
        User u = userDao.getUserByName(userName);
        if (u != null){
            map.put("msg","用户名已经存在");
            return map;
        }
        u = new User();
        u.setName(userName);
        u.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        u.setSalt(UUID.randomUUID().toString().substring(0,5));
        u.setPassword(QaUtils.MD5(password + u.getSalt()));
        userDao.addUser(u);

        String ticket = addTicket(u.getId());//用户注册成功之后下发ticket，使之自动登录
        map.put("ticket",ticket);

        return map;
    }

    public Map<String,String> login(String userName, String password) {
        Map<String,String> map = new HashMap<String,String>();
        if (StringUtils.isBlank(userName)){
            map.put("msg","用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg","密码不能为空!");
            return map;
        }
        User u = userDao.getUserByName(userName);
        if (u == null){
            map.put("msg","用户名不存在！");
            return map;
        }
        if (!QaUtils.MD5(password + u.getSalt()).equals(u.getPassword())){
            map.put("msg","用户密码错误！");
            return map;
        }
        //单点登录
        String ifTicketExist = loginTicketDao.selectTicketByUseridAndStatus(u.getId(),0);
        if (ifTicketExist != null){
            loginTicketDao.updateStatus(ifTicketExist,1);
            String ticket = addTicket(u.getId());//用户登录成功后，下发ticket
            map.put("ticket",ticket);
        }else {
            String ticket = addTicket(u.getId());//用户登录成功后，下发ticket
            map.put("ticket",ticket);
        }

        return map;
    }

    public String addTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setStatus(0);
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH,curr.get(Calendar.MONTH) + 1);
        Date now = curr.getTime();
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        ticket.setExpired(now);
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket,1);
    }
}
