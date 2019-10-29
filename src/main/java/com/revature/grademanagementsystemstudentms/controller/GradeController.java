package com.revature.grademanagementsystemstudentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.exception.ServiceException;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.service.GradeService;
import com.revature.grademanagementsystemstudentms.validator.GradeValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("grade")
public class GradeController {

	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private GradeValidator gradeValidator;
	
	@GetMapping("/gradeWiseList")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Grade API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> gradeWiseList() {
	
		List<StudentGradeDTO> list = null;
		String errorMessage = "";
		String status = "";
	
		try {
			list = gradeService.listOfStudentService();
			status = "success";
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
	
		if (status.equals("success")) {
			return new ResponseEntity<>(list, HttpStatus.OK );
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}
	}
	
	@GetMapping("/SpecficGradeWiseList")
	//@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Grade API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> SpecficGradeWiseList(@RequestParam("grade") String grade) {
		
		List<StudentGradeDTO> list = null;
		String errorMessage = "";
		String status = "";

		try {
			// grade Validation
			gradeValidator.gradeCheck(grade.toUpperCase());

			// call Service class and get the result
			list = gradeService.findByGradeService(grade.toUpperCase());

			status = "success";

		}catch (ServiceException e) {
			errorMessage = e.getMessage();
		} catch (ValidatorException e) {
			errorMessage = e.getMessage();
		} 

		if (status.equals("success")) { 
			return new ResponseEntity<>(list, HttpStatus.OK );
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}
	}
}
