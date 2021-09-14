package com.sos.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.blog.cache.UserCache;
import com.sos.blog.entity.CommentSecurity;
import com.sos.blog.model.BlogConverter;
import com.sos.blog.model.CommentModel;
import com.sos.blog.model.KeyValue;
import com.sos.blog.model.ResponseCode;
import com.sos.blog.model.ReturnValue;
import com.sos.blog.service.BlogService;
import com.sos.blog.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserCache userCache;
	
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ReturnValue <List<CommentModel>> create(@RequestBody CommentModel model, @RequestHeader String username, @RequestHeader String token)
	{
		Long blogId = model.getBlogId();
		Byte commentSecurity = this.blogService.getCommentSecurity(blogId);
		
		ReturnValue <List<CommentModel>> returnValue = new ReturnValue <List<CommentModel>> ();
		
		if(CommentSecurity.USER.getValue() <= Byte.toUnsignedInt(commentSecurity))
		{
			if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
			{
				this.userCache.keepAlive(username);
				returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
				
				List<CommentModel> commentModel = this.commentService.createNewComment(BlogConverter.CommentModelToEntity(model));
				returnValue.setValue(commentModel);
			}
			else
			{
				returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			}
		}
		
		return returnValue;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value="/list/by-blogid/{blogid}", produces = "application/json")
	public ReturnValue <List<CommentModel>> listByBlogId(@PathVariable Long blogid, @RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <List<CommentModel>> returnValue = new ReturnValue <List<CommentModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			List<CommentModel> commentList = this.commentService.listByBlogId(blogid);
			returnValue.setValue(commentList);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value="/list/security-model", produces = "application/json")
	public List <KeyValue<String,Integer>> getCommentSecurity()
	{
		CommentSecurity[] list = CommentSecurity.values();
		
		List <KeyValue<String,Integer>> keyValues = Arrays.asList(list).stream().map(item -> new KeyValue <String, Integer> (item.getName(), item.getValue())).
		collect(Collectors.toList());
		
		return keyValues;
	}
	
}
