package com.revature.grademanagementsystemstudentms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.revature.grademanagementsystemstudentms.configuration.Message;
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
    public ResponseEntity<?> addStudent(@RequestParam("name") String name,@RequestParam("regno") int regno) throws ServiceException {
    
        String errorMessage=null;
       Student student=null;
        Message message = null;
    try {
       student= studentService.addstudent(name, regno);
        return new ResponseEntity<>(student, HttpStatus.OK);
    } catch (Exception e) {
        errorMessage=e.getMessage();
         message = new Message(errorMessage);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);        
        
    }
}}
