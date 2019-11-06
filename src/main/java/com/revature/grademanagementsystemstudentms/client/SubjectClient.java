package com.revature.grademanagementsystemstudentms.client;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;

@Service
public class SubjectClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectClient.class);

	private static final String message = "Trace Message :";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Map<Integer, SubjectDTO> getSubjectList() {
		/* Get subject list from subject microservice api */
		List<SubjectDTO> subjectDtoList = new ArrayList<>();
		Map<Integer,SubjectDTO> subjectMap = new HashMap<Integer,SubjectDTO>();
		String msg = null;
		try {
			String apiUrl = "https://gradingappsubject.herokuapp.com";
			ResponseEntity<List> postForEntity1 = restTemplate.getForEntity(apiUrl + "/subjectList", List.class);
			
			//subjectDtoList = postForEntity1.getBody();
			List<LinkedHashMap> list = postForEntity1.getBody();
			for (LinkedHashMap map : list) {
				
				Integer id = (Integer) map.get("id");
				String code = (String) map.get("code");
				String name = (String) map.get("name");
				SubjectDTO dto = new SubjectDTO(id, code, name);
				subjectMap.put(id, dto);
			}
			System.out.println(subjectDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.trace(message, e);
		}
		return subjectMap;
	}
}
