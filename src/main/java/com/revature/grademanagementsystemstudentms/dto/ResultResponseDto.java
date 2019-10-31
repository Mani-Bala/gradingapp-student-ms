package com.revature.grademanagementsystemstudentms.dto;

import java.util.List;

import com.revature.grademanagementsystemstudentms.modal.StudentMark;

import lombok.Data;

@Data
public class ResultResponseDto {

	private List<StudentMark> marks;
	
	private StudentGradeDTO studentGrade;

	private List<SubjectDTO> subjectDTO;
	
	public ResultResponseDto(List<StudentMark> marks, StudentGradeDTO studentGradeDTO, List<SubjectDTO> subjectDTO) {
		super();
		this.marks = marks;
		this.studentGrade = studentGradeDTO;
		this.subjectDTO = subjectDTO;
	}
}
