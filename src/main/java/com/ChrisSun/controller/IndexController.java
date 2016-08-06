package com.ChrisSun.controller;

import com.ChrisSun.model.Question;
import com.ChrisSun.model.ViewObject;
import com.ChrisSun.service.QuestionService;
import com.ChrisSun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2016/7/25.
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model){
        List<ViewObject> vos = getQuestions(0,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }
    @RequestMapping(path = "/user/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("id") int id){
        model.addAttribute("vos",getQuestions(id,0,10));
        return "index";
    }
    private List<ViewObject> getQuestions(int id, int offset, int limit){
        List<ViewObject> vos = new ArrayList<ViewObject>();
        List<Question> questionList = questionService.getLatestQuesList(id,offset,limit);
        for (Question q:questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question",q);
            vo.set("user",userService.getUserById(q.getUserId()));
            vos.add(vo);
        }
        return vos;
    }









    /*JSONObject 实现往前台传值渲染*/
   /* @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model){
        List<JSONObject> vos = getQuestions(0,0,10);
        model.addAttribute("vos",vos);
        return "index";
    }
    @RequestMapping(path = "/user/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("id") int id){
        model.addAttribute("vos",getQuestions(id,0,10));
        return "index";
    }
    private List<JSONObject> getQuestions(int id, int offset, int limit){
        List<JSONObject> vos = new ArrayList<JSONObject>();
        List<Question> questionList = questionService.getLatestQuesList(id,offset,limit);
        for (Question q:questionList) {
            JSONObject vo = new JSONObject();
            vo.put("question",q);
            vo.put("user",userService.getUserById(q.getUserId()));
            vos.add(vo);
        }
        return vos;
    }*/
}
