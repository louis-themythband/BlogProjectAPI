package com.sos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sos.blog.entity.Visibility;

import com.sos.blog.entity.SimpleBlog;

@Repository
public interface SimpleBlogRepository extends JpaRepository<SimpleBlog, Long>{
	
	@Query("SELECT blog FROM SimpleBlog blog WHERE blog.user.userId = :userId AND blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.active = true ORDER BY blog.publishedDatetime DESC")
	public List<SimpleBlog> getPublishedBlogsByUserId(Long userId);

	
	@Query("SELECT blog FROM SimpleBlog blog WHERE (blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.active = true AND blog.visibility = :visibility) OR( blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.active = true AND blog.visibility = com.sos.blog.entity.Visibility.PUBLIC) ORDER BY blog.publishedDatetime DESC")
	public List<SimpleBlog> getAllPublishedBlogs(Visibility visibility);
	
	
	@Query("SELECT blog FROM SimpleBlog blog WHERE (blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.user.userName = :username AND blog.active = true AND blog.visibility = :visibility) OR (blog.status = com.sos.blog.entity.Status.PUBLISHED AND blog.user.userName = :username AND blog.visibility = com.sos.blog.entity.Visibility.PUBLIC) ORDER BY blog.publishedDatetime DESC")
	public List<SimpleBlog> getPublishedBlogsByUserName(String username, Visibility visibility);

	
	@Query("SELECT blog FROM SimpleBlog blog WHERE blog.user.userName = :username ORDER BY blog.publishedDatetime DESC")
	public List<SimpleBlog> getAllBlogsByUsername(String username);
	
}
