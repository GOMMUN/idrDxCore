package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AnomalydetectConfirmService{
	
	@Autowired
	AnomalydetectConfirmMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(AnomalydetectConfirmDTO param) throws Exception {

		return mapper.create(param);
	}
}
