package com.deom.BookManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@PropertySource("classpath:mysql-config.properties")
@Slf4j
public class BookManagementSystemApplication {

	public static void main(String[] args) {
		System.setProperty("todo.log.dir", System.getProperty("user.home") + "/logs");
		log.info("Logs getting stored at: " + System.getProperty("todo.log.dir"));
		SpringApplication.run(BookManagementSystemApplication.class, args);
	}
}
