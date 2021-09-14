package com.sos.blog.model;

import java.util.Date;

public class LoggedInUser {

	private int responseCode;
	private String responseMessage;
	private UserModel user;
	private Date date;
	private String token;
	

	public LoggedInUser(int responseCode, String message, UserModel user, String token)
	{
		this.responseCode = responseCode;
		this.responseMessage = message;
		this.user = user;
		this.date = new Date();
		this.token = token;
	}


	public int getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


	public String getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}


	public UserModel getUser() {
		return user;
	}


	public void setUser(UserModel user) {
		this.user = user;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	

}
