package com.revature.grademanagementsystemstudentms.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name="student_details")
public class Student {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name="id")
	 private int id;
	 @Column(name="regno")
	 private int regno;
	 @Column(name="name")
	 private String name;
}
