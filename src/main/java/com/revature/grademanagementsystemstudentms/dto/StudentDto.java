package com.revature.grademanagementsystemstudentms.dto;

import lombok.Data;

@Data
public class StudentDto {

	private int regNo;
	private String studentName;
	private String subject ;
	private int mark;
	private float avg;
	private String grade;
	private String email;
}
