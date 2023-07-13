package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.RejectContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.RejectContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.RejectContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class RejectContentsService{
	
	@Autowired
	RejectContentsMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(RejectContents param, int dataseq) throws Exception {
		
		RejectContentsDTO result = RejectContentsDTO.builder()
										.operationid(param.getOperation())
										.rejectItemid(param.getRejectcode())
										.modelid(param.getModel())
										.firsttimeRejectQty(param.getFirstrejectqty())
										.reworkRejectQty(param.getReworkrejectqty())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
}
