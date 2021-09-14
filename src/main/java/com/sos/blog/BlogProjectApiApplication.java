package com.sos.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.sos.blog.entity")
@EnableJpaRepositories("com.sos.blog.repository")
public class BlogProjectApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogProjectApiApplication.class, args);
	}

}
