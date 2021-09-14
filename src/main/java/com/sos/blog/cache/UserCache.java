package com.sos.blog.cache;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.sos.blog.entity.History;
import com.sos.blog.entity.HistoryType;
import com.sos.blog.service.HistoryService;
import com.sos.blog.util.GUIDExpressionParser;



@Component()
@PropertySource("classpath:application.properties")
public class UserCache implements ActionListener{
	
	@Autowired
	private HistoryService historyService;
	
	private static final Logger logger =LoggerFactory.getLogger(UserCache.class);
	
	@Value("${cache.guid}")
	private String guid = "$$$$$$$$$$$$$$$$";
	
	@Value("${cache.delay}")
	private Integer delay = 1800000;
	
	private ConcurrentHashMap <String, String> map;
	private ConcurrentHashMap <String, Timer> timers;
	
	private GUIDExpressionParser guidParser;

	public UserCache() {
		this.map = new ConcurrentHashMap <String, String> ();
		this.map.put("Anonymous", "0123456789ABCDEF");
		this.timers = new ConcurrentHashMap <String, Timer> ();
		this.guidParser = new GUIDExpressionParser(guid);
		logger.info("cache.delay = " + delay);
		logger.info("cache.guid = " + guid);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean hasCache(String key)
	{
		if(map != null && key != null)
			return this.map.containsKey(key);
		else 
			return false;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getCache(String key)
	{
		return this.map.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String addCache(String key)
	{
		logger.info("Session Cache added for " + key);
		
		String value = this.guidParser.generateRandomGUID();
		if(this.map.containsValue(value))
		{
			return addCache(key);
		}
		else
		{
			this.map.put(key, value);
			
			Timer timer = new Timer(delay, this);
			timer.setActionCommand(key);
			timer.start();
			
			this.timers.put(key, timer);
			return value;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean removeCache(String key)
	{
		if(this.map.containsKey(key))
		{
			this.map.remove(key);
			Timer timer = this.timers.remove(key);
			timer.setActionCommand(null);
			timer.removeActionListener(this);
			if(timer.isRunning()) timer.stop();
			logger.info("Session Cache removed for " + key);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param key
	 */
	public void keepAlive(String key)
	{
		logger.info("Session Cache keepAlive for " + key);
		
		Timer oldTimer = this.timers.get(key);
		
		if(oldTimer != null)
		{
			oldTimer.stop();
			oldTimer.setDelay(delay);
			oldTimer.setInitialDelay(delay);
			oldTimer.restart();
		}
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String key = event.getActionCommand();
		logger.info("Session Cache timed out for " + key);
		Boolean removed = this.removeCache(key);
		
		if(removed)
		{
			History history = new History();
			history.setHistoryType(HistoryType.SESSION_TIMEOUT);
			history.setTableName("blog_user");
			history.setColumn("user_name");
			history.setCreatedDatetime(new Date());
			history.setUsername(key);
			history.setMessage("User [" + key + "] Session timeout.");
			historyService.createHistoryRecord(history);
		}
	}

}
