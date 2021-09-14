package com.sos.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.blog.model.LoggedInUser;
import com.sos.blog.service.UserService;

@RestController
@RequestMapping("blog-user")
public class UserController 
{
	private static final Logger logger =LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping(value = "/login", produces = "application/json")
	public LoggedInUser login(@RequestHeader String username, @RequestHeader String password)
	{
		logger.info(username + "is logging in.");
		LoggedInUser user = userService.login(username, password);
		
		logger.info(username + " " + user.getResponseMessage());
		
		return user;
	}

	@GetMapping(value = "/logout")
	public void logout(@RequestHeader String username)
	{
		logger.info(username + "is logged out.");
		userService.logout(username);
	}
}
