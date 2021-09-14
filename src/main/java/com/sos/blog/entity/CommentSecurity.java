package com.sos.blog.entity;

public enum CommentSecurity {
	
	NONE(0), OWNER(1), USER(2), ANONYMOUS(4);
	
	private int value;
	
	private CommentSecurity(int securityValue)
	{
		this.value = securityValue;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public String getName()
	{
		return this.name();
	}
	
}
