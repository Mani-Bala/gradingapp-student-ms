package com.revature.grademanagementsystemstudentms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revature.grademanagementsystemstudentms.dto.MarkDto;
import com.revature.grademanagementsystemstudentms.dto.SubjectDTO;
import com.revature.grademanagementsystemstudentms.modal.StudentMark;

public class MarkSubjectUtil {

	
	private Map<Integer,SubjectDTO> subjectMap;

	public MarkSubjectUtil(Map<Integer, SubjectDTO> subjectMap) {
		super();
		this.subjectMap = subjectMap;
	}

	public MarkDto convertToMarkDto(StudentMark studentMark) {

		MarkDto markDTO = new MarkDto();
		markDTO.setMark(studentMark.getMark());
		Integer subId = studentMark.getSubId();
		
		// In MarkDTO itself, we need to store subject code and subject name.
		if( subjectMap.containsKey(subId)) {
			
				SubjectDTO subjectDTO = subjectMap.get(subId);
				markDTO.setSubjectCode(subjectDTO.getSubCode());
				markDTO.setSubjectName(subjectDTO.getSubName());
			
		}
		return markDTO;
	}

	public List<MarkDto> convertToMarkDto(List<StudentMark> markList) {
		List<MarkDto> list = new ArrayList<>();
		for (StudentMark mark : markList) {
			MarkDto markdto = convertToMarkDto(mark);
			list.add(markdto);
		}
		return list;
	}

	
	
	
	
	
}
