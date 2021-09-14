package com.sos.blog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sos.blog.entity.History;
import com.sos.blog.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired(required = true)
	private HistoryRepository historyRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void createHistoryRecord(History history)
	{
		this.historyRepository.saveAndFlush(history);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<History> getAllHistory()
	{
		return this.historyRepository.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public List<History> getHistoryBetweenDates(Date start, Date end)
	{
		return this.historyRepository.getHistoryBetweenDates(start, end);
	}
}
