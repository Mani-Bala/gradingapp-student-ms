package com.revature.grademanagementsystemstudentms.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResultResponseDto {

	private StudentGradeDTO studentGrade;

	private List<MarkDto> markList;
	
	public ResultResponseDto(List<MarkDto> markList, StudentGradeDTO studentGradeDTO) {

		this.markList = markList;
		this.studentGrade = studentGradeDTO;
	}
}
