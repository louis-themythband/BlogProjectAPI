package com.sos.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sos.blog.entity.Blog;
import com.sos.blog.entity.BlogUser;
import com.sos.blog.entity.Category;
import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.entity.LikeCompoundKey;
import com.sos.blog.entity.SimpleBlog;
import com.sos.blog.entity.Status;
import com.sos.blog.entity.Visibility;
import com.sos.blog.model.BlogConverter;
import com.sos.blog.model.BlogModel;
import com.sos.blog.model.SimpleBlogModel;
import com.sos.blog.repository.BlogRepository;
import com.sos.blog.repository.CategoryRepository;
import com.sos.blog.repository.LikeRepository;
import com.sos.blog.repository.SimpleBlogRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private SimpleBlogRepository simpleBlogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private LikeRepository likeRepository;
	
//	@Autowired
//	private CommentService commentService;
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> getBlogsByUserId(Long userId)
	{
		List<SimpleBlog> blogList = simpleBlogRepository.getPublishedBlogsByUserId(userId);
		List<SimpleBlogModel> modelList = new ArrayList<SimpleBlogModel>();
		
		for(SimpleBlog blog : blogList)
		{
			modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
		}
		
		return modelList;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> getBlogsByUsername(String userList, Visibility visibility, String username)
	{
		List<SimpleBlog> blogList = simpleBlogRepository.getPublishedBlogsByUserName(userList, visibility);
		List<SimpleBlogModel> modelList = new ArrayList<SimpleBlogModel>();
		
		History history = new History();
		history.setHistoryType(HistoryType.SELECT);
		history.setTableName("Blog");
		history.setColumn("user_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setMessage("User " + username +" searched all published by user " + userList + " returned " + blogList.size() + " records.");
		this.historyService.createHistoryRecord(history);
		
		for(SimpleBlog blog : blogList)
		{
			modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
		}
		
		return modelList;
	}

	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> getAllBlogs(Visibility visibility)
	{
		List<SimpleBlog> blogList = simpleBlogRepository.getAllPublishedBlogs(visibility);
		List<SimpleBlogModel> modelList = new ArrayList<SimpleBlogModel>();
		
		for(SimpleBlog blog : blogList)
		{
			modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
		}
		
		return modelList;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> getAllBlogsByUsername(String username)
	{
		List<SimpleBlog> blogList = simpleBlogRepository.getAllBlogsByUsername(username);
		List<SimpleBlogModel> modelList = new ArrayList<SimpleBlogModel>();
		
		for(SimpleBlog blog : blogList)
		{
			modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
		}
		
		return modelList;
	}

	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public BlogModel getBlog(Long blogId, String username)
	{
		Optional<Blog> optinalBlog = blogRepository.findById(blogId);
		
		if(optinalBlog.isPresent())
		{
			History history = new History();
			history.setHistoryType(HistoryType.SELECT);
			history.setTableName("Blog");
			history.setColumn("blog_id");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("User " + username +" loaded blog " + blogId);
			this.historyService.createHistoryRecord(history);
			
			BlogModel model = BlogConverter.BlogEntityToModel(optinalBlog.get());
			
			LikeCompoundKey likeKey = new LikeCompoundKey(blogId, username);
			
			if(this.likeRepository.findById(likeKey) != null)
			{
				model.setHasLiked(true);
			}
			else
			{
				model.setHasLiked(false);
			}
			
			return model;
		}
		else
		{
			return new BlogModel();
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public Integer increaseLike(Long blogId)
	{
		SimpleBlog blog = simpleBlogRepository.getOne(blogId);
		Integer likes = blog.getLikes();
		likes += 1;
		blog.setLikes(likes);
		
		return likes;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public Integer increaseDislike(Long blogId)
	{
		SimpleBlog blog = simpleBlogRepository.getOne(blogId);
		Integer dislikes = blog.getDislikes();
		dislikes += 1;
		blog.setDislikes(dislikes);
		
		return dislikes;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public Long createNewBlog(BlogModel blogModel, List<String> stringList)
	{
		// Load the BlogUser Entity
		Optional<BlogUser> optionalUser = this.userService.getUserById(blogModel.getUser().getUserId());
		// Convert the blog model toan entity
		Blog newBlog = BlogConverter.BlogModelToEntity(blogModel);
		
		// if the blog user exists add it to the blog
		if(optionalUser.isPresent())
		{
			newBlog.setUser(optionalUser.get());
		}
			
		// save the blog entity and get an attached entity in return
		Blog blog = blogRepository.save(newBlog);
		
		// create the categories for the blog
		if(stringList != null) 
		{
			List <Category> categoryList = new ArrayList <Category> ();
			
			for(String string : stringList)
			{
				Category category = new Category();
				category.setBlogId(blog.getBlogId());
				category.setText(string.toLowerCase());
				
				category = this.categoryRepository.saveAndFlush(category);
				categoryList.add(category);
			}
	
			// commented until needed or not needed
			// newBlog.setCategoryList(categoryList);
		}

		return blog.getBlogId();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateBlogActive(Long blogId, Boolean active, String username)
	{
		Optional<Blog> optionlBlog = this.blogRepository.findById(blogId);
		
		if(optionlBlog.isPresent())
		{
			Blog blog = optionlBlog.get();
			blog.setActive(active);
			
			History history = new History();
			history.setHistoryType(HistoryType.UPDATE);
			history.setTableName("Blog");
			history.setColumn("active");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("User " + username +" changed active = " + active);
			this.historyService.createHistoryRecord(history);
		}
		
		return;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateBlogStatus(Long blogId, Status status, String username)
	{
		Optional<Blog> optionlBlog = this.blogRepository.findById(blogId);
		
		if(optionlBlog.isPresent())
		{
			Blog blog = optionlBlog.get();
			blog.setStatus(status);
			
			History history = new History();
			history.setHistoryType(HistoryType.UPDATE);
			history.setTableName("Blog");
			history.setColumn("status");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("User " + username +" changed status = " + status.name());
			this.historyService.createHistoryRecord(history);
		}
		
		return;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateBlogVisibility(Long blogId, Visibility visibility, String username)
	{
		Optional<Blog> optionlBlog = this.blogRepository.findById(blogId);
		
		if(optionlBlog.isPresent())
		{
			Blog blog = optionlBlog.get();
			blog.setVisibility(visibility);
			
			History history = new History();
			history.setHistoryType(HistoryType.UPDATE);
			history.setTableName("Blog");
			history.setColumn("visibility");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("User " + username +" changed visibility = " + visibility.name());
			this.historyService.createHistoryRecord(history);
		}
		
		return;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> search(String term, Visibility visibility, String username)
	{
		List <SimpleBlogModel> modelList = new ArrayList <SimpleBlogModel> ();
		
		List<SimpleBlog> blogList = this.simpleBlogRepository.getAllPublishedBlogs(visibility);
		
		blogList.stream().forEach(blog -> 
		{
			if(blog.getTitle().toLowerCase().contains(term.toLowerCase()))
			{
				modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
			}
			else if(blog.getUser().getUserName().toLowerCase().equals(term.toLowerCase()))
			{
				modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
			}
			else
			{
				for(Category category : blog.getCategoryList())
				{
					if(category.getText().toLowerCase().contains(term.toLowerCase()))
					{
						modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
					}
				}
			}
		});
		
		History history = new History();
		history.setHistoryType(HistoryType.SEARCH);
		history.setTableName("Blog");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setMessage("Searched term [" + term + "] with visibility = " + visibility.name());
		this.historyService.createHistoryRecord(history);

		return modelList;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<SimpleBlogModel> searchByUsername(String userList, String term, Visibility visibility, String username)
	{
		List <SimpleBlogModel> modelList = new ArrayList <SimpleBlogModel> ();
		
		List<SimpleBlog> blogList = this.simpleBlogRepository.getPublishedBlogsByUserName(userList, visibility);
		
		blogList.stream().forEach(blog -> 
		{
			if(blog.getTitle().toLowerCase().contains(term.toLowerCase()))
			{
				modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
			}
			else
			{
				for(Category category : blog.getCategoryList())
				{
					if(category.getText().toLowerCase().contains(term.toLowerCase()))
					{
						modelList.add(BlogConverter.SimpleBlogEntityToModel(blog));
					}
				}
			}
		
		});
		
		History history = new History();
		history.setHistoryType(HistoryType.SEARCH);
		history.setTableName("Blog");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setMessage("Searched term [" + term + "] for user = " + userList +" with visibility = " + visibility.name());
		this.historyService.createHistoryRecord(history);

		return modelList;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public BlogModel updateBlog(BlogModel blogModel, String username) throws Exception
	{
		Optional<Blog> blogOptional = this.blogRepository.findById(blogModel.getBlogId());
		
		if(blogOptional.isPresent())
		{
			Blog blog = blogOptional.get();
			blog.setActive(blogModel.getActive());
			blog.setEditedDatetime(new Date());
			if(blogModel.getText() != null)
				blog.setText(blogModel.getText());
			if(blogModel.getTitle() != null)
				blog.setTitle(blogModel.getTitle());
			if(blogModel.getStatus() != null)
				blog.setStatus(blogModel.getStatus());
			if(blogModel.getVisibility() != null)
				blog.setVisibility(blogModel.getVisibility());
			
			History history = new History();
			history.setHistoryType(HistoryType.UPDATE);
			history.setTableName("Blog");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("Updated blog [" + blogModel.getBlogId() + "]");
			this.historyService.createHistoryRecord(history);
			
			return BlogConverter.BlogEntityToModel(blog);
		}
		else
		{
			throw new Exception("Could not find Blog [" + blogModel.getBlogId() + "]");
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteBlog(Long blogId, String username)
	{
		

		Optional<SimpleBlog> optionalBlog = this.simpleBlogRepository.findById(blogId);
		
		if(optionalBlog.isPresent())
		{
			SimpleBlog simpleBlog = optionalBlog.get();
			simpleBlog.setActive(false);
			simpleBlog.setStatus(Status.RETIRED);
			simpleBlog.setVisibility(Visibility.NONE);
			
			History history = new History();
			history.setHistoryType(HistoryType.DELETE);
			history.setTableName("Blog");
			history.setTableId(blogId);
			history.setColumn("blog_id");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setMessage("Deleted blog [" + blogId + "]");
			this.historyService.createHistoryRecord(history);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Byte getCommentSecurity(Long blogId)
	{
		return this.blogRepository.blogSecurityByBlogId(blogId);
	}

}
