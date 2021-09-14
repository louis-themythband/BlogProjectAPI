package com.sos.blog.model;

public enum ResponseCode {
	ONE_HUNDRED(100,"[100] Username not found."),
	ONE_HUNDRED_ONE(101,"[101] Uusername is null."),
	ONE_HUNDRED_TWO(102,"[102] password is null."),
	TWO_HUNDRED(200,"[200] Successfully Logged in."),
	TWO_HUNDRED_ONE(201,"[201] Incorrect password."),
	TWO_HUNDRED_TWO(202,"[202] Active User Session."),
	THREE_HUNDRED(300, "[300] Successfully changed password."),
	THREE_HUNDRED_ONE(301, "[301] User No Found."),
	THREE_HUNDRED_TWO(302, "[302] User password was not changed."),
	THREE_HUNDRED_THREE(303,"[303] User session expired."),
	THREE_HUNDRED_FOUR(304,"[304] ID not found"),
	THREE_HUNDRED_FIVE(305,"[305] Internal Exception");
	// TODO: add response codes for password rule exceptions.
	
	// **********************************
	// Password rules exceptions go here
	// **********************************
	
	
	private int code;
	private String message;
	
	ResponseCode(int code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public String getMessage()
	{
		return this.message;
	}
}
