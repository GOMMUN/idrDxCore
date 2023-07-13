package com.idr.pdd.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.dto.WorkerInputDTO;
import com.idr.pdd.mapper.WorkerInputMapper;

@Service
public class WorkerInputService{
	
	@Autowired
	WorkerInputMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(WorkerInput param, int dataseq) throws Exception {
		
		WorkerInputDTO result = WorkerInputDTO.builder()
										.personid(param.getWorker())
										.overtime(param.getOvertime())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
}
