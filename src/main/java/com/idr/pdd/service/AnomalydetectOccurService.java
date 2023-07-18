package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.AnomalydetectOccurMapper;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class AnomalydetectOccurService{
	
	@Autowired
	AnomalydetectOccurMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(AnomalydetectOccurDTO param, int dataseq) throws Exception {

		return mapper.create(param);
	}
}
