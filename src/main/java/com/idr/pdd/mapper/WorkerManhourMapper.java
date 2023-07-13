package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.idr.pdd.dto.WorkerManhourDTO;

@Mapper
public interface WorkerManhourMapper {

	int countByTid(String tid) throws Exception;
	
	int create(WorkerManhourDTO param) throws Exception;
}
