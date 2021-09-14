package com.sos.blog.model;

public class UserModel 
{
	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String userPassword;
	private String userEmail;
	private Boolean isAdmin;
	private Boolean active;
	private Boolean canComment;
	private Boolean canBlog;
	private String token;
	
	
	public UserModel() {
		// TODO Auto-generated constructor stub
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public Boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public Boolean getCanComment() {
		return canComment;
	}


	public void setCanComment(Boolean canComment) {
		this.canComment = canComment;
	}


	public Boolean getCanBlog() {
		return canBlog;
	}


	public void setCanBlog(Boolean canBlog) {
		this.canBlog = canBlog;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	
}
