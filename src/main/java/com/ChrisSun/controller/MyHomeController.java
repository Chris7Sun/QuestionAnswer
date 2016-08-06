package com.ChrisSun.controller;

import com.ChrisSun.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/7/24.
 */
public class MyHomeController {
    @RequestMapping({"/","/index","chris"})
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam("name") String userName,
                          @RequestParam(value="age",defaultValue="18",required = false) int age){
//        return "this is a " + groupId + ",id is: " + userId + "<br>" + "userName:" + userName + " age:" +age;
        return String.format("{%s},{%d},{%s},{%d}",groupId,userId,userName,age);
    }

    @RequestMapping("/user/profile")
    @ResponseBody
    public User userPro() {
        Date date = new Date();
        return new User();
    }

    @RequestMapping("/user")
    @ResponseBody
    public Model demoModel(Model model){
        model.addAttribute("name", "张三");
        model.addAttribute("gender", "male" );
        return model;
    }

    @RequestMapping("/vm")
    public String template(Model model){
        model.addAttribute("name", "张三");
        model.addAttribute("gender", "male" );
        List colors = new ArrayList(Arrays.asList(new String[] {"green","red","blue"}));
        model.addAttribute("colors",colors);
        return "home";
    }
}
