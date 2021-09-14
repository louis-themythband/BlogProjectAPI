package com.sos.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sos.blog.entity.Category;
import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.model.CategoryModel;
import com.sos.blog.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private HistoryService historyService;
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Category> addCategories(Long blogId, List<String> stringList, String username)
	{
		List <Category> categoryList = new ArrayList <Category> ();
		
		for(String categoryText : stringList)
		{
			Category category = new Category();
			category.setBlogId(blogId);
			category.setText(categoryText);
			
			category = this.categoryRepository.saveAndFlush(category);
			categoryList.add(category);
			
			History history = new History();
			history.setHistoryType(HistoryType.NEW);
			history.setTableName("blog_category");
			history.setColumn("category_id");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setNewValue(categoryText);
			history.setMessage("User " + username + " added category [ " + categoryText + " ]");
			historyService.createHistoryRecord(history);
		}
		
		
		return categoryList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void removeCategory(Long categoryId, String username)
	{
		Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);
		
		if(categoryOptional.isPresent())
		{
			Category category = categoryOptional.get();
			
			History history = new History();
			history.setHistoryType(HistoryType.DELETE);
			history.setTableName("blog_category");
			history.setColumn("category_id");
			history.setUsername(username);
			history.setCreatedDatetime(new Date());
			history.setOldValue(category.getText());
			history.setMessage("User " + username + " deleted category [ " + category.getText() + " ]");
			historyService.createHistoryRecord(history);
		}
		
		categoryRepository.deleteById(categoryId);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void removeCategory(CategoryModel model, String username) {
		this.categoryRepository.deleteById(model.getCategoryId());
		
		History history = new History();
		history.setHistoryType(HistoryType.DELETE);
		history.setTableName("blog_category");
		history.setColumn("category_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setOldValue(model.getCategoryId().toString());
		history.setMessage("User " + username + " deleted category [ " + model.getText() + " ] with id " + model.getCategoryId());
		historyService.createHistoryRecord(history);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CategoryModel addCategory(Long blogId, String categoryText, String username) 
	{
		
		
		Category category = new Category();
		category.setBlogId(blogId);
		category.setText(categoryText);
		category = this.categoryRepository.save(category);
		
		CategoryModel model = new CategoryModel();
		model.setBlogId(blogId);
		model.setCategoryId(category.getCategoryId());
		model.setText(categoryText);
		model.setCategoryId(category.getCategoryId());
		
		History history = new History();
		history.setHistoryType(HistoryType.NEW);
		history.setTableName("blog_category");
		history.setTableId(blogId);
		history.setColumn("blog_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setNewValue(categoryText);
		history.setMessage("Added category [" + categoryText + "]");
		this.historyService.createHistoryRecord(history);
		
		return model;
	}
	
	public Boolean containsCategory(Long catagoryId)
	{
		return (this.categoryRepository.findById(catagoryId) != null);
	}
	
	public void deleteCategoryByBlogId(Long blogId, String username)
	{
		Integer count = this.categoryRepository.deleteCategoryByBlogId(blogId);
		
		History history = new History();
		history.setHistoryType(HistoryType.DELETE);
		history.setTableName("blog_category");
		history.setTableId(blogId);
		history.setColumn("blog_id");
		history.setUsername(username);
		history.setCreatedDatetime(new Date());
		history.setMessage("Deleted " + count + " categorys for blog [" + blogId + "]");
		this.historyService.createHistoryRecord(history);
	}
}
