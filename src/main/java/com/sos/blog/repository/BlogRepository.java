package com.sos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
	
	@Query("SELECT blog FROM Blog blog WHERE blog.user.userId = :userId AND blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.active = true ORDER BY blog.publishedDatetime DESC")
	public List<Blog> getPublishedBlogsByUserId(Long userId);
	
	@Query("SELECT blog FROM Blog blog WHERE blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.active = true ORDER BY blog.publishedDatetime DESC")
	public List<Blog> getAllPublishedBlogs();
	
	@Query("SELECT blog FROM Blog blog WHERE blog.user.userId = :userId ORDER BY blog.publishedDatetime DESC")
	public List<Blog> getAllBlogsByUserId(Long userId);
	
	@Query("SELECT blog.commentSecurity FROM Blog blog where blog.blogId = :blogid")
	public Byte blogSecurityByBlogId(Long blogid);

}
