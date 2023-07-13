package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.WorkContentsDTO;

@Mapper
public interface WorkContentsMapper {

	int countByTid(String tid) throws Exception;
	
	int create(WorkContentsDTO param) throws Exception;
}
