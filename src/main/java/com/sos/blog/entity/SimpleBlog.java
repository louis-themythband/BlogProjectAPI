package com.sos.blog.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Entity(name="SimpleBlog")
@Table(name="blog")
public class SimpleBlog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "blog_id", nullable = false)
	private Long 			blogId;

	@OneToOne(targetEntity = BlogUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private BlogUser user;
	
	@Column(name = "blog_title", nullable = false, length = 60, columnDefinition = "varchar(60)")
	private String title;
	
	@Column(name="created_datetime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdDatetime;
	
	@Column(name="edited_datetime", nullable = false, columnDefinition = "TIMESTAMP")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date editedDatetime;
	
	@Column(name="published_datetime", nullable = false, columnDefinition = "TIMESTAMP")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date publishedDatetime;
	
	@Column(name="likes", columnDefinition = "INT DEFAULT 0")
	private Integer likes;
	
	@Column(name="dislikes", columnDefinition = "INT DEFAULT 0")
	private Integer dislikes;
	
	@Column(name="rating", columnDefinition = "INT DEFAULT 0")
	private Integer rating;
	
	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean active;
		
	@Column(name="status", length = 16, columnDefinition = "VARCHAR(16) DEFAULT 'DRAFT'")
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name="visibility", length = 16, columnDefinition = "VARCHAR(16) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private Visibility visibility;
	
	@OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_id", referencedColumnName = "blog_id")
	private List<Category> categoryList;


	
	

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public BlogUser getUser() {
		return user;
	}

	public void setUser(BlogUser user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Date getEditedDatetime() {
		return editedDatetime;
	}

	public void setEditedDatetime(Date editedDatetime) {
		this.editedDatetime = editedDatetime;
	}

	public Date getPublishedDatetime() {
		return publishedDatetime;
	}

	public void setPublishedDatetime(Date publishedDatetime) {
		this.publishedDatetime = publishedDatetime;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}


}
