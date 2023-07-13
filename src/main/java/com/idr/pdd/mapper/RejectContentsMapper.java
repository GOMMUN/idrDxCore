package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.RejectContentsDTO;

@Mapper
public interface RejectContentsMapper {

	int countByTid(String tid) throws Exception;
	
	int create(RejectContentsDTO param) throws Exception;
}
