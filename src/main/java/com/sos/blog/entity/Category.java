package com.sos.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Category")
@Table(name="blog_category")
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "category_id", nullable = false)
	private Long categoryId;
	
	@Column(name = "blog_id", nullable = false, columnDefinition = "INT")
	private Long blogId;
	
	@Column(name="category_text", nullable = false, length = 60, columnDefinition = "VARCHAR(60)")
	private String text;
	
	
	public Category() {}
	
	public Category(String text, Long catId, Long blogId) 
	{
		this.text = text;
		this.blogId = blogId;
		this.categoryId = catId;
	}
	

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int hashCode()
	{
		return this.text.hashCode();
	}
	
	@Override
	public boolean equals(Object src)
	{
		if(src != null)
		{
			if(src instanceof Category)
			{
				Category category = (Category)src;
				return (this.text.equals(category.text));
			}
		}
		
		return false;
	}
	
	public String toString()
	{
		return this.text;
	}

}
