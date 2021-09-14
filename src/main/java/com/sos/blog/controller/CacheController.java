package com.sos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.blog.cache.UserCache;

@RestController
@RequestMapping("cache")
public class CacheController {

	@Autowired
	private UserCache userCache;
	
	@GetMapping(value = "/keep-alive")
	public void keepSessionTokenAlive(@RequestHeader("username") String username)
	{
		this.userCache.keepAlive(username);
	}
}
