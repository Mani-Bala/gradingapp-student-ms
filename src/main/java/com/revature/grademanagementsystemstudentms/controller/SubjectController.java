package com.revature.grademanagementsystemstudentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;
import com.revature.grademanagementsystemstudentms.service.GradeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("subject")
public class SubjectController {

	@Autowired
	private GradeService gradeService;
	
	@GetMapping("/subjectWiseMark")
	//@ResponseStatus ( code = HttpStatus.OK )
	@ApiOperation(value = "SubjectWiseList API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 201, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> subjectWiseRankHolder(@RequestParam("subjectId")Integer subId){
		
		List<StudentMark> list = null;
		String errorMessage = "";
		String status = "";
		list = gradeService.findBySubIdService(subId);
		status = "success";

		if (status.equals("success")) {
			return new ResponseEntity<>(list, HttpStatus.OK );
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
		}	
	}
}
