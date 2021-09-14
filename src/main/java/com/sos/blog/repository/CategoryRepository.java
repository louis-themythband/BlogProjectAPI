package com.sos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Modifying(flushAutomatically = true)
	@Query("DELETE Category category WHERE category.blogId = :blogId")
	public Integer deleteCategoryByBlogId(@Param("blogId")Long blogId);
}
