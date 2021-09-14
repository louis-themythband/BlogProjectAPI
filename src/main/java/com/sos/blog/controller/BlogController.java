package com.sos.blog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.blog.cache.UserCache;
import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.entity.Status;
import com.sos.blog.entity.Visibility;
import com.sos.blog.model.BlogComposite;
import com.sos.blog.model.BlogModel;
import com.sos.blog.model.CategoryModel;
import com.sos.blog.model.KeyValue;
import com.sos.blog.model.ResponseCode;
import com.sos.blog.model.ReturnValue;
import com.sos.blog.model.SimpleBlogModel;
import com.sos.blog.service.BlogService;
import com.sos.blog.service.CategoryService;

@RestController
@RequestMapping("blog")
public class BlogController {
	
	private static final Logger logger =LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserCache userCache;
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list-published/by-user/{blogOwner}", produces = "application/json")
	public ReturnValue <List<SimpleBlogModel>> getBlogTitles(@PathVariable("blogOwner") String blogOwner, @RequestHeader String visibility, 
	@RequestHeader("username") String username, @RequestHeader String token)
	{
		logger.info("blog/list-published/by-user/{username} is called by " + username);
		
		ReturnValue <List<SimpleBlogModel>> returnValue = new ReturnValue <List<SimpleBlogModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			logger.debug(username + " is logged in to call blog/list-published/by-user/{username}");
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			logger.debug(username + " is not logged in to call blog/list-published/by-user/{username}");
		}
		
		List<SimpleBlogModel> blogList = blogService.getBlogsByUsername(blogOwner, Visibility.valueOf(visibility), username);
		returnValue.setValue(blogList);
		logger.debug(blogList.size() + " records returned for getBlogTitles(" + blogOwner + ") visibility(" + visibility + ")");
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search/by-user/{blogOwner}", produces = "application/json")
	public ReturnValue <List<SimpleBlogModel>> searchtByUsername(@PathVariable("blogOwner") String blogOwner, 
	@RequestHeader("username") String username, @RequestHeader String token, @RequestHeader String visibility, @RequestBody String term)
	{
		logger.info("blog/search/by-user/{username} is called by " + username);
		
		ReturnValue <List<SimpleBlogModel>> returnValue = new ReturnValue <List<SimpleBlogModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			logger.debug(username + " is logged in to call blog/search/by-user/{username}");
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			logger.debug(username + " is not logged in to call blog/search/by-user/{username}");
		}
		
		List<SimpleBlogModel> simpleBlogList = blogService.searchByUsername(blogOwner, term, Visibility.valueOf(visibility), username);
		returnValue.setValue(simpleBlogList);
		
		logger.debug(simpleBlogList.size() + " records returned for searchByUsername(" + blogOwner + ") visibility(" + visibility + ")");
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list-all/by-user/{blogOwner}", produces = "application/json")
	public ReturnValue <List<SimpleBlogModel>> getAllBlogsByUsername(@PathVariable("blogOwner") String blogOwner, 
	@RequestHeader("username") String username, @RequestHeader String token)
	{
		logger.info("blog/list-all/by-user/{username} is called by " + username);
		
		ReturnValue <List<SimpleBlogModel>> returnValue = new ReturnValue <List<SimpleBlogModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			List<SimpleBlogModel> simpleBlogList = blogService.getAllBlogsByUsername(blogOwner);
			returnValue.setValue(simpleBlogList);
			
			logger.info(username + " is logged in to call blog/list-all/by-user/{username}");
			logger.info(simpleBlogList.size() + " records getAllBlogsByUsername(" + blogOwner + ")");
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			logger.info(username + " is not logged in to call blog/list-all/by-user/{username}");
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/id/{blogid}", produces = "application/json")
	public ReturnValue <BlogModel> getBlog(@PathVariable("blogid") Long blogId, @RequestHeader String username, @RequestHeader String token)
	{
		logger.info("blog/id/{"+ blogId + "} is called by " + username);
		
		ReturnValue <BlogModel> returnValue = new ReturnValue <BlogModel> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			logger.info(username + " is logged in to call blog/id/{"+ blogId + "}");
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			logger.info(username + " is not logged in to call blog/id/{"+ blogId + "}");
		}
		
		BlogModel blogModel = blogService.getBlog(blogId, username);
		returnValue.setValue(blogModel);
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/all", produces ="application/json")
	public ReturnValue <List<SimpleBlogModel>> getAllBlogs(@RequestHeader String visibility, @RequestHeader(required = false) String username, 
	@RequestHeader(required = false) String token)
	{
		logger.info("blog/all is called by " + username);
		
		ReturnValue <List<SimpleBlogModel>> returnValue = new ReturnValue <List<SimpleBlogModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username) != null && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			logger.info(username + " is logged in to call blog/all");
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			logger.info(username + " is not logged in to call blog/all");
		}
		
		List<SimpleBlogModel> blogList = blogService.getAllBlogs(Visibility.valueOf(visibility));
		returnValue.setValue(blogList);
		logger.info(blogList.size() + " records for getAllBlogs visibility["+visibility+"]");
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value="/search/by-term", consumes = "application/json", produces = "application/json")
	public ReturnValue <List<SimpleBlogModel>> searchByTerm(@RequestBody String term, @RequestHeader String visibility, 
	@RequestHeader(required = false) String username, @RequestHeader String token)
	{
		ReturnValue <List<SimpleBlogModel>> returnValue = new ReturnValue <List<SimpleBlogModel>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		List<SimpleBlogModel> blogList =  this.blogService.search(term, Visibility.valueOf(visibility), username);
		returnValue.setValue(blogList);
		
		return returnValue;
	}
	
	
	@CrossOrigin(origins = "*")
	@PutMapping(value="/like/{blogid}", produces = "application/json")
	public ReturnValue <String> increaseLike(@PathVariable("blogid") Long blogId, @RequestHeader String username, 
	@RequestHeader String token) 
	{
		ReturnValue <String> returnValue = new ReturnValue <String> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			String value = blogService.increaseLike(blogId).toString();
			returnValue.setValue(value);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping(value="/dislike/{blogid}", produces = "application/json")
	public ReturnValue <String> increaseDislike(@PathVariable("blogid") Long blogId, @RequestHeader String username, @RequestHeader String token) 
	{
		ReturnValue <String> returnValue = new ReturnValue <String> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			String value = blogService.increaseDislike(blogId).toString();
			returnValue.setValue(value);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value="/create", consumes="application/json", produces="application/json")
	public ReturnValue <String> createNewBlog(@RequestBody BlogComposite blogComposite, @RequestHeader String username, 
	@RequestHeader String token)
	{
		ReturnValue <String> returnValue = new ReturnValue <String> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			BlogModel blog = blogComposite.getBlog();
			List<String> categoryList = blogComposite.getCategoryList();
			
			blog.setBlogId(null);
			String value =  blogService.createNewBlog(blog, categoryList).toString();
			returnValue.setValue(value);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/update/active/{blogId}", produces = "application/json")
	public ReturnValue <Boolean> updateBlogActive(@PathVariable("blogid")Long blogId, @RequestBody Boolean active, 
	@RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <Boolean> returnValue = new ReturnValue <Boolean> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			this.blogService.updateBlogActive(blogId, active, username);
			returnValue.setValue(true);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			returnValue.setValue(false);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/update/status/{blogId}", produces = "application/json")
	public ReturnValue <Boolean> updateBlogStatus(@PathVariable("blogid")Long blogId, @RequestBody Status status, 
	@RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <Boolean> returnValue = new ReturnValue <Boolean> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			this.blogService.updateBlogStatus(blogId, status, username);
			returnValue.setValue(true);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			returnValue.setValue(false);
		}
		
		return returnValue;
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = "/update/visibility/{blogId}", produces = "application/json")
	public ReturnValue <Boolean> updateBlogVisibility(@PathVariable("blogid")Long blogId, 
	@RequestBody Visibility visibility, @RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <Boolean> returnValue = new ReturnValue <Boolean> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			this.blogService.updateBlogVisibility(blogId, visibility, username);
			returnValue.setValue(true);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			returnValue.setValue(false);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/category/remove", produces = "application/json")
	public ReturnValue <Boolean> removeCategory(@RequestHeader("categoryId") Long categoryId, 
	@RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <Boolean> returnValue = new ReturnValue <Boolean> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			if(this.categoryService.containsCategory(categoryId))
			{
				categoryService.removeCategory(categoryId, username);
				returnValue.setValue(true);
			}
			else
			{
				returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_FOUR);
				returnValue.setValue(false);
			}
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			returnValue.setValue(false);
		}

		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/update/information", consumes = "application/json", produces = "application/json")
	public ReturnValue <BlogModel> updateBlog(@RequestBody BlogModel blog, @RequestHeader String username, 
	@RequestHeader String token)
	{
		ReturnValue <BlogModel> returnValue = new ReturnValue <BlogModel> ();
		
		History history = new History();
		history.setHistoryType(HistoryType.UPDATE);
		history.setTableName("Blog");
		history.setTableId(blog.getBlogId());
		history.setColumn("blog_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			if(blog.getBlogId() != null)
			{
				try 
				{
					BlogModel blogModel = this.blogService.updateBlog(blog, username);
					returnValue.setValue(blogModel);
					returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
				} 
				catch (Exception e) 
				{
					returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_FIVE);
				}
			}
			else
			{
				returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_FOUR);
			}
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/{blogid}/category/add", consumes = "application/json", produces = "application/json" )
	public ReturnValue <CategoryModel> addCategory(@PathVariable("blogid") Long blogid, @RequestBody String categoryText, @RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <CategoryModel> returnValue = new ReturnValue <CategoryModel> ();
		
		
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			CategoryModel model = this.categoryService.addCategory(blogid, categoryText, username);
			returnValue.setValue(model);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/delete", produces = "application/json")
	public ReturnValue <Boolean> deleteBlog(@RequestHeader("blogId")Long blogId, @RequestHeader String username, 
	@RequestHeader String token)
	{
		ReturnValue <Boolean> returnValue = new ReturnValue <Boolean> ();
		
		
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
			this.blogService.deleteBlog(blogId, username);
			returnValue.setValue(true);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
			returnValue.setValue(false);
		}
		
		return returnValue;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/visibility/list", produces = "application/json")
	public ReturnValue <List<KeyValue<String,String>>> getVisibilityList(@RequestHeader String username, @RequestHeader String token)
	{
		ReturnValue <List<KeyValue<String,String>>> returnValue = new ReturnValue <List<KeyValue<String,String>>> ();
		
		if(this.userCache.hasCache(username) && this.userCache.getCache(username).equals(token))
		{
			this.userCache.keepAlive(username);
			returnValue.setResponseCode(ResponseCode.TWO_HUNDRED_TWO);
		}
		else
		{
			returnValue.setResponseCode(ResponseCode.THREE_HUNDRED_THREE);
		}
		
		Visibility [] visibilityList = Visibility.values();
		
		List <KeyValue<String,String>> list = Arrays.asList(visibilityList).stream().map(
				item -> new KeyValue<String,String>(item.name(), item.name())).collect(Collectors.toList());
		returnValue.setValue(list);
		
		return returnValue;
	}

}
