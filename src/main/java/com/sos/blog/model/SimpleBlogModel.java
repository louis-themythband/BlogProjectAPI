package com.sos.blog.model;

import java.util.Date;
import java.util.List;

import com.sos.blog.entity.Status;
import com.sos.blog.entity.Visibility;

public class SimpleBlogModel {
	
	private Long blogId;
	private UserModel user;
	private String title;
	private Date createdDatetime;
	private Date editedDatetime;
	private Date publishedDatetime;
	private Integer likes;
	private Integer dislikes;
	private Integer rating;
	private Boolean active;
	private Status status;
	private Visibility visibility;
	private List<CategoryModel> categoryList;
	
	

	public SimpleBlogModel() {
		// TODO Auto-generated constructor stub
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
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

	public List<CategoryModel> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryModel> categoryList) {
		this.categoryList = categoryList;
	}

}
