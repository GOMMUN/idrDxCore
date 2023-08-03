package com.idr.pdd.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.mapper.JobexechistMapper;

@Service
public class JobexechistService {

	@Autowired
	private JobexechistMapper mapper;
	
	public void create(String tid, String state, String start, String error) {
		
		JobexechistDTO dto = new JobexechistDTO();
		dto.setExecTid(tid);
		dto.setExecStarttime(start);
		dto.setExecEndtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")));
		dto.setState(state);
		dto.setMessageError(error);
		
		mapper.create(dto);
	}
	
	public void save(String tid, String state, String start, String error){
		JobexechistDTO dto = new JobexechistDTO();
		dto.setExecTid(tid);
		dto.setExecStarttime(start);
		dto.setExecEndtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")));
		dto.setState(state);
		dto.setMessageError(error);
		
		mapper.save(dto);
	}
}
