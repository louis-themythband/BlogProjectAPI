package com.sos.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="BlogUser")
@Table(name="blog_user")
public class BlogUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "first_name", nullable = false, length = 30, columnDefinition = "varchar(30)")
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 30, columnDefinition = "varchar(30)")
	private String lastName;
	
	@Column(name = "user_name", nullable = false, length = 26, columnDefinition = "varchar(26)")
	private String userName;
	
	@Column(name = "user_password", nullable = false, length = 50, columnDefinition = "varchar(50)")
	private String userPassword;
	
	@Column(name = "user_email", nullable = false, length = 36, columnDefinition = "varchar(36)")
	private String userEmail;
	
	@Column(name = "is_admin", columnDefinition = "boolean default false")
	private Boolean isAdmin;
	
	@Column(name = "active", columnDefinition = "boolean default true")
	private Boolean active;
	
	@Column(name = "can_comment", columnDefinition = "boolean default true")
	private Boolean canComment;
	
	@Column(name = "can_blog", columnDefinition = "boolean default true")
	private Boolean canBlog;

	
	
	
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

}
