package com.revature.grademanagementsystemstudentms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.exception.ServiceException;
import com.revature.grademanagementsystemstudentms.modal.Grade;
import com.revature.grademanagementsystemstudentms.modal.Student;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.repository.GradeRepository;
import com.revature.grademanagementsystemstudentms.repository.StudentMarkRepository;
import com.revature.grademanagementsystemstudentms.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private StudentMarkRepository studentMarkRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	@Transactional
	public Student addstudent(String name, int regno) throws ServiceException {
		Student student = new Student();
		student.setName(name);
		student.setRegno(regno);
		try {
			student = studentRepository.save(student);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException(MessageConstants.UNABLE_TO_INSERT);
		}

		return student;
	}

	public void updateMarksAndGradeService(int regno, List<StudentMark> list) {

		Student findByRegNo = studentRepository.findByRegNo(regno);

		for (StudentMark studentMark : list) {
			studentMark.setStudent(findByRegNo);
			studentMarkRepository.save(studentMark);
		}

		int total = 0;
		for (StudentMark studentMark : list) {
			total = total + studentMark.getMark();
		}

		float avg = (float) (total / list.size());
		String gradeRange = null;
		try {

			String apiUrl = "https://gradingsystemgrade.herokuapp.com";
			ResponseEntity<String> postForEntity = restTemplate.getForEntity(apiUrl + "/ScoreRange/" + avg,
					String.class);
			gradeRange = postForEntity.getBody();

			System.out.println(gradeRange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Grade grade = new Grade();

		grade.setAverage(avg);
		grade.setGrade(gradeRange);
		grade.setStudent(findByRegNo);

		System.out.println(grade);
		gradeRepository.save(grade);

	}

}
