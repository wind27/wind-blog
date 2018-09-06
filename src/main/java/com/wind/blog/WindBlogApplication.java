package com.wind.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan(basePackages = {"com.wind.blog.mapper"})
public class WindBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindBlogApplication.class, args);
	}
}
