package com.revature.grademanagementsystemstudentms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.grademanagementsystemstudentms.modal.StudentMark;

@Repository
public interface StudentMarkRepository extends JpaRepository<StudentMark, Integer>{

	@Query(" from StudentMark sm where sm.student.regno =?1")
	List<StudentMark> findByRegNo(int regno);

	@Query(value="select sm.* from student_mark sm where sm.sub_id = :code order by sm.mark desc", nativeQuery = true)
	List<StudentMark> findBySubId(@Param("code") Integer subId);

}
