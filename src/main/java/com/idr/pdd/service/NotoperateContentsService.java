package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class NotoperateContentsService{
	
	@Autowired
	NotoperateContentsMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(NotoperateContents param, int dataseq) throws Exception {
		
		NotoperateContentsDTO result = NotoperateContentsDTO.builder()
										.notoperatetimeFrom(param.getFromtime())
										.notoperatetimeTo(param.getTotime())
										.hands(param.getMan())
										.manhour(param.getManhour())
										.cause(param.getContentcause())
										.correctiveAction(param.getCorrectiveaction())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
}
