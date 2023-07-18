package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.AnomalydetectOccurDTO;

@Mapper
public interface AnomalydetectOccurMapper {

	int countByTid(String tid) throws Exception;
	
	int create(AnomalydetectOccurDTO param) throws Exception;
}
