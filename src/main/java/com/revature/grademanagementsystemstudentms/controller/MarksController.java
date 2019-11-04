package com.revature.grademanagementsystemstudentms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.dto.UpdateMarkDTO;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.service.StudentService;
import com.revature.grademanagementsystemstudentms.validator.StudentValidator;

import io.swagger.annotations.ApiResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("mark")
public class MarksController {
	
	@Autowired
	private StudentValidator studentValidator;
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("updateMark")
	//@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Mark API")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully updated mark", response = Message.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> updateMark(@RequestBody UpdateMarkDTO updateMark) {
		System.out.println(updateMark);
		
		StudentMark sm1 = new StudentMark();

		sm1.setMark(updateMark.getMark1());
		sm1.setSubId(1);
		
		StudentMark sm2 = new StudentMark();

		sm2.setMark(updateMark.getMark2());
		sm2.setSubId(2);

		StudentMark sm3 = new StudentMark();

		sm3.setMark(updateMark.getMark3());
		sm3.setSubId(3);

		StudentMark sm4 = new StudentMark();

		sm4.setMark(updateMark.getMark4());
		sm4.setSubId(4);

		StudentMark sm5 = new StudentMark();

		sm5.setMark(updateMark.getMark5());
		sm5.setSubId(5);

		List<StudentMark> list = new ArrayList<StudentMark>();
		list.add(sm1);
		list.add(sm2);
		list.add(sm3);
		list.add(sm4);
		list.add(sm5);
		
		String errorMessage = null;
		String status = "";
		try {
			studentValidator.isRegnoUpdated(updateMark.getRegno());

			studentService.updateMarksAndGradeService(updateMark.getRegno(), list);

			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		if (status.equals("Success")) {
			Message message = new Message(status);
			return new ResponseEntity<>(message, HttpStatus.OK);
			
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_GATEWAY);
		}
	}
}
