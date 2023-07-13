package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class WorkContentsService{
	
	@Autowired
	WorkContentsMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(WorkContents param, int dataseq) throws Exception {
		
		WorkContentsDTO result = WorkContentsDTO.builder()
										.worktimeFrom(param.getFromtime())
										.worktimeTo(param.getTotime())
										.modelid(param.getModel())
										.operationid(param.getOperation())
										.manhour(param.getManhour())
										.prodQty(param.getProdqty())
										.firsttimeGoodQty(param.getFirstgoodqty())
										.firsttimeFailQty(param.getFirstfailqty())
										.reworkGoodQty(param.getReworkgoodqty())
										.reworkFailQty(param.getReworkfailqty())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
}
