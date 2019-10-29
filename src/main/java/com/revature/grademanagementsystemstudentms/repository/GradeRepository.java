package com.revature.grademanagementsystemstudentms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.grademanagementsystemstudentms.modal.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer>{

//	@Query("from Grade g where g.student.regno = ?1")
//	Grade findByRegNo(int regno);

	@Query("from Grade g where g.grade = ?1")
	List<Grade> findByGrade(String grade);

	@Query(value="select g.* from student_grade g order by g.average desc", nativeQuery = true)
	List<Grade> findTopToBottomGrade();

}
