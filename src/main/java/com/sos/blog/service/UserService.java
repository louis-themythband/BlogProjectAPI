package com.sos.blog.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sos.blog.cache.UserCache;
import com.sos.blog.entity.BlogUser;
import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.model.BlogConverter;
import com.sos.blog.model.LoggedInUser;
import com.sos.blog.model.ResponseCode;
import com.sos.blog.model.UserModel;
import com.sos.blog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCache userCache;
	
	@Autowired
	private HistoryService historyService;
	
	
	@Transactional
	public LoggedInUser login(String userName, String password)
	{
		History history = new History();
		history.setHistoryType(HistoryType.LOGIN);
		history.setTableName("blog_user");
		history.setColumn("user_name");
		history.setUsername(userName);
		history.setCreatedDatetime(new Date());
		
		if(userName == null)
		{
			history.setMessage("username is null");
			historyService.createHistoryRecord(history);
			
			return new LoggedInUser(ResponseCode.ONE_HUNDRED_ONE.getCode(),
				ResponseCode.ONE_HUNDRED_ONE.getMessage() , null, null);
		}
		
		if(password == null)
		{
			history.setMessage("password is null");
			historyService.createHistoryRecord(history);
			
			return new LoggedInUser(ResponseCode.ONE_HUNDRED_TWO.getCode(),
				ResponseCode.ONE_HUNDRED_TWO.getMessage() , null, null);
		}
		
		BlogUser user = userRepository.getUserByUserName(userName);
		
		if(user != null)
		{
			if(password.contentEquals(user.getUserPassword()))
			{
				if(this.userCache.hasCache(user.getUserName()))
				{
					this.userCache.removeCache(user.getUserName());
				}
				
				this.userCache.addCache(user.getUserName());
				
				String guid = this.userCache.getCache(user.getUserName());
				
				UserModel userModel = BlogConverter.BlogUserEntityToModel(user);
				userModel.setToken(guid);
				
				history.setMessage("User " + userName +" successfully logged in");
				historyService.createHistoryRecord(history);
				
				return new LoggedInUser(ResponseCode.TWO_HUNDRED.getCode(), 
					ResponseCode.TWO_HUNDRED.getMessage(),userModel, guid);
			}
			else
			{
				history.setMessage("User " + userName +" unsuccessfully logged in");
				historyService.createHistoryRecord(history);
				
				return new LoggedInUser(ResponseCode.TWO_HUNDRED_ONE.getCode(),
					ResponseCode.TWO_HUNDRED_ONE.getMessage(), null, null);
			}
		}
		else
		{
			history.setMessage("User " + userName +" Not Found");
			historyService.createHistoryRecord(history);
			
			return new LoggedInUser(ResponseCode.ONE_HUNDRED.getCode(), 
				ResponseCode.ONE_HUNDRED.getMessage(), null, null);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void logout(String username)
	{
		
		
		if(this.userCache.hasCache(username))
		{
			Boolean removed = this.userCache.removeCache(username);
			
			if(removed)
			{
				History history = new History();
				history.setHistoryType(HistoryType.LOGOUT);
				history.setTableName("blog_user");
				history.setColumn("user_name");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setMessage("User " + username + " successfully logged out.");
				historyService.createHistoryRecord(history);
			}
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public Optional<BlogUser> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateUserActive(Long userId, Boolean active, String username)
	{
		Optional<BlogUser> optionalUser = userRepository.findById(userId);
		
		if(optionalUser.isPresent())
		{
			BlogUser user = optionalUser.get();
			
			History history = new History();
			history.setHistoryType(HistoryType.UPDATE);
			history.setTableName("blog_user");
			history.setColumn("active");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setOldValue(user.getActive().toString());
			history.setNewValue(active.toString());
			history.setMessage("User " + user.getUserName() + " set active = " + active);
			historyService.createHistoryRecord(history);
			
			user.setActive(active);
		}
		
		return;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public UserModel getUserModelById(Long userId)
	{
		Optional<BlogUser> optionalUser = userRepository.findById(userId);
		
		if(optionalUser.isPresent())
		{
			return BlogConverter.BlogUserEntityToModel(optionalUser.get());
		}
		else
		{
			return new UserModel();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public UserModel createNewUSer(UserModel model, String username)
	{
		BlogUser user = userRepository.saveAndFlush(BlogConverter.UserModelToEntity(model));
		model.setUserId(user.getUserId());
		
		History history = new History();
		history.setHistoryType(HistoryType.NEW);
		history.setTableName("blog_user");
		history.setColumn("user_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setNewValue(user.getUserId().toString());
		history.setMessage("User " + user.getUserName() + " with userId = " + user.getUserId() + " was created");
		historyService.createHistoryRecord(history);
		
		return model;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateUser(UserModel model, String username)
	{
		Optional<BlogUser> optionalUser = userRepository.findById(model.getUserId());
		
		if(optionalUser.isPresent())
		{
			BlogUser user = optionalUser.get();
			
			if(user.getActive() != model.getActive()) 
			{
				
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("active");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getActive().toString());
				history.setOldValue(user.getActive().toString());
				history.setMessage("User " + user.getUserName() + " active = " + user.getActive());
				historyService.createHistoryRecord(history);
				
				user.setActive(model.getActive());
			}
			
			if(user.getCanComment() != model.getCanComment())
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("can_comment");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getCanComment().toString());
				history.setOldValue(user.getCanComment().toString());
				history.setMessage("User " + user.getUserName() + " can comment = " + user.getCanComment());
				historyService.createHistoryRecord(history);
				
				user.setCanComment(model.getCanComment());
			}
			
			if(!user.getFirstName().equals(model.getFirstName()))
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("first_name");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getFirstName());
				history.setOldValue(user.getFirstName());
				history.setMessage("User " + user.getUserName() + " first name = " + user.getFirstName());
				historyService.createHistoryRecord(history);
				
				user.setFirstName(model.getFirstName());
			}
			
			if(!user.getLastName().equals(model.getLastName()))
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("last_name");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getLastName());
				history.setOldValue(user.getLastName());
				history.setMessage("User " + user.getUserName() + " last name = " + user.getLastName());
				historyService.createHistoryRecord(history);
				
				user.setLastName(model.getLastName());
			}

			if(user.getIsAdmin() != model.getIsAdmin())
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("is_admin");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getIsAdmin().toString());
				history.setOldValue(user.getIsAdmin().toString());
				history.setMessage("User " + user.getUserName() + " is admin = " + user.getIsAdmin());
				historyService.createHistoryRecord(history);
				user.setIsAdmin(model.getIsAdmin());
			}
			
			if(user.getCanBlog() != model.getCanBlog())
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("can_blog");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getCanBlog().toString());
				history.setOldValue(user.getCanBlog().toString());
				history.setMessage("User " + user.getUserName() + " can blog = " + user.getCanBlog());
				historyService.createHistoryRecord(history);
				
				user.setIsAdmin(model.getCanBlog());
			}
			
			if(!user.getUserEmail().equals(model.getUserEmail()))
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("user_email");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getUserEmail());
				history.setOldValue(user.getUserEmail());
				history.setMessage("User " + user.getUserName() + " user email = " + user.getUserEmail());
				historyService.createHistoryRecord(history);
				
				user.setUserEmail(model.getUserEmail());
			}
			
			if(!user.getUserName().equals(model.getUserName()))
			{
				History history = new History();
				history.setHistoryType(HistoryType.NEW);
				history.setTableName("blog_user");
				history.setColumn("user_name");
				history.setUsername(username);
				history.setCreatedDatetime(new Date());
				history.setNewValue(model.getUserName());
				history.setOldValue(user.getUserName());
				history.setMessage("User " + model.getUserName() + " user name = " + user.getUserName());
				historyService.createHistoryRecord(history);
				
				user.setUserName(model.getUserName());
			}
			
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public LoggedInUser changeUserPassword(Long userId, String password, String username)
	{
		Optional<BlogUser> optionalUser = userRepository.findById(userId);
		LoggedInUser loggedInUser;
		
		if(optionalUser.isPresent())
		{
			BlogUser user = optionalUser.get();
			
			// TODO implement password rules check
			
			// ****************************
			// Password rules check go here
			// ****************************
			
			user.setUserPassword(password);
			
			History history = new History();
			history.setHistoryType(HistoryType.NEW);
			history.setTableName("blog_user");
			history.setColumn("user_password");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("User " + user.getUserName() + " changed password.");
			historyService.createHistoryRecord(history);
			
			if(this.userCache.hasCache(user.getUserName()))
			{
				this.userCache.addCache(user.getUserName());
			}
			
			String guid = this.userCache.getCache(user.getUserName());
			
			loggedInUser = new LoggedInUser(ResponseCode.THREE_HUNDRED.getCode(), 
				ResponseCode.THREE_HUNDRED.getMessage(), BlogConverter.BlogUserEntityToModel(user), guid);
		}
		else
		{
			loggedInUser = new LoggedInUser(ResponseCode.THREE_HUNDRED_ONE.getCode(), 
				ResponseCode.THREE_HUNDRED_ONE.getMessage(), null, null);
		}
		
		
		return loggedInUser;
	}

}
