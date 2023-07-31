package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.mapper.AlarmSettingMapper;

@Service
public class AlarmSettingService {
	
	@Autowired
	private AlarmSettingMapper mapper;

	public int find(String type) throws Exception{
		return mapper.find(type);
	}
}
