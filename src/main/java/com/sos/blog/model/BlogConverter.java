package com.sos.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sos.blog.entity.Blog;
import com.sos.blog.entity.BlogUser;
import com.sos.blog.entity.Category;
import com.sos.blog.entity.Comment;
import com.sos.blog.entity.History;
import com.sos.blog.entity.SimpleBlog;

public class BlogConverter {

	public static UserModel BlogUserEntityToModel(BlogUser user)
	{
		UserModel model = new UserModel();
		model.setUserId(user.getUserId());
		model.setFirstName(user.getFirstName());
		model.setLastName(user.getLastName());
		model.setUserName(user.getUserName());
		model.setUserEmail(user.getUserEmail());
		model.setIsAdmin(user.getActive());
		model.setCanComment(user.getCanComment());
		model.setCanBlog(user.getCanBlog());
		model.setActive(user.getActive());
		
		return model;
	}
	
	public static BlogUser UserModelToEntity(UserModel model)
	{
		BlogUser user = new BlogUser();
		user.setUserId(model.getUserId());
		user.setFirstName(model.getFirstName());
		user.setLastName(model.getLastName());
		user.setUserName(model.getUserName());
		user.setUserPassword(model.getUserPassword());
		user.setUserEmail(model.getUserEmail());
		user.setIsAdmin(model.getIsAdmin());
		user.setCanComment(model.getCanComment());
		user.setCanBlog(model.getCanBlog());
		user.setActive(model.getActive());
		
		return user;
	}
	
	public static BlogModel BlogEntityToModel(Blog blog)
	{
		BlogModel model = new BlogModel();
		model.setBlogId(blog.getBlogId());
		model.setUser(BlogUserEntityToModel(blog.getUser()));
		model.setPublishedDatetime(blog.getPublishedDatetime());
		model.setCreatedDatetime(blog.getCreatedDatetime());
		model.setEditedDatetime(blog.getEditedDatetime());
		model.setLikes(blog.getLikes());
		model.setDislikes(blog.getDislikes());
		model.setRating(blog.getRating());
		model.setCommentSecurity(blog.getCommentSecurity());
		model.setTitle(blog.getTitle());
		model.setText(blog.getText());
		model.setStatus(blog.getStatus());
		model.setVisibility(blog.getVisibility());
		model.setActive(blog.getActive());
		
//		List<CommentModel> commentList = new ArrayList<CommentModel>();
//		for(Comment comment : blog.getCommentList())
//		{
//			commentList.add(CommentEntityToModel(comment));
//		}
//		model.setCommentList(commentList);
		
		List<CategoryModel> categoryList = new ArrayList <CategoryModel> ();
		for(Category category : blog.getCategoryList())
		{
			categoryList.add(CategoryEntityToModel(category));
		}
		
		model.setCategoryList(categoryList);
		
		return model;
	}
	
	public static Blog BlogModelToEntity(BlogModel model)
	{
		Blog blog = new Blog();
		blog.setBlogId(model.getBlogId());
		blog.setUser(UserModelToEntity(model.getUser()));
		blog.setPublishedDatetime(model.getPublishedDatetime());
		blog.setCreatedDatetime(model.getCreatedDatetime());
		blog.setEditedDatetime(model.getEditedDatetime());
		blog.setLikes(model.getLikes());
		blog.setDislikes(model.getDislikes());
		blog.setRating(model.getRating());
		blog.setCommentSecurity(model.getCommentSecurity());
		blog.setTitle(model.getTitle());
		blog.setText(model.getText());
		blog.setStatus(model.getStatus());
		blog.setVisibility(model.getVisibility());
		blog.setActive(model.getActive());
		
		return blog;
	}
	
	public static CategoryModel CategoryEntityToModel(Category category)
	{
		CategoryModel model = new CategoryModel();
		
		model.setCategoryId(category.getCategoryId());
		model.setBlogId(category.getBlogId());
		model.setText(category.getText());
		
		return model;
	}
	
	
	public static Category CategoryModelToEntity(CategoryModel model)
	{
		Category category = new Category();
		
		category.setCategoryId(model.getCategoryId());
		category.setBlogId(model.getBlogId());
		category.setText(model.getText());
		
		return category;
	}

	
	public static CommentModel CommentEntityToModel(Comment comment)
	{
		CommentModel model = new CommentModel();
		model.setBlogId(comment.getBlogId());
		model.setCommentId(comment.getCommentId());
		model.setCommentText(comment.getCommentText());
		model.setCommentTitle(comment.getCommentTitle());
		model.setLikes(comment.getLikes());
		model.setDislikes(comment.getDislikes());
		model.setRating(comment.getRating());
		model.setActive(comment.getActive());
		model.setCreatedDate(comment.getCreatedDate());
		model.setParentCommentId(comment.getParentCommentId());
		model.setCommentUser(BlogUserEntityToModel(comment.getCommentUser()));
		
		List<CommentModel> commentList = new ArrayList<CommentModel>();
		for(Comment subComment : comment.getCommentsList())
		{
			commentList.add(CommentEntityToModel(subComment));
		}
		model.setCommentsList(commentList);
		
		return model;
	}
	
	public static Comment CommentModelToEntity(CommentModel model)
	{
		Comment comment = new Comment();
		comment.setBlogId(model.getBlogId());
		comment.setCommentId(model.getCommentId());
		comment.setCommentText(model.getCommentText());
		comment.setCommentTitle(model.getCommentTitle());
		comment.setLikes(model.getLikes());
		comment.setDislikes(model.getDislikes());
		comment.setRating(model.getRating());
		comment.setActive(model.getActive());
		comment.setCreatedDate(model.getCreatedDate());
		comment.setParentCommentId(model.getParentCommentId());
		comment.setCommentUser(UserModelToEntity(model.getCommentUser()));
		
		return comment;
	}

	
	public static SimpleBlogModel SimpleBlogEntityToModel(SimpleBlog blog)
	{
		SimpleBlogModel model = new SimpleBlogModel();
		model.setBlogId(blog.getBlogId());
		model.setUser(BlogUserEntityToModel(blog.getUser()));
		model.setPublishedDatetime(blog.getPublishedDatetime());
		model.setCreatedDatetime(blog.getCreatedDatetime());
		model.setEditedDatetime(blog.getEditedDatetime());
		model.setLikes(blog.getLikes());
		model.setDislikes(blog.getDislikes());
		model.setRating(blog.getRating());
		model.setTitle(blog.getTitle());
		model.setStatus(blog.getStatus());
		model.setVisibility(blog.getVisibility());
		model.setActive(blog.getActive());
		
		List<CategoryModel> categoryList = new ArrayList <CategoryModel> ();
		for(Category category : blog.getCategoryList())
		{
			categoryList.add(CategoryEntityToModel(category));
		}
		model.setCategoryList(categoryList);
		
		return model;
	}
	
	public static History HistoryModelToEntity(HistoryModel model)
	{
		History history = new History();
		
		history.setHistoryId(null);
		history.setHistoryType(model.getHistpryType());
		history.setTableName(model.getTableName());
		history.setTableId(model.getTableId());
		history.setColumn(model.getColumn());
		history.setOldValue(model.getOldValue());
		history.setNewValue(model.getNewValue());
		history.setMessage(model.getMessage());
		history.setCreatedDatetime(new Date());
		
		return history;
	}

}
