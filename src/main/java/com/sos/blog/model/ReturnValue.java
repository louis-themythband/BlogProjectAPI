package com.sos.blog.model;

public class ReturnValue <V>{
	
	private int code;
	private String message;
	private V value;

	
	public ReturnValue() 
	{
		
	}
	
	public ReturnValue(ResponseCode code, V value) {
		this.code = code.getCode();
		this.message = code.getMessage();
		this.value = value;
	}
	

	public ReturnValue(ResponseCode code) {
		this.code = code.getCode();
		this.message = code.getMessage();
	}

	public int getResponseCode() {
		return code;
	}
	
	public void setResponseCode(int code) {
		this.code = code;
	}

	public void setResponseCode(ResponseCode code) {
		this.code = code.getCode();
		this.message = code.getMessage();
	}

	public String getResponseMessage() {
		return message;
	}

	public void setResponseMessage(String message) {
		this.message = message;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
