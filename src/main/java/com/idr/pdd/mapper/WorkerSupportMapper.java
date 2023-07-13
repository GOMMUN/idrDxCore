package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.idr.pdd.dto.WorkerManhourDTO;
import com.idr.pdd.dto.WorkerSupportDTO;

@Mapper
public interface WorkerSupportMapper {

	int countByTid(String tid) throws Exception;
	
	int create(WorkerSupportDTO param) throws Exception;
}
