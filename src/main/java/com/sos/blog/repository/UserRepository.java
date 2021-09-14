package com.sos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.BlogUser;

@Repository
public interface UserRepository extends JpaRepository<BlogUser, Long> {
	
	@Query("SELECT bu FROM BlogUser bu WHERE bu.userName = :userName AND bu.active = true")
	public BlogUser getUserByUserName(String userName);
	
}
