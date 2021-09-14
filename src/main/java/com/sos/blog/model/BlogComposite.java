package com.sos.blog.model;

import java.util.List;

public class BlogComposite {
	
	private BlogModel blog;
	private List<String> categoryList;

	
	public BlogComposite() {
		// TODO Auto-generated constructor stub
	}

	public BlogModel getBlog() {
		return blog;
	}

	public void setBlog(BlogModel blog) {
		this.blog = blog;
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

}
