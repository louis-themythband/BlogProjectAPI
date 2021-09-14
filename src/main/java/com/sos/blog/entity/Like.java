package com.sos.blog.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="Like")
@Table(name="blog_like")
public class Like {

	
	@EmbeddedId
	private LikeCompoundKey likeCompoundKey;
	
	public Like()
	{
		// Do Nothing
	}
	
	public Like(Long blogId, String username)
	{
		likeCompoundKey = new LikeCompoundKey(blogId, username);
	}
	

	public LikeCompoundKey getLikeCompoundKey() {
		return likeCompoundKey;
	}

	public void setLikeCompoundKey(LikeCompoundKey likeCompoundKey) {
		this.likeCompoundKey = likeCompoundKey;
	}

}
