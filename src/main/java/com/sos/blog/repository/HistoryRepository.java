package com.sos.blog.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.blog.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
	
	
	@Query("SELECT history FROM History history WHERE history.createdDatetime BETWEEN :start AND :end ORDER BY history.createdDatetime DESC")
	public List<History> getHistoryBetweenDates(Date start, Date end);
	
	@Query("SELECT history FROM History history WHERE history.username = :username ORDER BY history.createdDatetime DESC")
	public List<History> getHistoryByUsername(String username);
}
