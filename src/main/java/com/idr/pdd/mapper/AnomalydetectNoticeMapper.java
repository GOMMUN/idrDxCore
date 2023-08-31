package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;
import com.idr.pdd.dto.AnomalydetectOccurDTO;


@Mapper
public interface AnomalydetectNoticeMapper {

	int countByTid(String tid) throws Exception;
	
	int count(AnomalydetectNoticeDTO param) throws Exception;
	
	int create(AnomalydetectNoticeDTO param) throws Exception;
}
