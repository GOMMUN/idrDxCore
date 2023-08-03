package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.JobexechistDTO;

@Mapper
public interface JobexechistMapper {

	void create(JobexechistDTO dto);
	void save(JobexechistDTO dto); 
}
