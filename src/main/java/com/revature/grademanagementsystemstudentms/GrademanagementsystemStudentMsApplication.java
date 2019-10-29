package com.revature.grademanagementsystemstudentms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GrademanagementsystemStudentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrademanagementsystemStudentMsApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
