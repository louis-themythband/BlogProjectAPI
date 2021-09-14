package com.sos.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.blog.entity.BlogUser;
import com.sos.blog.entity.Comment;
import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.model.BlogConverter;
import com.sos.blog.model.CommentModel;
import com.sos.blog.repository.CommentRepository;
import com.sos.blog.repository.UserRepository;

@Service
public class CommentService {

	@Autowired 
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HistoryService historyService;
	
	
	/**
	 * 
	 * @param comment
	 * @return
	 */
	public List<CommentModel> createNewComment(Comment comment)
	{
		BlogUser user = this.userRepository.getOne(comment.getCommentUser().getUserId());
		comment.setCommentUser(user);
		comment = this.commentRepository.saveAndFlush(comment);
		
		History history = new History();
		history.setHistoryType(HistoryType.NEW);
		history.setTableName("blog_comment");
		history.setColumn("comment_id");
		history.setUsername(user.getUserName());
		history.setCreatedDatetime(new Date());
		history.setNewValue(comment.getCommentId().toString());
		history.setMessage("User " + user.getUserName() + " created a new comment with id  " + comment.getCommentId());
		historyService.createHistoryRecord(history);
		
		List<Comment> commentList = this.commentRepository.getCommentsByBlogId(comment.getBlogId());
		List<CommentModel> commentModelList = new ArrayList<CommentModel>(commentList.size());
		for(Comment commentItem : commentList)
		{
			commentModelList.add(BlogConverter.CommentEntityToModel(commentItem));
		}
		
		return commentModelList;
	}
	
	public List<CommentModel> listByBlogId(Long blogid)
	{
		List<Comment> commentList = this.commentRepository.getCommentsByBlogId(blogid);
		List<CommentModel> commentModelList = new ArrayList<CommentModel> (commentList.size());
		
		for(Comment comment: commentList)
		{
			commentModelList.add(BlogConverter.CommentEntityToModel(comment));
		}
		
		return commentModelList;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteCommentById(Long id, String username)
	{
		this.commentRepository.deleteById(id);
		
		History history = new History();
		history.setHistoryType(HistoryType.DELETE);
		history.setTableName("blog_comment");
		history.setColumn("comment_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setOldValue(id.toString());
		history.setMessage("User " +username + " delete comment with id  " + id);
		historyService.createHistoryRecord(history);
	}
	
	public void deleteByBlogId(Long blogId)
	{
		this.commentRepository.deleteByBlogId(blogId);
	}

}
