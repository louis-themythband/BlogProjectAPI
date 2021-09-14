package com.sos.blog.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name="Comment")
@Table(name="blog_comment")
public class Comment {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "comment_id", nullable = false)
	private Long commentId;
	
	@Column(name="blog_id", nullable = false, columnDefinition = "INT")
	private Long blogId;
	
	@Column(name="comment_title", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
	private String commentTitle;
	
	@Column(name="comment_text", nullable = false, columnDefinition = "CLOB")
	private String commentText;
	
	@OneToOne(targetEntity = BlogUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_user", referencedColumnName = "user_id")
	private BlogUser commentUser;
	
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id")
	private List<Comment> commentsList;
	
	@Column(name="likes", columnDefinition = "INT DEFAULT 0")
	private Integer likes;
	
	@Column(name="dislikes", columnDefinition = "INT DEFAULT 0")
	private Integer dislikes;
	
	@Column(name="rating", columnDefinition = "INT DEFAULT 0")
	private Integer rating;
	
	@Column(name = "active", nullable = true, length = 16, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean active;
	
	@Column(name="created_datetime", columnDefinition = "TIMESTAMP DEFAULT NOW()")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="parent_comment_id", columnDefinition="INT")
	private Long parentCommentId;

	
	
	
	
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

	public BlogUser getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(BlogUser commentUser) {
		this.commentUser = commentUser;
	}

	public List<Comment> getCommentsList() {
		if(this.commentsList == null)
		{
			this.commentsList = new ArrayList<Comment>();
		}
		return commentsList;
	}

	public void setCommentsList(List<Comment> commentsList) {
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
