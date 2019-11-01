package com.revature.grademanagementsystemstudentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.grademanagementsystemstudentms.client.SubjectClient;
import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.dto.MarkDto;
import com.revature.grademanagementsystemstudentms.dto.ResultResponseDto;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.service.MarkSubjectUtil;
import com.revature.grademanagementsystemstudentms.service.StudentService;
import com.revature.grademanagementsystemstudentms.validator.StudentValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ResultController {
	@Autowired
	private StudentService studentService;

	@Autowired
	private SubjectClient SubjectClient;

	@Autowired
	private StudentValidator studentValidator;
	
	@GetMapping("studentResult")
	//@ResponseStatus ( code = HttpStatus.OK)
	@ApiOperation(value = "Result API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated mark", response = ResultResponseDto.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> studentResult(@RequestParam("regno")int regno){
		
		try {
			studentValidator.isRegnoExist(regno);
			
			StudentGradeDTO studentResult = studentService.getStudentResult(regno);
			List<StudentMark> studentMarks =  studentService.getStudentMarks(regno);
			MarkSubjectUtil util = new MarkSubjectUtil( SubjectClient.getSubjectList());
			List<MarkDto> markList =  util.convertToMarkDto(studentMarks);
			
			// move the above code to service
			ResultResponseDto result = new ResultResponseDto(markList, studentResult);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ValidatorException e) {
			Message message = new Message(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
}