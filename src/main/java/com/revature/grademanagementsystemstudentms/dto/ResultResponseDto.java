package com.revature.grademanagementsystemstudentms.dto;

import java.util.List;

import com.revature.grademanagementsystemstudentms.modal.StudentMark;

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
