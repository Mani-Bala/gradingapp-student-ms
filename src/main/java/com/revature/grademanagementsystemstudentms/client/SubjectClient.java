package com.revature.grademanagementsystemstudentms.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
import com.revature.grademanagementsystemstudentms.service.StudentService;

@Service
public class SubjectClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	private static final String message = "Trace Message :";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<SubjectDTO> getSubjectList() {
		/* Get subject list from subject microservice api */
		List<SubjectDTO> subjectDtoList = null;
		String msg = null;
		try {
			String apiUrl = "https://gradingappsubject.herokuapp.com";
			ResponseEntity<List> postForEntity1 = restTemplate.getForEntity(apiUrl + "/subjectList", List.class);
			subjectDtoList = postForEntity1.getBody();

			System.out.println(subjectDtoList);
		} catch (Exception e) {
			LOGGER.trace(message, e);
		}
		return subjectDtoList;
	}
}
