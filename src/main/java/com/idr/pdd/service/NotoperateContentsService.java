package com.idr.pdd.service;

import java.util.List;

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
	
	@Transactional
	public int create(List<NotoperateContents> param, int dataseq) throws Exception {
		int result=0;
		for(NotoperateContents notopercontent:param) {
			NotoperateContentsDTO dto =  NotoperateContentsDTO.builder()
					.notoperatetimeFrom(notopercontent.getFromtime())
					.notoperatetimeTo(notopercontent.getTotime())
					.hands(notopercontent.getMan())
					.manhour(notopercontent.getManhour())
					.cause(notopercontent.getContentcause())
					.correctiveAction(notopercontent.getCorrectiveaction())
					.tid(notopercontent.getTid())
					.workdailySeq(dataseq)
					.build();
			
			result+=mapper.create(dto);
		}
		
		return result;
	}
}
