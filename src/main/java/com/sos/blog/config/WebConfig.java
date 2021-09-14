package com.sos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer
{

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/blog/**").allowedOrigins("*").maxAge(3600);
		registry.addMapping("/blog-user/**").allowedOrigins("*").maxAge(3600);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

}
