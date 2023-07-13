package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.NotoperateContentsDTO;

@Mapper
public interface NotoperateContentsMapper {

	int countByTid(String tid) throws Exception;
	
	int create(NotoperateContentsDTO param) throws Exception;
}
