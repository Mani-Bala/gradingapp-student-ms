package com.revature.grademanagementsystemstudentms.dto;

import java.util.List;

import lombok.Data;

@Data
public class MailResultDto {

	private List<MarkDto> marks;

	private StudentGradeDTO studentGrade;

	public MailResultDto(List<MarkDto> marks, StudentGradeDTO studentGradeDTO) {
		super();
		this.marks = marks;
		this.studentGrade = studentGradeDTO;
	}
}
