package com.revature.grademanagementsystemstudentms.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.grademanagementsystemstudentms.configuration.MessageConstants;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.repository.StudentMarkRepository;

@Service
public class StudentValidator {

		@Autowired
		private StudentMarkRepository studentMarkRepository;
		
		public void isRegnoUpdated(int regno) throws ValidatorException{

			List<StudentMark> list = null;
			list = studentMarkRepository.findByRegNo(regno);	
			
			if( list.size() != 0 )
				throw new ValidatorException(regno+ MessageConstants.ALREADY_UPDATED);
		}
		
		public void isRegnoExist(int regno) throws ValidatorException{
			
			List<StudentMark> list = null;
			list = studentMarkRepository.findByRegNo(regno);	
			
			System.out.println(list);
			if( list.size() == 0 )
				throw new ValidatorException(MessageConstants.MARK_DOESNOT_UPDATED);
			
		}

		public void loginInput(int regno, String email) throws ValidatorException {
			
			if (regno <= 0 || "".equals(email.trim())) {
				throw new ValidatorException(MessageConstants.INVALID_CREDENTIAL);
			}
			
		}
}
