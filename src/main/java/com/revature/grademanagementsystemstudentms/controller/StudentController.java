package com.revature.grademanagementsystemstudentms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.revature.grademanagementsystemstudentms.configuration.Message;
import com.revature.grademanagementsystemstudentms.dto.StudentDto;
import com.revature.grademanagementsystemstudentms.exception.ServiceException;
import com.revature.grademanagementsystemstudentms.modal.Student;
import com.revature.grademanagementsystemstudentms.service.StudentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping("/AddStudent")
	@ApiOperation(value = "AddStudent API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Student.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto) {

		String errorMessage = null;
		Student student = null;
		Message message = null;
		try {
			student = studentService.addstudent(studentDto.getName(), studentDto.getRegNo(), studentDto.getEmail());
			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (Exception e) {
			errorMessage = e.getMessage();
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping("/login")
    @ApiOperation(value = "Login API")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully LogedIn", response = Student.class),
            @ApiResponse(code = 400, message = "Invalid Access", response = Message.class) })
	  public ResponseEntity<?> loginController(@RequestParam("regno") int regno, @RequestParam("email") String email) {
		 String errorMessage = null;
	      Student student=null;
	      
		 try {
			 student = studentService.login(regno, email);
	          return new ResponseEntity<>(student, HttpStatus.OK );
	      } catch (ServiceException e) {
	          errorMessage = e.getMessage();
	          Message message = new Message(errorMessage);
	          return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST );
	      }
	}
}
