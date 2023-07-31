package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;

@Mapper
public interface WorkContentsMapper {

	int countByTid(String tid) throws Exception;
	
	int create(WorkContentsDTO param) throws Exception;
	
	int sumProdQtyBySeq(int dataSeq) throws Exception;
	
}
