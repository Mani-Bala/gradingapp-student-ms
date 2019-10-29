package com.revature.grademanagementsystemstudentms.validator;

import org.springframework.stereotype.Service;

import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;

@Service
public class GradeValidator {

	public void gradeCheck(String grade) throws ValidatorException {
		
		if (grade == null || "".equals(grade.trim()) || grade.length() != 1) 
			throw new ValidatorException(MessageConstants.INVALID_GRADE);
	}
}
