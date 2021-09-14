package com.sos.blog.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class LikeCompoundKey implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1380530053211904586L;
	private Long blogId;
	private String userName;

	public LikeCompoundKey(Long blogId, String userName) {
		this.blogId = blogId;
		this.userName = userName;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
