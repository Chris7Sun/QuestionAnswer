package com.ChrisSun.controller;

import com.ChrisSun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Chris on 2016/7/30.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/reg",method = RequestMethod.POST)
    public String reg(Model model,
                      @RequestParam("username") String userName,
                      @RequestParam("password") String password,
                      HttpServletResponse response){
        try {
            Map<String, String> map = userService.register(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        }catch (Exception e){
            logger.error("注册异常：" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("username") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remeberme", defaultValue = "false", required = false) boolean remeberme,
                        HttpServletResponse response){
        try {
            Map<String, String> map = userService.login(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("登录异常：" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/reglogin",method = RequestMethod.GET)
    public String reg(Model model){
        return "login";
    }
}