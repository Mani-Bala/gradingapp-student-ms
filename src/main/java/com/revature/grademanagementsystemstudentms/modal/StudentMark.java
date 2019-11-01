package com.revature.grademanagementsystemstudentms.modal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name = "student_mark")
public class StudentMark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="student_id", referencedColumnName = "regno")
	private Student student;
	
	@Column(name="sub_id")
	private int subId;
	
	@Column(name="mark")
	private Integer mark;
	
}

