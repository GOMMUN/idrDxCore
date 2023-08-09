package com.idr.pdd.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.dto.WorkDailyReportDTO;
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
	
	@Transactional
	public int create(List<WorkerInput> param, int dataseq) throws Exception {
		int result=0;
		for(WorkerInput wi:param) {
			WorkerInputDTO dto = WorkerInputDTO.builder()
					.personid(wi.getWorker())
					.overtime(wi.getOvertime())
					.tid(wi.getTid())
					.workdailySeq(dataseq)
					.build();
			result += mapper.create(dto);
		}
		
		return result;
	}

	
}
