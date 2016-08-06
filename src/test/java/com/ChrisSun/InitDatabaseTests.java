package com.ChrisSun;

import com.ChrisSun.dao.UserDao;
import com.ChrisSun.model.Question;
import com.ChrisSun.model.User;
import com.ChrisSun.service.QuestionService;
import com.ChrisSun.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QaApplication.class)
@WebAppConfiguration
public class InitDatabaseTests {
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserDao userDao;

	@Test
	public void contextLoads() {
//		selectByUserIdAndOffset();
//		updatePwd(6);
		addQuestion();
	}
	public void addUser(){
		Random random = new Random();
		for (int i = 0; i < 11; ++i){
			User u = new User();
			u.setName("USER" + i);
			u.setPassword("");
			u.setSalt("");
			u.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			userService.addUser(u);
		}
	}
	public void updatePwd(int id){
		User u = userService.getUserById(id);
		u.setPassword("123456");
		userService.updatePassword(u);
	}
	public void selectByUserIdAndOffset(){
		List<User> list = new ArrayList<User>();
		list = userService.selectByUserIdAndOffset(0,0,5);
		System.out.println(list.toString());
	}
	public void addQuestion(){
		for (int i = 0; i < 11; ++i){
			Question q = new Question();
			q.setTitle("TITLE" + i);
			q.setUserId(i + 1);
			q.setCommentCount(i * i + 50);
			q.setContent(i + "大家一起来讨论问题啦！");
			Date date = new Date();
			date.setTime(date.getTime() + 1000*3600*24*i);
			q.setCreatedTime(date);
			questionService.addQuestion(q);
		}
	}
}
