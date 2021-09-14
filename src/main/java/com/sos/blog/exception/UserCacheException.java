package com.sos.blog.exception;

public class UserCacheException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5025381526926621501L;
	private String username;

	public UserCacheException() {
		// TODO Auto-generated constructor stub
	}

	public UserCacheException(String username, String message) {
		super(message);
		this.username = username;
	}

	public UserCacheException(String username, Throwable cause) {
		super(cause);
		this.username = username;
	}

	public UserCacheException(String username, String message, Throwable cause) {
		super(message, cause);
		this.username = username;
	}

	public UserCacheException(String username, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
