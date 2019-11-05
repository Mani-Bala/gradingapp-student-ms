package com.revature.grademanagementsystemstudentms.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.service.StudentService;

@Service
public class GradeClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 *  Get the grade based on average from grade microservice api
	 */
	public String getGrade(Float avg) {
		
		
		String gradeRange = null;
		try {
			String apiUrl = "https://gradingsystemgrade.herokuapp.com";
			ResponseEntity<String> postForEntity = restTemplate.getForEntity(apiUrl + "/ScoreRange/" + avg,
					String.class);
			gradeRange = postForEntity.getBody();

			System.out.println(gradeRange);
		} catch (Exception e) {
			LOGGER.trace(gradeRange, e);
		}
		return gradeRange;

	}
}
