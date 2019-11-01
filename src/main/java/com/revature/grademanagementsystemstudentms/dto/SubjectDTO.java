package com.revature.grademanagementsystemstudentms.dto;

import lombok.Data;

@Data
public class SubjectDTO {
	
	private int id;
	private String subCode;
	private String subName;
	
	public SubjectDTO() {
		
	}

	public SubjectDTO(int id, String subCode, String subName) {
		super();
		this.id = id;
		this.subCode = subCode;
		this.subName = subName;
	}
}
