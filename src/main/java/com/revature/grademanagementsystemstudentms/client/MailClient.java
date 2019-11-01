package com.revature.grademanagementsystemstudentms.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.grademanagementsystemstudentms.dto.MailResultDto;
import com.revature.grademanagementsystemstudentms.service.StudentService;

@Service
public class MailClient {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

	private static final String message = "Trace Message :";
	
	public void sendMail(MailResultDto resultDto) {
		System.out.println(resultDto);
		
		ObjectMapper Obj = new ObjectMapper(); 
		try {

			String jsonStr = Obj.writeValueAsString(resultDto); 
			System.out.println(jsonStr);
			String apiUrl = "https://checker-notify-ms.herokuapp.com/";
			String param = "?applicationName=gradeapp&email="+ resultDto.getStudentGrade().getEmail() + "&name=Mark Details"; 
			ResponseEntity postForEntity = restTemplate.postForEntity(apiUrl + "/student/mark" + param,resultDto, void.class);

		} catch (Exception e) {
			
			LOGGER.trace(message, e);
		}
	}
}
