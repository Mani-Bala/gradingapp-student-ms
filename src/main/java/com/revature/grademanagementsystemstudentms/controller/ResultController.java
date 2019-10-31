package com.revature.grademanagementsystemstudentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.dto.ResultResponseDto;
import com.revature.grademanagementsystemstudentms.dto.StudentGradeDTO;
import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
import com.revature.grademanagementsystemstudentms.exception.ValidatorException;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
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
	private StudentValidator studentValidator;
	
	@GetMapping("studentResult")
	//@ResponseStatus ( code = HttpStatus.OK)
	@ApiOperation(value = "Result API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated mark", response = ResultResponseDto.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> studentResult(@RequestParam("regno")int regno){
		List<StudentMark> markList = null;
		StudentGradeDTO studentResult = null;
		List<SubjectDTO> subjectDTO = null;
		try {
			studentValidator.isRegnoExist(regno);
			
			studentResult = studentService.getStudentResult(regno);
			markList = studentService.getStudentMarks(regno);
			subjectDTO = studentService.getSubjectList();
			ResultResponseDto result = new ResultResponseDto(markList, studentResult, subjectDTO);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ValidatorException e) {
			Message message = new Message(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
}