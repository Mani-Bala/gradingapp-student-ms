package com.revature.grademanagementsystemstudentms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.exception.ServiceException;
import com.revature.grademanagementsystemstudentms.modal.Grade;
import com.revature.grademanagementsystemstudentms.modal.Student;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.repository.GradeRepository;
import com.revature.grademanagementsystemstudentms.repository.StudentMarkRepository;

@Service
public class GradeService {

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private StudentMarkRepository studentMarkRepository;
	
	public List<StudentGradeDTO> listOfStudentService() throws ServiceException {
	
		List<StudentGradeDTO> listOfStudent = new ArrayList<StudentGradeDTO>();
		
		try {
			List<Grade> findAll = gradeRepository.findTopToBottomGrade();
			
			for (Grade studentGrade : findAll) {
				
				StudentGradeDTO dto = toStudentGradeDTO(studentGrade);
				
				listOfStudent.add(dto);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return listOfStudent;
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

	/** 
	 * findByGrade Service in UserService
	 * @Param grade
	 * 
	 * If the credential is Invalid, return ServiceException
	 * 
	 * If the credential is valid, return List<StudentGradeDTO> 
	 */
	public List<StudentGradeDTO> findByGradeService(String grade)  throws ServiceException{
		List<StudentGradeDTO> dtoList = new ArrayList<StudentGradeDTO>();
		List<Grade> specificGradeList = null;
		
			specificGradeList = gradeRepository.findByGrade(grade);

			System.out.println(specificGradeList);
			
			
				for (Grade studentGrade : specificGradeList) {
					StudentGradeDTO dto = toStudentGradeDTO(studentGrade);
					
					dtoList.add(dto);
				}
				
			if ( dtoList.size() == 0 )
				throw new ServiceException(MessageConstants.NO_RECORDS_AVAILABLE);

			return dtoList;
	}

	public List<StudentMark> findBySubIdService(Integer subId) {
		return studentMarkRepository.findBySubId(subId);
	}

}
