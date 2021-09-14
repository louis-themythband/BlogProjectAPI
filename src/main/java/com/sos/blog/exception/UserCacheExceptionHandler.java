package com.sos.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class UserCacheExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger =LoggerFactory.getLogger(UserCacheExceptionHandler.class);

	public UserCacheExceptionHandler() {
		
	}

	@ExceptionHandler(value  = { UserCacheException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) 
	{
		logger.error(ex.getMessage(), ex);
		
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, 
        new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
