package com.sos.blog.model;

import java.util.Date;

import com.sos.blog.entity.HistoryType;

public class HistoryModel {

	public HistoryModel() {
		// TODO Auto-generated constructor stub
	}

	private Long historyId;
	private HistoryType histpryType;
	private String tableName;
	private Long tableId;
	private String column;
	private String oldValue;
	private String newValue;
	private String message;
	private Date createdDatetime;
	
	
	public Long getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	
	public HistoryType getHistpryType() {
		return histpryType;
	}
	
	public void setHistpryType(HistoryType histpryType) {
		this.histpryType = histpryType;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Long getTableId() {
		return tableId;
	}
	
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public String getOldValue() {
		return oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getCreatedDatetime() {
		return createdDatetime;
	}
	
	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	
	
}
