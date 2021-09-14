package com.sos.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="History")
@Table(name="blog_history")
public class History {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "history_id", nullable = false)
	private Long historyId;
	
	@Column(name = "history_type", nullable = false, length = 10, columnDefinition = "varchar(10)")
	@Enumerated(EnumType.STRING)
	private HistoryType historyType;
	
	@Column(name = "history_table", nullable = false, length = 30, columnDefinition = "varchar(30)")
	private String tableName;
	
	@Column(name = "table_id", nullable = false, columnDefinition = "INT")
	private Long tableId;
	
	@Column(name = "table_column", nullable = false, length = 30, columnDefinition = "VARCHAR(30)")
	private String column;
	
	@Column(name = "username", nullable = false, length = 26, columnDefinition = "VARCHAR(26)")
	private String username;
	
	@Column(name = "old_value", nullable = true, length = 60, columnDefinition = "VARCHAR(60)")
	private String oldValue;
	
	@Column(name = "new_value", nullable = true, length = 60, columnDefinition = "VARCHAR(60)")
	private String newValue;
	
	@Column(name = "message", nullable = true, length = 256, columnDefinition = "VARCHAR(256)")
	private String message;

	@Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdDatetime;
	
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public HistoryType getHistoryType() {
		return historyType;
	}

	public void setHistoryType(HistoryType histpryType) {
		this.historyType = histpryType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
