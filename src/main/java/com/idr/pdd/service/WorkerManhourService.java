package com.idr.pdd.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerManhour;
import com.idr.pdd.dto.WorkerInputDTO;
import com.idr.pdd.dto.WorkerManhourDTO;
import com.idr.pdd.mapper.WorkerInputMapper;
import com.idr.pdd.mapper.WorkerManhourMapper;

@Service
public class WorkerManhourService{
	
	@Autowired
	WorkerManhourMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(WorkerManhour param, int dataseq) throws Exception {
		
		WorkerManhourDTO result = WorkerManhourDTO.builder()
										.inputItemid(param.getSeparation())
										.hands(param.getMan())
										.manhour(param.getManhour())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
	
	@Transactional
	public int create(List<WorkerManhour> param, int dataseq) throws Exception {
		
		int result=0;
		for(WorkerManhour wm:param) {
			WorkerManhourDTO dto = WorkerManhourDTO.builder()
					.inputItemid(wm.getSeparation())
					.hands(wm.getMan())
					.manhour(wm.getManhour())
					.tid(wm.getTid())
					.workdailySeq(dataseq)
					.build();
			result+=mapper.create(dto);
		}
		
		return result;
	}
}
