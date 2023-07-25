package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.Anomalydetect;
import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.AnomalydetectConfirmMapper;
import com.idr.pdd.mapper.AnomalydetectNoticeMapper;
import com.idr.pdd.mapper.AnomalydetectOccurMapper;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class AnomalydetectService{
	
	@Autowired
	AnomalydetectOccurMapper occurMapper;
	
	@Autowired
	AnomalydetectNoticeMapper noticeMapper;
	
	@Autowired
	AnomalydetectConfirmMapper confirmMapper;
	
	public int countByTid(String tid) throws Exception {
		return occurMapper.countByTid(tid);
	}
	
	@Transactional
	public int create(Anomalydetect param) throws Exception {
		
		int result = 0;
		
		if("OCCUR".equals(param.getMessengerState())) {
			
			AnomalydetectOccurDTO data = AnomalydetectOccurDTO.builder()
				.factoryid(param.getFactory())
				.occurid(param.getMessengerid())
				.occurReason(param.getMessengerReason())
				.occurReasondescRiption(param.getMessengerReasondescription())
				.tid(param.getMessengerid()).build();
			
			result = occurMapper.create(data);
		}else if("NOTICE".equals(param.getMessengerState())) {
			
			AnomalydetectNoticeDTO data = AnomalydetectNoticeDTO.builder()
					.factoryid(param.getFactory())
					.noticeid(param.getMessengerid())
					.noticeReason(param.getMessengerReason())
					.noticeReasondescRiption(param.getMessengerReasondescription())
					.tid(param.getMessengerid()).build();
			
			result = noticeMapper.create(data);
		}else if("CONFIRM".equals(param.getMessengerState())) {
			AnomalydetectConfirmDTO data = AnomalydetectConfirmDTO.builder()
					.factoryid(param.getFactory())
					.confirmid(param.getMessengerid())
					.tid(param.getMessengerid()).build();
			
			result = confirmMapper.create(data);
		}
		
		return result;
	}
}
