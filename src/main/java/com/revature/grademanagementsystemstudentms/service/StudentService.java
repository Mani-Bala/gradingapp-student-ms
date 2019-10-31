package com.revature.grademanagementsystemstudentms.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.dto.StudentDto;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
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

	/** 
	 * update Marks And Grade Service in UserService
	 * @Param regNo and List<StudentMark> marks
	 * 
	 * insert marks 
	 * calculate total, average and grade
	 * insert total, average and grade.
	 */
	public void updateMarksAndGradeService(int regno, List<StudentMark> list) {

		Student findByRegNo = studentRepository.findByRegNo(regno);

		LOGGER.debug("Student Details: "+findByRegNo);
		for (StudentMark studentMark : list) {
			studentMark.setStudent(findByRegNo);
			studentMarkRepository.save(studentMark);
		}

		int total = 0;
		for (StudentMark studentMark : list) {
			total = total + studentMark.getMark();
		}

		float avg = (float) (total / list.size());
		LOGGER.debug("Average:" + avg);
		
		/* Get the grade based on average from grade microservice api */
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
		
		Grade grade = new Grade();

		grade.setAverage(avg);
		grade.setGrade(gradeRange);
		grade.setStudent(findByRegNo);

		gradeRepository.save(grade);
		
		List<StudentMark> markList = getStudentMarks(regno);
		StudentGradeDTO studentResult = getStudentResult(regno);
		
		/* Get subject list from getSubjectList() */
		List<SubjectDTO> subjectDtoList = getSubjectList();
		
		/* Iterate marks, studentDetails, subject in a DTO(StudentDto) class */
		List<StudentDto> studentDtoList = new ArrayList<StudentDto>();
		for (StudentMark studentMark : markList) {
			for (SubjectDTO subjectDTO : subjectDtoList) {
				StudentDto dto = toStudentDto(studentMark, studentResult, subjectDTO);
				
				studentDtoList.add(dto);
			}
		}
		
		/* send result to student mail through mail notification service */
		List<StudentDto> mailList = null;
		try {

			String apiUrl = "https://charity-notification.herokuapp.com/";
			ResponseEntity<List> postForEntity2 = restTemplate.getForEntity(apiUrl + "/student/mark/" + mailList, List.class);
			mailList = postForEntity2.getBody();

			System.out.println(mailList);
		} catch (Exception e) {
			LOGGER.trace(gradeRange, e);
		}
	}
	
	private StudentDto toStudentDto(StudentMark studentMark, StudentGradeDTO studentResult, SubjectDTO subjectDTO) {
		StudentDto dto = new StudentDto();
		
		dto.setStudentName(studentResult.getStudentName());
		dto.setRegNo(studentResult.getRegNo());
		dto.setAvg(studentResult.getAvg());
		dto.setGrade(studentResult.getGrade());
		dto.setMark(studentMark.getMark());
		dto.setSubject(subjectDTO.getSubName());
		return dto;
	}

	/** 
	 * getStudentResult Service in UserService
	 * @Param regNo
	 * 
	 * return StudentGradeDTO object
	 */
	public StudentGradeDTO getStudentResult(int regno) {
		Grade grade = gradeRepository.findByRegNo(regno);
		StudentGradeDTO dto = toStudentGradeDTO(grade);
		return dto;
	}

	/** 
	 * getStudentMarks Service in UserService
	 * @Param regNo
	 * 
	 * return List<StudentMark>
	 */
	public List<StudentMark> getStudentMarks(int regno) {
		return studentMarkRepository.findByRegNo(regno);
	}
	
	/** 
	 * @Param Grade object
	 * 
	 * return StudentGradeDTO (dto object)
	 */
	public StudentGradeDTO toStudentGradeDTO(Grade studentGrade) {
		StudentGradeDTO dto = new StudentGradeDTO();
		final Student student = studentGrade.getStudent();
		dto.setRegNo(student.getRegno());
		dto.setStudentName(student.getName());
		dto.setAvg(studentGrade.getAverage());
		dto.setGrade(studentGrade.getGrade());
		return dto;
	}

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
			LOGGER.trace(msg, e);
		}
		return subjectDtoList;
	}

}
