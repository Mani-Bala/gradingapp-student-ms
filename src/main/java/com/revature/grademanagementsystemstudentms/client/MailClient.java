package com.revature.grademanagementsystemstudentms.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.dto.MailResultDto;
import com.revature.grademanagementsystemstudentms.service.StudentService;

@Service
public class MailClient {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	private static final String message = "Trace Message :";
	
	public void sendMail(MailResultDto resultDto) {
		try {

			String apiUrl = "https://charity-notification.herokuapp.com/";
			ResponseEntity postForEntity = restTemplate.postForEntity(apiUrl + "/student/mark",resultDto, void.class);

		} catch (Exception e) {
			LOGGER.trace(message, e);
		}
	}
}
