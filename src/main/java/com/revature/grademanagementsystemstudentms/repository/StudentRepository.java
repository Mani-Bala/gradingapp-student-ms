package com.revature.grademanagementsystemstudentms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.grademanagementsystemstudentms.modal.Student;
@Transactional
@Repository
public interface StudentRepository  extends JpaRepository<Student, Integer>{
	
//	@Query(" from Student where student_name = :student_name")
//    List<Student> findByStudent(@Param("student_name") String studentName);

	@Query("from Student sd where sd.regno = :regno")
	Student findByRegNo(@Param("regno") int regno);

	@Query("from Student s where s.regno = :regno and s.email = :email")
	Student findByRegnoAndEmail(@Param("regno") int regno, @Param("email") String email);
}
