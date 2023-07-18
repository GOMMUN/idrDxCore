package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;


@Mapper
public interface AnomalydetectNoticeMapper {

	int countByTid(String tid) throws Exception;
	
	int create(AnomalydetectNoticeDTO param) throws Exception;
}
