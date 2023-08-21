package com.idr.pdd.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.idr.pdd.dto.WorkerInputDTO;

@Mapper
public interface WorkerInputMapper {

	int countByTid(String tid) throws Exception;
	
	int countByWorker(WorkerInputDTO param) throws Exception;
	
	int create(WorkerInputDTO param) throws Exception;
}
