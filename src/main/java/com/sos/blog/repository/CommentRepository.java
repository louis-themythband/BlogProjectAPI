package com.sos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query("SELECT comment FROM Comment comment WHERE comment.blogId = :blogid AND comment.parentCommentId = 0 ORDER BY comment.createdDate DESC")
	public List<Comment> getCommentsByBlogId(Long blogid);
	
	@Modifying(flushAutomatically = true)
	@Query("DELETE Comment comment WHERE comment.blogId = :blogid")
	public Integer deleteByBlogId(Long blogid);
}
