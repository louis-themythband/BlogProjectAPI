package com.sos.blog.model;

import java.util.Date;
import java.util.List;

public class CommentModel {
	
	private Long commentId;
	private Long blogId;
	private String commentTitle;
	private String commentText;
	private UserModel commentUser;
	private List<CommentModel> commentsList;
	private Integer likes;
	private Integer dislikes;
	private Integer rating;
	private Boolean active;
	private Date createdDate;
	private Long parentCommentId;

	public CommentModel() {
		// TODO Auto-generated constructor stub
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getCommentTitle() {
		return commentTitle;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public UserModel getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(UserModel commentUser) {
		this.commentUser = commentUser;
	}

	public List<CommentModel> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<CommentModel> commentsList) {
		this.commentsList = commentsList;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

}
