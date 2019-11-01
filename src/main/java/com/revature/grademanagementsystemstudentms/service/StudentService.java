package com.revature.grademanagementsystemstudentms.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.grademanagementsystemstudentms.client.GradeClient;
import com.revature.grademanagementsystemstudentms.client.MailClient;
import com.revature.grademanagementsystemstudentms.client.SubjectClient;
import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.dto.MailResultDto;
import com.revature.grademanagementsystemstudentms.dto.MarkDto;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
import com.revature.grademanagementsystemstudentms.exception.ServiceException;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.modal.Grade;
import com.revature.grademanagementsystemstudentms.modal.Student;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.repository.GradeRepository;
import com.revature.grademanagementsystemstudentms.repository.StudentMarkRepository;
import com.revature.grademanagementsystemstudentms.repository.StudentRepository;
import com.revature.grademanagementsystemstudentms.validator.StudentValidator;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private StudentMarkRepository studentMarkRepository;

	@Autowired
	private MailClient mailService;
	
	@Autowired
	private GradeClient gradeClient;
	
	@Autowired
	private SubjectClient subjectClient;
	
	@Autowired
	private StudentValidator studentvalidator;

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	@Transactional
	public Student addstudent(String name, int regno, String email) throws ServiceException {
		Student student = new Student();
		student.setName(name);
		student.setRegno(regno);
		student.setEmail(email);
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
		String gradeRange = gradeClient.getGrade(avg);		
		Grade grade = new Grade();

		grade.setAverage(avg);
		grade.setGrade(gradeRange);
		grade.setStudent(findByRegNo);

		gradeRepository.save(grade);
		
		List<StudentMark> markList = getStudentMarks(regno);
		
		List<MarkDto> mark = new ArrayList<MarkDto>();
		for (StudentMark studentMark : markList) {
			MarkDto markDTO = new MarkDto();
			markDTO.setMark(studentMark.getMark());
			mark.add(markDTO);
		}

		StudentGradeDTO studentResult = getStudentResult(regno);
		
		/* Get subject list from getSubjectList() */
		List<SubjectDTO> subjectDtoList = subjectClient.getSubjectList();
		
		/* store marks, studentDetails, subject in a DTO(ResultResponseDto) class */
		MailResultDto resultDto = new MailResultDto(mark, studentResult, subjectDtoList);
		
		mailService.sendMail(resultDto);
		
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
		dto.setEmail(student.getEmail());
		dto.setAvg(studentGrade.getAverage());
		dto.setGrade(studentGrade.getGrade());
		return dto;
	}

	public Student login(int regno, String email) throws ServiceException {
		Student student = null;
		System.out.println(regno +" : "+ email);
		try {
			studentvalidator.loginInput(regno, email);
				
			student = studentRepository.findByRegnoAndEmail(regno, email);
			System.out.println(student);
			if (student == null) {
				throw new ServiceException(MessageConstants.INVALID_CREDENTIAL);
			}
		} catch (ValidatorException e) {
				LOGGER.error("Exception:", e);
				throw new ServiceException(e.getMessage());
		}
	return student;
	}

	

}
