package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;



@Mapper
public interface AnomalydetectConfirmMapper {

	int countByTid(String tid) throws Exception;
	
	int count(AnomalydetectConfirmDTO param) throws Exception;
	
	int create(AnomalydetectConfirmDTO  param) throws Exception;
}
